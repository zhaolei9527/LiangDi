package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

import org.lzh.framework.updatepluginlib.UpdateBuilder;

import java.util.HashMap;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.GetVersionCode;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.CommomDialog;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

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
    private Dialog dialog;

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


    private int getversionCode() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        int versionCode = packInfo.versionCode;
        return versionCode;
    }

    private void getupdata() {

        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            if (!dialog.isShowing()) {
                dialog.show();
                getVersion();
            }
        } else {
            EasyToast.showShort(context, "网络未连接");
        }

    }

    /**
     * 应用更新
     */
    private void getVersion() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "user/app_version", "user/app_version", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    GetVersionCode getVersionCode = new Gson().fromJson(result, GetVersionCode.class);
                    Log.e("App", result);
                    if ("1".equals(String.valueOf(getVersionCode.getCode()))) {
                        int versionCode = getversionCode();
                        int Android_bnum = Integer.parseInt(getVersionCode.getRes().getAz().getVersion());
                        if (versionCode < Android_bnum) {
                            // 可在任意线程进行调用
                            UpdateBuilder.create().check();
                        } else {
                            EasyToast.showShort(context, "已是最新版本");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
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
              //  getupdata();
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
