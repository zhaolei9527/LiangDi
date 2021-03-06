package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;


/**
 * Created by 赵磊 on 2017/5/24.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout rl_back;
    private EditText et_oldpassword;
    private EditText et_newpassword;
    private EditText et_newpasswordagain;
    private Button btn_save;
    private String oldpassword;
    private String newpassword;
    private Dialog dialog;
    //

    @Override
    protected int setthislayout() {
        return R.layout.change_password_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        et_oldpassword = (EditText) findViewById(R.id.et_oldpassword);
        et_newpassword = (EditText) findViewById(R.id.et_newpassword);
        et_newpasswordagain = (EditText) findViewById(R.id.et_newpasswordagain);
        btn_save = (Button) findViewById(R.id.btn_save);
    }

    @Override
    protected void initListener() {
        btn_save.setOnClickListener(this);
        rl_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_save:
                submit();
            default:
                break;
        }
    }

    private void submit() {
        oldpassword = et_oldpassword.getText().toString().trim();
        if (TextUtils.isEmpty(oldpassword)) {
            EasyToast.showShort(context, "请输入旧密码");
            return;
        }
        newpassword = et_newpassword.getText().toString().trim();
        if (TextUtils.isEmpty(newpassword)) {
            EasyToast.showShort(context, "请输入新密码");
            return;
        }
        String newpasswordagain = et_newpasswordagain.getText().toString().trim();
        if (TextUtils.isEmpty(newpasswordagain)) {
            EasyToast.showShort(context, "请确认新密码");
            return;
        }
        if (!newpassword.equals(newpasswordagain)) {
            EasyToast.showShort(context, "两次输入密码不一致");
            return;
        }

        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            if (!dialog.isShowing()){
                dialog.show();
            }
            changpasswordIndex();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }

    }

    /**
     * 注册协议获取
     */
    private void changpasswordIndex() {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("old_password", oldpassword);
        params.put("new_password", newpassword);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "changpassword/index", "changpassword/index", params, new VolleyInterface(context) {

            private Intent intent;

            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        Toast.makeText(ChangePasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                        SpUtil.clear(context);
                        intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("changpassword/index");
        System.gc();
    }
}
