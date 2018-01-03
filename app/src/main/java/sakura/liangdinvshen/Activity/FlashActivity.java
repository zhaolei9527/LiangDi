package sakura.liangdinvshen.Activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.LoginBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * Created by 赵磊 on 2017/7/13.
 */

public class FlashActivity extends BaseActivity {

    private String account;
    private String password;
    private String qqopenid;
    private String wxopenid;

    @Override
    protected void ready() {
        super.ready();
//       /*set it to be no title*/
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//       /*set it to be full screen*/
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int setthislayout() {
        return R.layout.flash_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        boolean connected = Utils.isConnected(context);
        if (connected) {
            AutoLogin();
        } else {
            if (context != null) {
                Toast.makeText(context, "网路未连接", Toast.LENGTH_SHORT).show();
                gotoLogin();
            }
        }
    }


    private void AutoLogin() {
        account = (String) SpUtil.get(context, "account", "");
        password = (String) SpUtil.get(context, "password", "");
        qqopenid = (String) SpUtil.get(context, "qqopenid", "");
        wxopenid = (String) SpUtil.get(context, "wxopenid", "");

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
            getLogin(account, password, "", "");
        } else if (!TextUtils.isEmpty(qqopenid)) {
            getLogin("", "", "1", qqopenid);
        } else if (!TextUtils.isEmpty(wxopenid)) {
            getLogin("", "", "2", qqopenid);
        } else {
            gotoLogin();
        }
    }

    /**
     * 登录获取
     */
    private void getLogin(final String tel, final String password, String type, String openid) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("tel", tel);
        params.put("password", password);
        params.put("type", type);
        params.put("openid", openid);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "login/login", "login/login", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("LoginActivity", decode);
                try {
                    LoginBean loginBean = new Gson().fromJson(decode, LoginBean.class);
                    if ("1".equals(loginBean.getCode())) {
                        Toast.makeText(context, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                        SpUtil.putAndApply(context, "account", tel);
                        SpUtil.putAndApply(context, "password", password);
                        SpUtil.putAndApply(context, "uid", loginBean.getRes().getId());
                        SpUtil.putAndApply(context, "loginBeanDecode", decode);
                        SpUtil.putAndApply(context, "img", loginBean.getRes().getImg());
                        SpUtil.putAndApply(context, "username", loginBean.getRes().getNi_name());
                        SpUtil.putAndApply(context, "jifen", loginBean.getRes().getJifen());
                        SpUtil.putAndApply(context, "money", loginBean.getRes().getMoney());
                        SpUtil.putAndApply(context, "Role", loginBean.getRes().getRole());
                        SpUtil.putAndApply(context, "shengri", loginBean.getRes().getShengri());
                        SpUtil.putAndApply(context, "shengao", loginBean.getRes().getShengao());
                        SpUtil.putAndApply(context, "hunyin", loginBean.getRes().getHunyin());
                        SpUtil.putAndApply(context, "chengshi", loginBean.getRes().getCity());
                        SpUtil.putAndApply(context, "jieduan", loginBean.getRes().getStu());
                        //注册
                        final LoginBean finalLoginBean = loginBean;
                        ChatClient.getInstance().register(loginBean.getRes().getId(), loginBean.getRes().getId(), new Callback() {
                            @Override
                            public void onSuccess() {
                                if (ChatClient.getInstance().isLoggedInBefore()) {
                                    //已经登录，可以直接进入
                                    gotoMain();
                                } else {
                                    //未登录，需要登录后，再进入
                                    ChatClient.getInstance().login(finalLoginBean.getRes().getId(), finalLoginBean.getRes().getId()
                                            , new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    gotoMain();
                                                }

                                                @Override
                                                public void onError(int i, String s) {
                                                    gotoMain();
                                                }

                                                @Override
                                                public void onProgress(int i, String s) {

                                                }
                                            });
                                }
                            }

                            @Override
                            public void onError(int i, String s) {
                                if (ChatClient.getInstance().isLoggedInBefore()) {
                                    //已经登录，可以直接进入
                                    gotoMain();
                                } else {
                                    //未登录，需要登录后，再进入
                                    ChatClient.getInstance().login(finalLoginBean.getRes().getId(), finalLoginBean.getRes().getId()
                                            , new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    gotoMain();
                                                }

                                                @Override
                                                public void onError(int i, String s) {
                                                    gotoMain();
                                                }

                                                @Override
                                                public void onProgress(int i, String s) {

                                                }
                                            });
                                }
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });

                    } else {
                        Toast.makeText(context, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                        gotoLogin();
                    }
                    decode = null;
                    loginBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                gotoLogin();
            }
        });
    }

    private void gotoMain() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

    private void gotoLogin() {
        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.queues.cancelAll("login/login");
        account = null;
        password = null;
        System.gc();
    }

}
