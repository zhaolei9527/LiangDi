package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Bean.CodeBean;
import sakura.liangdinvshen.Bean.ContentBean;
import sakura.liangdinvshen.Bean.QQBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.CodeUtils;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.XieYiDialog;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_account;
    private EditText et_code;
    private ImageView image;
    private EditText et_phonecode;
    private EditText et_password;
    private EditText et_passwordagain;
    private EditText et_tuijian;
    private TextView tv_xieyi;
    private TextView tv_shengming;
    private Button btn_register;
    private RelativeLayout rl4;
    private ImageView img_weixin;
    private ImageView img_qq;
    private String code;
    private TextView tv_changecode;
    private Button btn_getSMScode;
    private CodeUtils codeUtils;
    private Bitmap bitmap;
    private Timer timer;
    private TimerTask task;
    private int time = 60;
    private Context context;
    private String account;
    private String imgCode;
    private String phonecode;
    private String password;
    private String passwordagain;
    private String tuijian;
    private String openid = "";
    private String type = "";
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        context = RegisterActivity.this;
        initView();
        initData();
    }

    private void initData() {
        getXieyi();
        getSheng();
    }

    private void initView() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_code = (EditText) findViewById(R.id.et_code);
        image = (ImageView) findViewById(R.id.image);
        et_phonecode = (EditText) findViewById(R.id.et_phonecode);
        et_password = (EditText) findViewById(R.id.et_password);
        et_passwordagain = (EditText) findViewById(R.id.et_passwordagain);
        et_tuijian = (EditText) findViewById(R.id.et_tuijian);
        tv_xieyi = (TextView) findViewById(R.id.tv_xieyi);
        tv_shengming = (TextView) findViewById(R.id.tv_shengming);
        btn_register = (Button) findViewById(R.id.btn_register);
        rl4 = (RelativeLayout) findViewById(R.id.rl4);
        img_weixin = (ImageView) findViewById(R.id.img_weixin);
        img_qq = (ImageView) findViewById(R.id.img_qq);
        codeUtils = CodeUtils.getInstance();
        bitmap = codeUtils.createBitmap();
        code = codeUtils.getCode();
        image.setImageBitmap(bitmap);
        tv_changecode = (TextView) findViewById(R.id.tv_changecode);
        btn_getSMScode = (Button) findViewById(R.id.btn_getSMScode);
        btn_getSMScode.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_changecode.setOnClickListener(this);
        tv_xieyi.setOnClickListener(this);
        tv_shengming.setOnClickListener(this);
        dialog = Utils.showLoadingDialog(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_weixin:
                dialog.show();
                Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                weChat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
                        arg2.printStackTrace();

                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        String s = arg0.getDb().exportData();
                        Log.e("LoginActivity", s);


                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub

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

                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        String s = arg0.getDb().exportData();
                        try {
                            QQBean qqBean = new Gson().fromJson(s, QQBean.class);
                            openid = qqBean.getUserID();
                            type = "1";
                            getRegister("", "", "", "", openid, type);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub

                    }
                });
                qq.showUser(null);//授权并获取用户信息
                break;
            case R.id.btn_register:
                submit();
                break;
            case R.id.btn_getSMScode:
                account = et_account.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Utils.isCellphone(account)) {
                    Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                imgCode = et_code.getText().toString().trim();
                if (TextUtils.isEmpty(imgCode)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!this.code.equals(imgCode)) {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (time == 60) {
                    getcaptcha(et_account.getText().toString());
                }
                break;
            case R.id.tv_changecode:
                bitmap = codeUtils.createBitmap();
                code = codeUtils.getCode();
                image.setImageBitmap(bitmap);
                break;
            case R.id.tv_xieyi:
                String xieyi = (String) SpUtil.get(context, "xieyi", "");
                if (!TextUtils.isEmpty(xieyi)) {
                    new XieYiDialog(context, R.style.dialog, xieyi, new XieYiDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                            }
                        }
                    }).setTitle("注册协议").show();
                } else {
                    Toast.makeText(context, xieyi, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_shengming:
                String sheng = (String) SpUtil.get(context, "sheng", "");
                if (!TextUtils.isEmpty(sheng)) {
                    new XieYiDialog(context, R.style.dialog, sheng, new XieYiDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                            }
                        }
                    }).setTitle("免责声明").show();
                } else {
                    Toast.makeText(context, sheng, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void getcaptcha(String phone) {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time--;
                        btn_getSMScode.setText("" + time);
                        if (time < 0) {
                            timer.cancel();
                            btn_getSMScode.setText("获取验证码");
                            btn_getSMScode.setEnabled(true);
                            time = 60;
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
        //// TODO: 2017/5/18  发送验证码
        if (Utils.isConnected(context)) {
            getUserPlace(phone);
        } else {
            Toast.makeText(context, getString(R.string.Networkexception), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送验证码
     */
    private void getUserPlace(String phone) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("tel", phone);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "login/regsend", "login/regsend", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("RegisterActivity", decode);
                try {
                    CodeBean codeBean = new Gson().fromJson(decode, CodeBean.class);
                    Toast.makeText(RegisterActivity.this, codeBean.getMsg(), Toast.LENGTH_SHORT).show();
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {

                    } else {
                        time = 0;
                    }
                    decode = null;
                    codeBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    time = 0;
                    Toast.makeText(RegisterActivity.this, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                time = 0;
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 注册协议获取
     */
    private void getXieyi() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "login/xieyi", "login/xieyi", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("RegisterActivity", decode);
                try {
                    ContentBean contentBean = new Gson().fromJson(decode, ContentBean.class);
                    if (1 == contentBean.getCode()) {
                        SpUtil.putAndApply(context, "xieyi", contentBean.getContent());
                    }
                    decode = null;
                    contentBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 免责声明获取
     */
    private void getSheng() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "login/sheng", "login/sheng", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("RegisterActivity", decode);
                try {
                    ContentBean contentBean = new Gson().fromJson(decode, ContentBean.class);
                    if (1 == contentBean.getCode()) {
                        SpUtil.putAndApply(context, "sheng", contentBean.getContent());
                    }
                    decode = null;
                    contentBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 注册提交
     */
    private void submit() {
        // validate
        account = et_account.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.isCellphone(account)) {
            Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        imgCode = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(imgCode)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!this.code.equals(imgCode)) {
            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }

        phonecode = et_phonecode.getText().toString().trim();
        if (TextUtils.isEmpty(phonecode)) {
            Toast.makeText(this, "请输入短信验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        passwordagain = et_passwordagain.getText().toString().trim();
        if (TextUtils.isEmpty(passwordagain)) {
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordagain)) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        tuijian = et_tuijian.getText().toString().trim();
        if (!TextUtils.isEmpty(tuijian)) {
            if (!Utils.isCellphone(tuijian)) {
                Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        getRegister(account, phonecode, password, tuijian, openid, type);

    }

    /**
     * 注册id
     */
    private void getRegister(String phone, String code, String password, String tel2, String openid, String type) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("tel", phone);
        params.put("ecode", code);
        params.put("password", password);
        params.put("cfmpwd", password);
        params.put("openid", openid);
        params.put("type", type);
        if (TextUtils.isEmpty(tel2)) {
            params.put("tel2", tel2);
        }
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "login/regist", "login/regist", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                time = 0;
                String decode = result;
                Log.e("RegisterActivity", decode);
                try {
                    CodeBean codeBean = new Gson().fromJson(decode, CodeBean.class);
                    if (1 == codeBean.getCode()) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, codeBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    decode = null;
                    codeBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                time = 0;
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("login/regsend");
        App.getQueues().cancelAll("login/xieyi");
        App.getQueues().cancelAll("login/sheng");
        App.getQueues().cancelAll("login/regist");
        codeUtils = null;
        code = null;
        bitmap = null;
        task = null;
        if (timer != null) {
            timer = null;
        }
        account = null;
        imgCode = null;
        phonecode = null;
        password = null;
        passwordagain = null;
        tuijian = null;
        System.gc();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }
}
