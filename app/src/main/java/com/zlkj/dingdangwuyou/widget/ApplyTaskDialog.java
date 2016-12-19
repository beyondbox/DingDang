package com.zlkj.dingdangwuyou.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.adapter.PopMenuStringAdapter;
import com.zlkj.dingdangwuyou.adapter.PopMenuTaskTypeAdapter;
import com.zlkj.dingdangwuyou.entity.TaskTypeList;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 申请接任务对话框
 * Created by Botx on 2016/11/25.
 */

public class ApplyTaskDialog implements View.OnClickListener{

    private AlertDialog dialog;
    private Context context;

    private EditText txtName;
    private TextView txtSex;
    private EditText txtPhone;
    private EditText txtIdentity;
    private EditText txtArea;

    private List<String> sexList;
    private PopMenu popMenuSex;
    private OnConfirmListener onConfirmListener;

    public ApplyTaskDialog(Context context) {
        this.context = context;
        init();
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    /**
     * 初始化
     */
    public void init() {
        dialog = new AlertDialog.Builder(context).create();
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_apply_task, null);
        dialog.setView(contentView);

        txtName = (EditText) contentView.findViewById(R.id.txtName);
        txtSex = (TextView) contentView.findViewById(R.id.txtSex);
        txtPhone = (EditText) contentView.findViewById(R.id.txtPhone);
        txtIdentity = (EditText) contentView.findViewById(R.id.txtIdentity);
        txtArea = (EditText) contentView.findViewById(R.id.txtArea);

        TextView txtCancel = (TextView) contentView.findViewById(R.id.txtCancel);
        TextView txtConfirm = (TextView) contentView.findViewById(R.id.txtConfirm);

        txtSex.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        txtConfirm.setOnClickListener(this);

        //个人用户默认显示用户姓名
        if (UserUtil.isLogin()) {
            if (UserUtil.getUserType() == Const.USER_TYPE_PER) {
                txtName.setText(UserUtil.getUserInfo().getName());
                txtName.setSelection(txtName.getText().length());
            }
        }
    }

    /**
     * 显示
     */
    public void show() {
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initSexPopMenu();
            }
        }, 200);
    }

    /**
     * 隐藏
     */
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSex:
                popMenuSex.showAsDropDown(txtSex, 0, 10);
                break;
            case R.id.txtCancel:
                dialog.dismiss();
                break;
            case R.id.txtConfirm:
                if (!isRequiredOk()) {
                    return;
                }
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm(
                            txtName.getText().toString().trim(),
                            txtSex.getText().toString(),
                            txtPhone.getText().toString().trim(),
                            txtIdentity.getText().toString().trim(),
                            txtArea.getText().toString().trim()
                    );
                }
                break;
            default:
                break;
        }
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

    /**
     * 必填项判断
     * @return
     */
    private boolean isRequiredOk() {
        if (TextUtils.isEmpty(txtName.getText().toString().trim())) {
            Toast.makeText(context, "请输入姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(txtPhone.getText().toString().trim())) {
            Toast.makeText(context, "请输入联系方式", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(txtArea.getText().toString().trim())) {
            Toast.makeText(context, "请输入区域", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public interface OnConfirmListener {
        void onConfirm(String name, String sex, String phone, String identity, String area);
    }
}
