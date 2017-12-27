package sakura.liangdinvshen.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Fragment.CollectionNewsListFragment;
import sakura.liangdinvshen.Fragment.CollectionShopListFragment;
import sakura.liangdinvshen.R;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/21
 * 功能描述：
 */
public class CollectionActivity extends BaseActivity implements View.OnClickListener {

    private View view_item1;
    private LinearLayout ll_ThePassage;
    private View view_item2;
    private LinearLayout ll_TheCar;
    private LinearLayout ll_title;
    private FrameLayout fl_content;
    private boolean ispage1 = true;
    private FragmentTransaction fragmentTransaction;
    private CollectionNewsListFragment collectionNewsListFragment;
    private CollectionShopListFragment collectionShopListFragment;
    private FrameLayout rl_back;
    private FragmentManager supportFragmentManager;
    private TextView tv_2;
    private TextView tv_1;

    @Override
    protected int setthislayout() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initview() {
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        view_item1 = (View) findViewById(R.id.view_item1);
        ll_ThePassage = (LinearLayout) findViewById(R.id.ll_ThePassage);
        view_item2 = (View) findViewById(R.id.view_item2);
        ll_TheCar = (LinearLayout) findViewById(R.id.ll_TheCar);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        rl_back = (FrameLayout) findViewById(R.id.rl_back);

    }

    @Override
    protected void initListener() {
        ll_TheCar.setOnClickListener(this);
        ll_ThePassage.setOnClickListener(this);
        rl_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        collectionNewsListFragment = new CollectionNewsListFragment();
        collectionShopListFragment = new CollectionShopListFragment();
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_content, collectionNewsListFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_ThePassage:
                if (ispage1) {
                    return;
                }
                tv_1.setTextColor(getResources().getColor(R.color.textAccent));
                tv_2.setTextColor(getResources().getColor(R.color.text333));
                view_item2.setVisibility(View.GONE);
                view_item1.setVisibility(View.VISIBLE);
                ispage1 = true;
                fragmentTransaction = supportFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_content, collectionNewsListFragment).commit();
                break;
            case R.id.ll_TheCar:
                if (ispage1) {
                    tv_1.setTextColor(getResources().getColor(R.color.text333));
                    tv_2.setTextColor(getResources().getColor(R.color.textAccent));
                    view_item2.setVisibility(View.VISIBLE);
                    view_item1.setVisibility(View.GONE);
                    ispage1 = false;
                    fragmentTransaction = supportFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fl_content, collectionShopListFragment).commit();
                }
                break;
            default:
                break;
        }
    }
}
