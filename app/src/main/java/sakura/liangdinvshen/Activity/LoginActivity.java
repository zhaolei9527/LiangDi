package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.LoginBean;
import sakura.liangdinvshen.Bean.QQBean;
import sakura.liangdinvshen.Bean.WXBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;


/**
 * Created by 赵磊 on 2017/7/13.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Dialog dialog;
    private ImageView img;
    private ImageView img_yonghu;
    private EditText et_account;
    private RelativeLayout rl;
    private ImageView img_mima;
    private EditText et_password;
    private RelativeLayout rl2;
    private TextView tv_forgetpassworld;
    private TextView tv_register;
    private RelativeLayout rl3;
    private Button btn_login;
    private RelativeLayout rl4;
    private ImageView img_weixin;
    private ImageView img_qq;
    private LinearLayout rl5;
    private int pswminlen = 6;
    private String account;
    private String password;
    private String openid = "";
    private String type = "";

    @Override
    protected void ready() {
        super.ready();
       /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int setthislayout() {
        return R.layout.activcity_login;
    }

    @Override
    protected void initview() {
        initView();
    }

    @Override
    protected void initListener() {
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_forgetpassworld.setOnClickListener(this);
        img_weixin.setOnClickListener(this);
        img_qq.setOnClickListener(this);
        dialog = Utils.showLoadingDialog(context);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setAction("LoginActivityIsStart");
        sendBroadcast(intent);
    }


    private void gotoMain() {
        if ("-1".equals(String.valueOf(SpUtil.get(context, "jieduan", "")))) {
            startActivity(new Intent(context, PhaseActivity.class).putExtra("type", "chage"));
            finish();
        } else {
            startActivity(new Intent(context, MainActivity.class));
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                submit();
                break;
            case R.id.tv_register:
                startActivity(new Intent(context, RegisterActivity.class));
                break;
            case R.id.tv_forgetpassworld:
                startActivity(new Intent(context, ForgetActivity.class));
                break;
            case R.id.img_weixin:
                dialog.show();
                Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                weChat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
                        arg2.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        String s = arg0.getDb().exportData();
                        Log.e("LoginActivity", s);
                        WXBean wxBean = new Gson().fromJson(s, WXBean.class);
                        openid = wxBean.getUnionid();
                        type = "2";
                        SpUtil.putAndApply(context, "wxopenid", openid);
                        getLogin("", "", openid, type);
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                EasyToast.showShort(context, "授权取消");
                            }
                        });
                    }
                });
                weChat.showUser(null);//授权并获取用户信息
                break;
            case R.id.img_qq:
                dialog.show();
                final Platform qq = ShareSDK.getPlatform(QQ.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                qq.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
                        arg2.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        String s = arg0.getDb().exportData();
                        Log.e("LoginActivity", s);
                        QQBean qqBean = new Gson().fromJson(s, QQBean.class);
                        openid = qqBean.getUserID();
                        type = "1";
                        SpUtil.putAndApply(context, "qqopenid", openid);
                        getLogin("", "", openid, type);
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                EasyToast.showShort(context, "授权取消");
                            }
                        });
                    }
                });
                qq.showUser(null);//授权并获取用户信息
                break;
            default:

                break;
        }
    }

    private void initView() {
        img = (ImageView) findViewById(R.id.img);
        img_yonghu = (ImageView) findViewById(R.id.img_yonghu);
        et_account = (EditText) findViewById(R.id.et_account);
        rl = (RelativeLayout) findViewById(R.id.rl);
        img_mima = (ImageView) findViewById(R.id.img_mima);
        et_password = (EditText) findViewById(R.id.et_password);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        tv_forgetpassworld = (TextView) findViewById(R.id.tv_forgetpassworld);
        tv_register = (TextView) findViewById(R.id.tv_register);
        rl3 = (RelativeLayout) findViewById(R.id.rl3);
        btn_login = (Button) findViewById(R.id.btn_login);
        rl4 = (RelativeLayout) findViewById(R.id.rl4);
        img_weixin = (ImageView) findViewById(R.id.img_weixin);
        img_qq = (ImageView) findViewById(R.id.img_qq);
        rl5 = (LinearLayout) findViewById(R.id.rl5);
    }

    private void submit() {
        // validate
        account = et_account.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Utils.isCellphone(account)) {
            Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < pswminlen) {
            Toast.makeText(this, "密码长度最小六位", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

        dialog.show();

        getLogin(account, password, openid, type);

    }

    /**
     * 登录获取
     */
    private void getLogin(final String tel, final String password, final String openid, final String type) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("tel", tel);
        params.put("openid", openid);
        params.put("type", type);
        params.put("password", password);
        Log.e("LoginActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "login/login", "login/login", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("LoginActivity", decode);
                try {

                    LoginBean loginBean = new Gson().fromJson(decode, LoginBean.class);
                    if ("1".equals(loginBean.getCode())) {
                        SpUtil.putAndApply(context, "account", tel);
                        SpUtil.putAndApply(context, "password", password);
                        SpUtil.putAndApply(context, "uid", loginBean.getRes().getId());
                        SpUtil.putAndApply(context, "loginBeanDecode", decode);
                        SpUtil.putAndApply(context, "img", loginBean.getRes().getImg());
                        SpUtil.putAndApply(context, "username", loginBean.getRes().getNi_name());
                        SpUtil.putAndApply(context, "jifen", loginBean.getRes().getJifen());
                        SpUtil.putAndApply(context, "dongjie", loginBean.getRes().getDj_jifen());
                        SpUtil.putAndApply(context, "money", loginBean.getRes().getMoney());
                        SpUtil.putAndApply(context, "Role", loginBean.getRes().getRole());
                        SpUtil.putAndApply(context, "shengri", loginBean.getRes().getShengri());
                        SpUtil.putAndApply(context, "shengao", loginBean.getRes().getShengao());
                        SpUtil.putAndApply(context, "hunyin", loginBean.getRes().getHunyin());
                        SpUtil.putAndApply(context, "chengshi", loginBean.getRes().getCity());
                        SpUtil.putAndApply(context, "jieduan", loginBean.getRes().getStu());
                        SpUtil.putAndApply(context, "Level", loginBean.getRes().getLevel_name());
                        SpUtil.putAndApply(context, "tuijian", loginBean.getRes().getErweima());
                        final LoginBean finalLoginBean = loginBean;
                        ChatClient.getInstance().register(loginBean.getRes().getId(), loginBean.getRes().getId(), new Callback() {
                            @Override
                            public void onSuccess() {
                                if (ChatClient.getInstance().isLoggedInBefore()) {
                                    //已经登录，可以直接进入
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    gotoMain();
                                } else {
                                    //未登录，需要登录后，再进入
                                    ChatClient.getInstance().login(finalLoginBean.getRes().getId(), finalLoginBean.getRes().getId()
                                            , new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                            dialog.dismiss();
                                                            gotoMain();
                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onError(int i, String s) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                            dialog.dismiss();
                                                            gotoMain();
                                                        }
                                                    });

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
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    //已经登录，可以直接进入
                                    gotoMain();
                                } else {
                                    //未登录，需要登录后，再进入
                                    ChatClient.getInstance().login(finalLoginBean.getRes().getId(), finalLoginBean.getRes().getId()
                                            , new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                            dialog.dismiss();
                                                            gotoMain();
                                                        }
                                                    });


                                                }

                                                @Override
                                                public void onError(int i, String s) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                            dialog.dismiss();
                                                            gotoMain();
                                                        }
                                                    });

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

                    } else if ("215".equals(loginBean.getCode())) {
                        dialog.dismiss();
                        btn_login.setText("授权登录");
                        rl4.setVisibility(View.GONE);
                        rl5.setVisibility(View.GONE);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
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
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
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
