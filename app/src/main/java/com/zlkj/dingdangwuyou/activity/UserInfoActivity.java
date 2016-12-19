package com.zlkj.dingdangwuyou.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.adapter.PopMenuStringAdapter;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.User;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.ImageUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;
import com.zlkj.dingdangwuyou.widget.PopMenu;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息
 * Created by Botx on 2016/11/26.
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtRight)
    TextView txtRight;
    @BindView(R.id.imgViAvatar)
    ImageView imgViAvatar;

    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.txtSex)
    TextView txtSex;
    @BindView(R.id.txtNation)
    EditText txtNation;
    @BindView(R.id.txtHeight)
    EditText txtHeight;
    @BindView(R.id.txtMarriage)
    TextView txtMarriage;
    @BindView(R.id.txtPolitics)
    EditText txtPolitics;
    @BindView(R.id.txtNativePlace)
    EditText txtNativePlace;
    @BindView(R.id.txtEducation)
    EditText txtEducation;
    @BindView(R.id.txtSchool)
    EditText txtSchool;
    @BindView(R.id.txtAddress)
    EditText txtAddress;

    private String userId;
    private File avatarFile; // 用户头像文件(调用相机拍照时)

    private String imagePath = ""; //用户更改的头像路径

    private List<String> sexList;
    private PopMenu popMenuSex;

    private List<String> marriageList;
    private PopMenu popMenuMarriage;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initData() {
        txtTitle.setText("个人信息");
        txtRight.setText("保存更改");
        txtRight.setVisibility(View.VISIBLE);

        userId = UserUtil.getUserInfo().getId();
        getUserInfo();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initSexPopMenu();
                initMarriagePopMenu();
            }
        }, 200);
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        RequestParams params = new RequestParams();
        params.put("id", userId);

        MyHttpClient.getInstance().post(Url.URL_GET_PER_INFO, params, new AsyncHttpResponseHandler() {
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
                        JSONObject jsonObj = jsonArr.getJSONObject(0);
                        User user = GsonUtil.getEntity(jsonObj.toString(), User.class);
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
     * 保存用户信息
     */
    private void saveUserInfo() {
        if (TextUtils.isEmpty(txtName.getText().toString().trim())) {
            Toast.makeText(context, "请输入您的姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams params = new RequestParams();
        params.put("id", userId);
        params.put("name", txtName.getText().toString().trim());
        params.put("sex", txtSex.getText().toString().trim());
        params.put("nation", txtNation.getText().toString().trim());
        params.put("jiguan", txtNativePlace.getText().toString().trim());
        params.put("height", txtHeight.getText().toString().trim());
        params.put("marital", txtMarriage.getText().toString().trim());
        params.put("politics", txtPolitics.getText().toString().trim());
        params.put("education", txtEducation.getText().toString().trim());
        params.put("school", txtSchool.getText().toString().trim());
        params.put("adress", txtAddress.getText().toString().trim());

        if (!TextUtils.isEmpty(imagePath)) {
            try {
                params.put("file", new File(imagePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        MyHttpClient.getInstance().post(Url.URL_MODIFY_PER_INFO, params, new AsyncHttpResponseHandler() {
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
     * @param user
     */
    private void setData(User user) {
        txtName.setText(user.getName());
        txtSex.setText(user.getSex());
        txtNation.setText(user.getNation());
        txtHeight.setText(user.getHeight());
        txtMarriage.setText(user.getMarital());
        txtPolitics.setText(user.getPolitics());
        txtNativePlace.setText(user.getJiguan());
        txtEducation.setText(user.getEducation());
        txtSchool.setText(user.getSchool());
        txtAddress.setText(user.getAdress());

        if (!TextUtils.isEmpty(user.getImgurl())) {
            Glide.with(context)
                    .load(Url.HOST + user.getImgurl())
                    .placeholder(R.mipmap.image_loading)
                    .error(R.mipmap.image_error)
                    .into(imgViAvatar);
        }

        txtName.setSelection(user.getName().length());
    }

    private void initSexPopMenu() {
        sexList = new ArrayList<String>();
        sexList.add("男");
        sexList.add("女");

        View contentView = LayoutInflater.from(context).inflate(R.layout.commen_popmenu_content, null);
        ListView lvData = (ListView) contentView.findViewById(R.id.lvData);
        popMenuSex = new PopMenu(txtSex.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, contentView,
                lvData, new PopMenuStringAdapter(context, sexList));

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popMenuSex.dismiss();
                txtSex.setText(sexList.get(position));
            }
        });
    }

    private void initMarriagePopMenu() {
        marriageList= new ArrayList<String>();
        marriageList.add("未婚");
        marriageList.add("已婚");

        View contentView = LayoutInflater.from(context).inflate(R.layout.commen_popmenu_content, null);
        ListView lvData = (ListView) contentView.findViewById(R.id.lvData);
        popMenuMarriage = new PopMenu(txtMarriage.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, contentView,
                lvData, new PopMenuStringAdapter(context, marriageList));

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popMenuMarriage.dismiss();
                txtMarriage.setText(marriageList.get(position));
            }
        });
    }

    /**
     * 更改头像对话框
     */
    private void showAvatarOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = new String[] { "拍照", "从相册选择" };
        builder.setTitle("更改头像").setItems(items,
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
                                    avatarFile = new File(imagePath.getPath(), curTime + ".jpg");

                                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(avatarFile));
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

    @OnClick({R.id.imgViBack, R.id.imgViAvatar, R.id.txtRight, R.id.txtSex, R.id.txtMarriage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.imgViAvatar:
                showAvatarOption();
                break;
            case R.id.txtRight:
                saveUserInfo();
                break;
            case R.id.txtSex:
                popMenuSex.showAsDropDown(txtSex, 0, 10);
                break;
            case R.id.txtMarriage:
                popMenuMarriage.showAsDropDown(txtMarriage, 0, 10);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_CAMERA) { // 拍照
            if (avatarFile.exists()) {
                Intent intent = new Intent(this, CutImageActivity.class);
                intent.putExtra("path", avatarFile.getPath());
                startActivityForResult(intent, 0);
            }

        } else if (requestCode == Const.REQUEST_CODE_GALLERY) { // 从相册选择
            if (data != null) {
                Uri imgUri = data.getData();
                String imgPath = ImageUtil.getImageAbsolutePath(this, imgUri);
                if (!TextUtils.isEmpty(imgPath) && new File(imgPath).exists()) {
                    Intent intent = new Intent(this, CutImageActivity.class);
                    intent.putExtra("path", imgPath);
                    startActivityForResult(intent, 0);
                }
            }

        } else if (resultCode == Const.RESULT_CODE_REFRESH_AVATAR) { //刷新用户头像
            imagePath = data.getStringExtra(Const.KEY_IMAGE_PATH);
            Glide.with(context).load(imagePath).into(imgViAvatar);
        }
    }

}
