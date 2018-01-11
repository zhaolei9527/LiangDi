package sakura.liangdinvshen.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import org.lzh.framework.updatepluginlib.UpdateBuilder;

import java.util.HashMap;
import java.util.List;

import sakura.bottomtabbar.BottomTabBar;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.GetVersionCode;
import sakura.liangdinvshen.Fragment.MyFragment;
import sakura.liangdinvshen.Fragment.NewsFragment;
import sakura.liangdinvshen.Fragment.RecordFragment;
import sakura.liangdinvshen.Fragment.ShopFragment;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;


public class MainActivity extends BaseActivity {

    private BottomTabBar BottomTabBar;
    private ImageView img_zixun;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initview() {
        img_zixun = (ImageView) findViewById(R.id.img_zixun);
        img_zixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isConnected(context)) {
                    if (ChatClient.getInstance().isLoggedInBefore()) {
                        //已经登录，可以直接进入会话界面
                        Intent intent = new IntentBuilder(context)
                                .setServiceIMNumber("liangdinvshen") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                                .setTitleName("靓蒂女神客服")
                                .setVisitorInfo(ContentFactory.createVisitorInfo(null)
                                        .phone(String.valueOf(SpUtil.get(context, "account", "")))
                                        .name(String.valueOf(SpUtil.get(context, "username", "")))
                                        .nickName(String.valueOf(SpUtil.get(context, "username", ""))))
                                .build();
                        startActivity(intent);
                    } else {
                        //未登录，需要登录后，再进入会话界面
                        dialog = Utils.showLoadingDialog(context);
                        dialog.show();
                        ChatClient.getInstance().login(String.valueOf(SpUtil.get(context, "uid", "")), String.valueOf(SpUtil.get(context, "uid", ""))
                                , new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        dialog.dismiss();
                                        Intent intent = new IntentBuilder(context)
                                                .setServiceIMNumber("liangdinvshen") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                                                .setTitleName("靓蒂女神客服")
                                                .setVisitorInfo(ContentFactory.createVisitorInfo(null)
                                                        .phone(String.valueOf(SpUtil.get(context, "account", "")))
                                                        .name(String.valueOf(SpUtil.get(context, "username", "")))
                                                        .nickName(String.valueOf(SpUtil.get(context, "username", ""))))
                                                .build();
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        dialog.dismiss();
                                        EasyToast.showShort(context, "暂时无法访问客服");
                                    }

                                    @Override
                                    public void onProgress(int i, String s) {

                                    }
                                });
                    }
                } else {
                    EasyToast.showShort(context, "网络未连接");
                }

            }
        });


        BottomTabBar = (BottomTabBar) findViewById(R.id.BottomTabBar);
        BottomTabBar.initFragmentorViewPager(getSupportFragmentManager())
                .addReplaceLayout(R.id.Vp_context)
                .setImgSize(getResources().getDimension(R.dimen.x18), getResources().getDimension(R.dimen.x18))
                .isShowDivider(true)
                .setTabPadding(getResources().getDimension(R.dimen.x5), getResources().getDimension(R.dimen.x2), getResources().getDimension(R.dimen.x3))
                .setChangeColor(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.textColor))
                .addTabItem("资讯", getResources().getDrawable(R.mipmap.zixun1), getResources().getDrawable(R.mipmap.zixun2), NewsFragment.class)
                .addTabItem("记录", getResources().getDrawable(R.mipmap.jilu1), getResources().getDrawable(R.mipmap.jilu2), RecordFragment.class)
                .addTabItem("美学文化街", getResources().getDrawable(R.mipmap.shangpinjie1), getResources().getDrawable(R.mipmap.shangpinjie2), ShopFragment.class)
                .addTabItem("我的", getResources().getDrawable(R.mipmap.wo1), getResources().getDrawable(R.mipmap.wo2), MyFragment.class)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int i, View view) {

                    }
                })
                .commit();

        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setDeniedMessage(getString(R.string.requstPerminssions))
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        getupdata();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(context, R.string.Thepermissionapplicationisrejected, Toast.LENGTH_SHORT).show();
                    }
                });

        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)) {
            return;
        }
    }

    @Override
    protected void initListener() {
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
        getVersion();
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
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
