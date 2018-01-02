package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.CommomDialog;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/24
 * 功能描述：
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {


    private TextView tv_change_psw;
    private TextView tv_upapp;
    private TextView tv_lookus;
    private TextView tv_help;
    private FrameLayout rl_back;
    private TextView tv_exit;

    @Override
    protected int setthislayout() {
        return R.layout.activity_setting_layout;
    }

    @Override
    protected void initview() {
        tv_change_psw = (TextView) findViewById(R.id.tv_change_psw);
        tv_upapp = (TextView) findViewById(R.id.tv_upapp);
        tv_lookus = (TextView) findViewById(R.id.tv_lookus);
        tv_help = (TextView) findViewById(R.id.tv_help);
        tv_exit = (TextView) findViewById(R.id.tv_exit);

        rl_back = (FrameLayout) findViewById(R.id.rl_back);
    }

    @Override
    protected void initListener() {
        tv_change_psw.setOnClickListener(this);
        tv_upapp.setOnClickListener(this);
        tv_lookus.setOnClickListener(this);
        tv_help.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        rl_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit:
                new CommomDialog(context, R.style.dialog, "您确定退出登录么？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, final boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();


                            if (Utils.isConnected(context)) {
                                final Dialog dialog1 = Utils.showLoadingDialog(context);
                                dialog1.show();
                                //第一个参数为是否解绑推送的devicetoken
                                ChatClient.getInstance().logout(true, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        dialog1.dismiss();
                                        SpUtil.clear(context);
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        dialog1.dismiss();
                                        SpUtil.clear(context);
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onProgress(int i, String s) {

                                    }
                                });
                            } else {
                                EasyToast.showShort(context, "网络未连接");
                            }


                        }
                    }
                }).setTitle("提示").show();
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_change_psw:
                startActivity(new Intent(context, ChangePasswordActivity.class));
                break;
            case R.id.tv_upapp:

                break;
            case R.id.tv_lookus:
                startActivity(new Intent(context, WhitUsDetailsActivity.class).putExtra("type", "withus"));
                break;
            case R.id.tv_help:
                startActivity(new Intent(context, WhitUsDetailsActivity.class).putExtra("type", "help"));
                break;
            default:

                break;

        }
    }
}
