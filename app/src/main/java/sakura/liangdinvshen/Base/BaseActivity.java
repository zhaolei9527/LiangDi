package sakura.liangdinvshen.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.badoo.mobile.util.WeakHandler;

import sakura.liangdinvshen.R;

import static sakura.liangdinvshen.Utils.WindowUtil.getStatusBarHeight;

/**
 * Created by 赵磊 on 2017/5/12.
 */

/**
 * ━━━━━━神兽保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * <p>
 * ━━━━━━代码无BUG━━━━━━
 */
public abstract class BaseActivity extends AppCompatActivity {
    public WeakHandler mHandler;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ready();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        int statusBarHeight = getStatusBarHeight(this);

        View mTopView = mContentView.getChildAt(0);
        if (mTopView != null && mTopView.getLayoutParams() != null && mTopView.getLayoutParams().height == statusBarHeight) {
            //避免重复添加 View
            mTopView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            return;
        }
//使 ChildView 预留空间
        if (mTopView != null) {
            ViewCompat.setFitsSystemWindows(mTopView, true);
        }

//添加假 View
        mTopView = new View(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        mTopView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mContentView.addView(mTopView, 0, lp);
        setContentView(setthislayout());
        context = this;
        mHandler = new WeakHandler();
        initview();
        initListener();
        initData();
    }

    protected void ready() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract int setthislayout();

    protected abstract void initview();

    protected abstract void initListener();

    protected abstract void initData();

}