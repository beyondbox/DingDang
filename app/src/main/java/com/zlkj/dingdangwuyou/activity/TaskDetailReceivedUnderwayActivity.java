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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.ReceivedTask;
import com.zlkj.dingdangwuyou.entity.Receiver;
import com.zlkj.dingdangwuyou.entity.Task;
import com.zlkj.dingdangwuyou.entity.TaskTypeList;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.ImageUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 进行中的任务详情(我接受的)
 * Created by btx on 2016/11/25.
 */

public class TaskDetailReceivedUnderwayActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.txtType)
    TextView txtType;
    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.txtContent)
    EditText txtContent;
    @BindView(R.id.txtFinishTime)
    TextView txtFinishTime;
    @BindView(R.id.txtIdentity)
    EditText txtIdentity;
    @BindView(R.id.txtArea)
    EditText txtArea;
    @BindView(R.id.txtCount)
    EditText txtCount;
    @BindView(R.id.txtPacketMoney)
    EditText txtPacketMoney;
    @BindView(R.id.txtPacketNum)
    EditText txtPacketNum;
    @BindView(R.id.txtPhone)
    EditText txtPhone;
    @BindView(R.id.txtMessage)
    EditText txtMessage;
    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.txtHandle)
    TextView txtHandle;
    @BindView(R.id.txtReceiver)
    TextView txtReceiver;

    private Receiver receiver;
    private Task task;

    private File cameraFile; //拍照文件
    private File uploadFile; //最终上传的图片文件
    private int uploadSize = 800; //上传图片大小限制，单位KB

    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_detail_received_underway;
    }

    @Override
    protected void initData() {
        txtTitle.setText("任务详情");

        ReceivedTask receivedTask = (ReceivedTask) getIntent().getSerializableExtra(Const.KEY_OBJECT);
        receiver = receivedTask.getReceiver();
        task = receivedTask.getTask();

        setData();
    }


    /**
     * 渲染数据
     */
    private void setData() {
        txtType.setText(getTaskType());
        txtName.setText(task.getT_name());
        txtContent.setText(task.getT_content());
        txtFinishTime.setText(AppTool.dateFormat(task.getT_finish_time().getTime(), "yyyy-MM-dd"));
        txtIdentity.setText(task.getT_status());
        txtArea.setText(task.getT_area());
        txtCount.setText(task.getT_num());
        txtPacketMoney.setText(task.getT_money() + "     (单位:元)");
        txtPacketNum.setText(task.getT_hbnum());
        txtPhone.setText(task.getT_contact());
        txtMessage.setText(task.getT_words());

        /*if (!TextUtils.isEmpty(task.getPicture())) {
            Glide.with(context)
                    .load(Url.HOST + task.getPicture())
                    .placeholder(R.mipmap.image_loading)
                    .error(R.mipmap.image_error)
                    .into(imageView);
        }*/
    }


    /**
     * 根据任务类型id获取类型名称
     * @return
     */
    private String getTaskType() {
        String result = "";
        for (TaskTypeList.TaskType taskType : TaskTypeList.list) {
            if (taskType.getId().equals(task.getCa_id())) {
                result = taskType.getName();
                break;
            }
        }

        return result;
    }

    /**
     * 确认上传
     */
    private void upload() {
        RequestParams params = new RequestParams();
        params.put("jlname", receiver.getJlname());
        params.put("jlsex", receiver.getJlsex());
        params.put("jlarea", receiver.getJlarea());
        params.put("jltel", receiver.getJltel());
        params.put("ren_id", task.getId());
        params.put("jl_id", receiver.getId());
        params.put("jl_ren_id", UserUtil.getUserInfo().getId());

        try {
            params.put("file", uploadFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        MyHttpClient.getInstance().post(Url.URL_TASK_UPLOAD, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.setMessage("正在提交，请稍候....");
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
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
     * 上传图片对话框
     */
    private void showUploadOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = new String[] { "拍照", "从相册中选择" };
        builder.setTitle("上传图片").setItems(items,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        switch (which) {
                            case 0: // 拍照
                                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                    String currTime = AppTool.dateFormat(System.currentTimeMillis(), "yyyyMMddHHmmss");
                                    // 设置拍照后图片保存的路径
                                    File imagePath = new File(Environment.getExternalStorageDirectory() + Const.APP_IMAGE_PATH);
                                    if (!imagePath.exists()) {
                                        imagePath.mkdirs();
                                    }
                                    cameraFile = new File(imagePath.getPath(), currTime + ".jpg");

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
        uploadFile = new File(Environment.getExternalStorageDirectory() + Const.APP_IMAGE_PATH, currTime + ".jpg");
        //质量为100时，保存后的图片仍然大于指定大小
        return ImageUtil.saveBitmap(bitmap, 90, uploadFile);
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Const.REQUEST_CODE_CAMERA) { //拍照
            if (cameraFile.exists()) {
                if (compressAndSave(cameraFile.getPath())) {
                    upload();
                }
            }

        } else if (requestCode == Const.REQUEST_CODE_GALLERY) { //从相册选择
            if (data != null) {
                Uri imgUri = data.getData();
                String imgPath = ImageUtil.getImageAbsolutePath(this, imgUri);
                if (!TextUtils.isEmpty(imgPath) && new File(imgPath).exists()) {
                    if (compressAndSave(imgPath)) {
                        upload();
                    }
                }
            }
        }
    }

    @OnClick({R.id.imgViBack, R.id.txtHandle, R.id.txtReceiver})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgViBack: //返回
                finish();
                break;
            case R.id.txtHandle: //上传图片
                if (TextUtils.isEmpty(task.getPicture())) {
                    showUploadOption();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uploadFile != null && uploadFile.exists()) {
            uploadFile.delete();
        }
    }
}
