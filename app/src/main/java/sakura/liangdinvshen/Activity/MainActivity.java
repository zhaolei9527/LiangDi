package sakura.liangdinvshen.Activity;

import android.Manifest;
import android.view.View;
import android.widget.Toast;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

import sakura.bottomtabbar.BottomTabBar;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Fragment.MyFragment;
import sakura.liangdinvshen.Fragment.NewsFragment;
import sakura.liangdinvshen.Fragment.RecordFragment;
import sakura.liangdinvshen.Fragment.ShopFragment;
import sakura.liangdinvshen.R;


public class MainActivity extends BaseActivity {

    private BottomTabBar BottomTabBar;

    @Override
    protected int setthislayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initview() {

        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setDeniedMessage(getString(R.string.requstPerminssions))
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {


                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(context, R.string.Thepermissionapplicationisrejected, Toast.LENGTH_SHORT).show();
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


}
