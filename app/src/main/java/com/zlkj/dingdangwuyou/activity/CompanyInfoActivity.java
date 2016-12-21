package com.zlkj.dingdangwuyou.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.User;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.ImageUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 企业信息（可编辑）
 * Created by btx on 2016/11/21.
 */

public class CompanyInfoActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtRight)
    TextView txtRight;
    @BindView(R.id.txtNameCom)
    EditText txtNameCom;
    @BindView(R.id.txtLegalPerson)
    EditText txtLegalPerson;
    @BindView(R.id.txtCompanyAdd)
    EditText txtCompanyAdd;
    @BindView(R.id.txtRegisterAdd)
    EditText txtRegisterAdd;
    @BindView(R.id.txtType)
    EditText txtType;
    @BindView(R.id.txtIndustry)
    EditText txtIndustry;
    @BindView(R.id.txtIntro)
    EditText txtIntro;

    @BindView(R.id.imgViLicense)
    ImageView imgViLicense;
    @BindView(R.id.imgViLegalPerson)
    ImageView imgViLegalPerson;
    @BindView(R.id.imgViLegalPersonID)
    ImageView imgViLegalPersonID;

    private String id = ""; //企业用户id

    private File cameraFile; // 拍照保存文件(调用相机拍照时)

    private int choosePosition; //当前点击更改图片的位置， 1营业执照、2法人近期照、3法人身份证

    private int uploadSize = 500; //上传图片大小限制（单张），单位KB

    private File licenseFile; //选择的营业执照图片（压缩后）
    private File legalPersonFile; //选择的法人近期照图片（压缩后）
    private File legalPersonIDFile; //选择的法人身份证图片（压缩后）

    private List<File> uploadList; //上传图片的集合(便于退出时清空压缩后的图片)


    @Override
    protected int getContentViewId() {
        return R.layout.activity_company_info;
    }

    @Override
    protected void initData() {
        txtTitle.setText("企业信息");
        txtRight.setText("保存更改");
        txtRight.setVisibility(View.VISIBLE);

        uploadList = new ArrayList<File>();

        id = UserUtil.getUserInfo().getId();
        getCompanyInfo();
    }

    /**
     * 获取企业信息
     */
    private void getCompanyInfo() {
        RequestParams params = new RequestParams();
        params.put("id", id);
        MyHttpClient.getInstance().post(Url.URL_GET_COM_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    if (jsonArr.length() > 0) {
                        User user = GsonUtil.getEntity(jsonArr.getJSONObject(0).toString(), User.class);
                        setData(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, getResources().getString(R.string.request_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pDialog.dismiss();
            }
        });
    }

    /**
     * 保存企业信息
     */
    private void saveCompanyInfo() {
        if (TextUtils.isEmpty(txtNameCom.getText().toString().trim())) {
            Toast.makeText(context, "请输入公司名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtLegalPerson.getText().toString().trim())) {
            Toast.makeText(context, "请输入公司法人", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtCompanyAdd.getText().toString().trim())) {
            Toast.makeText(context, "请输入公司地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtRegisterAdd.getText().toString().trim())) {
            Toast.makeText(context, "请输入注册地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtType.getText().toString().trim())) {
            Toast.makeText(context, "请输入企业类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtIndustry.getText().toString().trim())) {
            Toast.makeText(context, "请输入所属行业", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("cpname", txtNameCom.getText().toString().trim());
        params.put("cpfaren", txtLegalPerson.getText().toString().trim());
        params.put("cpadress", txtCompanyAdd.getText().toString().trim());
        params.put("registadress", txtRegisterAdd.getText().toString().trim());
        params.put("cptype", txtType.getText().toString().trim());
        params.put("industry", txtIndustry.getText().toString().trim());
        params.put("cpcontent", txtIntro.getText().toString().trim());

        if (licenseFile != null && licenseFile.exists()) {
            try {
                params.put("file1", licenseFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (legalPersonFile != null && legalPersonFile.exists()) {
            try {
                params.put("file2", legalPersonFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (legalPersonIDFile != null && legalPersonIDFile.exists()) {
            try {
                params.put("file3", legalPersonIDFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        MyHttpClient.getInstance().post(Url.URL_MODIFY_COM_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.setMessage("正在提交，请稍候....");
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                setResult(Const.RESULT_CODE_SAVE_SUCCEED);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, getResources().getString(R.string.request_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pDialog.dismiss();
            }
        });
    }

    /**
     * 渲染数据
     */
    private void setData(User user) {
        txtNameCom.setText(user.getCpname());
        txtLegalPerson.setText(user.getCpfaren());
        txtCompanyAdd.setText(user.getCpadress());
        txtRegisterAdd.setText(user.getRegistadress());
        txtType.setText(user.getCptype());
        txtIndustry.setText(user.getIndustry());
        txtIntro.setText(user.getCpcontent());

        txtNameCom.setSelection(user.getCpname().length());

        //营业执照
        if (!TextUtils.isEmpty(user.getImg_yyzz())) {
            Glide.with(context)
                    .load(Url.HOST + user.getImg_yyzz())
                    .placeholder(R.mipmap.image_loading)
                    .error(R.mipmap.image_error)
                    .into(imgViLicense);
        }

        //法人近期照片
        if (!TextUtils.isEmpty(user.getImg_frjqzp())) {
            Glide.with(context)
                    .load(Url.HOST + user.getImg_frjqzp())
                    .placeholder(R.mipmap.image_loading)
                    .error(R.mipmap.image_error)
                    .into(imgViLegalPerson);
        }

        //法人身份证扫描件
        if (!TextUtils.isEmpty(user.getImg_frsfz())) {
            Glide.with(context)
                    .load(Url.HOST + user.getImg_frsfz())
                    .placeholder(R.mipmap.image_loading)
                    .error(R.mipmap.image_error)
                    .into(imgViLegalPersonID);
        }
    }

    /**
     * 更改图片对话框
     */
    private void showAvatarOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = new String[] { "拍照", "从相册选择" };
        builder.setTitle("更改图片").setItems(items,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        switch (which) {
                            case 0: // 拍照
                                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                    String curTime = AppTool.dateFormat(System.currentTimeMillis(), "yyyyMMddHHmmss");
                                    // 设置拍照后图片保存的路径
                                    File imagePath = new File(Environment.getExternalStorageDirectory() + Const.APP_IMAGE_PATH);
                                    if (!imagePath.exists()) {
                                        imagePath.mkdirs();
                                    }
                                    cameraFile = new File(imagePath.getPath(), curTime + ".jpg");

                                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
                                    startActivityForResult(intent, Const.REQUEST_CODE_CAMERA);

                                } else {
                                    Toast.makeText(context, "手机存储不可用，请检查存储状态", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            case 1: // 从相册选择
                                intent.setAction(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent, Const.REQUEST_CODE_GALLERY);
                                break;

                            default:
                                break;
                        }
                    }
                });

        builder.show();
    }

    /**
     * 图片压缩并保存
     * @param imgPath 图片路径
     * @return 成功或失败
     */
    private boolean compressAndSave(String imgPath) {
        Bitmap bitmap = ImageUtil.compressImage(imgPath, uploadSize);
        if (bitmap == null) {
            Toast.makeText(context, "压缩图片时出错", Toast.LENGTH_SHORT).show();
            return false;
        }

        String currTime = AppTool.dateFormat(System.currentTimeMillis(), "yyyyMMddHHmmss");
        File file = new File(Environment.getExternalStorageDirectory() + Const.APP_IMAGE_PATH, currTime + ".jpg");

        switch (choosePosition) {
            case 1:
                licenseFile = file;
                break;
            case 2:
                legalPersonFile = file;
                break;
            case 3:
                legalPersonIDFile = file;
                break;
            default:
                break;
        }

        //质量为100时，保存后的图片仍然大于指定大小
        return ImageUtil.saveBitmap(bitmap, 90, file);
    }

    /**
     * 刷新图片（本地）
     */
    private void refreshImage(String imgPath) {
        if (compressAndSave(imgPath)) {
            switch (choosePosition) {
                case 1:
                    Glide.with(context).load(licenseFile).into(imgViLicense);
                    uploadList.add(licenseFile);
                    break;
                case 2:
                    Glide.with(context).load(legalPersonFile).into(imgViLegalPerson);
                    uploadList.add(legalPersonFile);
                    break;
                case 3:
                    Glide.with(context).load(legalPersonIDFile).into(imgViLegalPersonID);
                    uploadList.add(legalPersonIDFile);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_CAMERA) { // 拍照
            if (cameraFile.exists()) {
                refreshImage(cameraFile.getPath());
            }

        } else if (requestCode == Const.REQUEST_CODE_GALLERY) { // 从相册选择
            if (data != null) {
                Uri imgUri = data.getData();
                String imgPath = ImageUtil.getImageAbsolutePath(this, imgUri);
                if (!TextUtils.isEmpty(imgPath) && new File(imgPath).exists()) {
                    refreshImage(imgPath);
                }
            }
        }
    }

    @OnClick({R.id.imgViBack, R.id.txtRight, R.id.imgViLicense, R.id.imgViLegalPerson, R.id.imgViLegalPersonID})
    public void onClick(View view) {
        Intent intent = new Intent(context, ImageBrowseActivity.class);
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.txtRight:
                saveCompanyInfo();
                break;
            case R.id.imgViLicense:
                choosePosition = 1;
                showAvatarOption();
                break;
            case R.id.imgViLegalPerson:
                choosePosition = 2;
                showAvatarOption();
                break;
            case R.id.imgViLegalPersonID:
                choosePosition = 3;
                showAvatarOption();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空压缩后的图片
        for (File file : uploadList) {
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

}
