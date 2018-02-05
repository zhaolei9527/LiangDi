package sakura.liangdinvshen.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.LoginBean;
import sakura.liangdinvshen.Bean.yinDaoBean;
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
    private SimpleDraweeView img;
    private ImageView img_yindao;

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
        return R.layout.flash_layout;
    }

    @Override
    protected void initview() {
        img = (SimpleDraweeView) findViewById(R.id.img);
        img_yindao = (ImageView) findViewById(R.id.img_yindao);

        try {
            Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()
                    .getPath() + "/cache/yindao.jpg");
            img_yindao.setBackground(new BitmapDrawable(bmp));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setDeniedMessage(getString(R.string.requstPerminssions))
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        yinDao();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(context, R.string.Thepermissionapplicationisrejected, Toast.LENGTH_SHORT).show();
                    }
                });


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
                delayGoToLogin();
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
            getLogin("", "", "2", wxopenid);
        } else {
            delayGoToLogin();
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
        Log.e("LoginActivity", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "login/login", "login/login", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("LoginActivity", decode);
                try {
                    LoginBean loginBean = new Gson().fromJson(decode, LoginBean.class);
                    if ("1".equals(loginBean.getCode())) {
                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
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
                        delayGoToLogin();
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
                delayGoToLogin();
            }
        });
    }

    /**
     * 获取引导画面
     */
    private void yinDao() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "index/yin_dao", "index/yin_dao", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("FlashActivity", result);
                try {
                    yinDaoBean yindaoBean = new Gson().fromJson(result, yinDaoBean.class);
                    Postprocessor redMeshPostprocessor = new BasePostprocessor() {
                        @Override
                        public String getName() {
                            return "redMeshPostprocessor";
                        }

                        @Override
                        public void process(final Bitmap bitmap) {
                            App.pausableThreadPoolExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    File file = new File(Environment.getExternalStorageDirectory()
                                            .getPath() + "/cache/");
                                    file.mkdirs();
                                    file = new File(Environment.getExternalStorageDirectory()
                                            .getPath() + "/cache/yindao.jpg");// 保存到sdcard根目录下，文件名为share_pic.png
                                    Log.i("CXC", Environment.getExternalStorageDirectory().getPath());
                                    FileOutputStream fos = null;
                                    try {
                                        fos = new FileOutputStream(file);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos);
                                    } catch (FileNotFoundException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    try {
                                        fos.close();
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    };
                    ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(UrlUtils.URL + yindaoBean.getYd_img()))
                            .setPostprocessor(redMeshPostprocessor)
                            .build();
                    PipelineDraweeController controller = (PipelineDraweeController)
                            Fresco.newDraweeControllerBuilder()
                                    .setImageRequest(request)
                                    .setOldController(img.getController())
                                    .build();
                    img.setController(controller);
                } catch (Exception e) {

                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();

            }
        });
    }


    private void gotoMain() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("-1".equals(String.valueOf(SpUtil.get(context, "jieduan", "")))) {
                    startActivity(new Intent(context, PhaseActivity.class).putExtra("type", "chage"));
                    finish();
                } else {
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                }
            }
        }, 2000);
    }

    private void delayGoToLogin() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        }, 2000);
    }


}
