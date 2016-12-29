package com.zlkj.dingdangwuyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.ImageUtil;
import com.zlkj.dingdangwuyou.utils.LogHelper;
import com.zlkj.dingdangwuyou.widget.CutImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片裁剪
 * Created by Botx on 2016/11/26.
 */
public class CutImageActivity extends Activity implements OnClickListener{

	private TextView txtTitle;
	private TextView txtRevoke; //撤消
	private TextView txtConfirm; //裁剪、完成
	private ImageView imgViBack;
	private CutImageView cutImageView;
	private ImageView imgViResult;
	private RelativeLayout laytResultImg;

	private Bitmap origBitmap;
	private Bitmap resultBitmap;
	private String origPath;
	private String resultPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cut_image);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			origPath = bundle.getString("path");
		}

		//压缩原始图片
		if (!TextUtils.isEmpty(origPath)) {
			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(origPath, opts);
			int origWidth = opts.outWidth;
			int origHeight = opts.outHeight;
			int be = 1;
			if (origWidth > 2048 || origHeight > 2048) {
				if (origWidth >= origHeight) {
					be = origWidth / 2048;
				} else {
					be = origHeight / 2048;
				}
				opts.inJustDecodeBounds = false;
				opts.inSampleSize = be;
				origBitmap = BitmapFactory.decodeFile(origPath, opts);
			} else {
				origBitmap = BitmapFactory.decodeFile(origPath);
			}
		}
		
		//处理图片方向
		int degree = ImageUtil.parseDirection(origPath);
		if (degree != 0 && origBitmap != null) {
			Matrix matrix = new Matrix();
			matrix.postRotate(degree);
			origBitmap = Bitmap.createBitmap(origBitmap, 0, 0, origBitmap.getWidth(), origBitmap.getHeight(), matrix, true);
		}
		
		initView();
	}
	
	private void initView() {
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTitle.setText("图片裁剪");
		imgViBack = (ImageView) findViewById(R.id.imgViBack);
		
		laytResultImg = (RelativeLayout) findViewById(R.id.laytResultImg);
		txtRevoke = (TextView) findViewById(R.id.txtRevoke);
		txtConfirm = (TextView) findViewById(R.id.txtConfirm);

		cutImageView = (CutImageView) findViewById(R.id.cutImageVi);
		if (origBitmap != null) {
			cutImageView.setOrigBitmap(origBitmap);
		}
		imgViResult = (ImageView) findViewById(R.id.imgViResult);
		
		imgViBack.setOnClickListener(this);
		txtRevoke.setOnClickListener(this);
		txtConfirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.imgViBack: //返回
			finish();
			break;

		case R.id.txtConfirm: //裁剪、完成
			if (txtConfirm.getText().equals("裁剪")) {
				resultBitmap = cutImageView.getResultBitmap();
				LogHelper.e("CutImgResult", resultBitmap.getWidth() + "*" + resultBitmap.getHeight());
				cutImageView.setVisibility(View.GONE);
				laytResultImg.setVisibility(View.VISIBLE);
				imgViResult.setImageBitmap(resultBitmap);
				
				txtRevoke.setVisibility(View.VISIBLE);
				txtConfirm.setText("完成");
			} else if (txtConfirm.getText().equals("完成")) {
				boolean state = saveBitmap(resultBitmap);
				if (state) {
					Intent intent = new Intent();
					intent.putExtra(Const.KEY_IMAGE_PATH, resultPath);
					setResult(Const.RESULT_CODE_REFRESH_AVATAR, intent);
					finish();
				}
			}
			break;

		case R.id.txtRevoke: //撤消
			laytResultImg.setVisibility(View.GONE);
			cutImageView.setVisibility(View.VISIBLE);
			txtRevoke.setVisibility(View.INVISIBLE);
			txtConfirm.setText("裁剪");
			break;

		default:
			break;
		}
	}
	
	/**
	 * 保存裁剪后的bitmap到本地
	 * @param bitmap
	 * @return boolean
	 */
	private boolean saveBitmap(Bitmap bitmap) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			OutputStream os = null;
			try {
				String curTime = AppTool.dateFormat(System.currentTimeMillis(), "yyyyMMddHHmmss");
				File file = new File(Environment.getExternalStorageDirectory() + Const.APP_IMAGE_PATH, curTime + ".jpg");
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				os = new FileOutputStream(file);
				bitmap.compress(CompressFormat.JPEG, 90, os);
				resultPath = file.getPath();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "保存图片出错", Toast.LENGTH_SHORT).show();
				return false;
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			Toast.makeText(this, "手机存储不可用，请检查存储状态", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	
	@Override
	protected void onDestroy() {
		//释放bitmap占用的资源
		if (origBitmap != null) {
			origBitmap.recycle();
			cutImageView.getOrigBitmap().recycle();
		}
		super.onDestroy();
	}
	
}
