package sakura.liangdinvshen.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
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
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;


public class MainActivity extends BaseActivity {

    private BottomTabBar BottomTabBar;

    @Override
    protected int setthislayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initview() {
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
