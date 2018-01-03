package sakura.liangdinvshen.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Adapter.OrderPageAdapter;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.R;

public class MyOrderActivity extends BaseActivity {

    private FrameLayout rl_back;
    private PagerSlidingTabStrip tabs;
    private ViewPager VpNews_context;
    private List titles = new ArrayList();
    private OrderPageAdapter orderPageAdapter;

    @Override
    protected int setthislayout() {
        return R.layout.activity_my_data;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        VpNews_context = (ViewPager) findViewById(R.id.VpNews_context);
        titles.clear();
        titles.add("全部");
        titles.add("待付款");
        titles.add("待发货");
        titles.add("待收货");
        titles.add("待评价");
        titles.add("已取消");
    }

    @Override
    protected void onResume() {
        super.onResume();
        context.sendBroadcast(new Intent("OrderContentRefresh").putExtra("stu", ""));
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (orderPageAdapter == null) {
            orderPageAdapter = new OrderPageAdapter(context, titles);
            VpNews_context.setAdapter(orderPageAdapter);
        }

        VpNews_context.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    context.sendBroadcast(new Intent("OrderContentRefresh").putExtra("stu", ""));
                } else if (position == 1) {
                    context.sendBroadcast(new Intent("OrderContentRefresh").putExtra("stu", "1"));
                } else if (position == 2) {
                    context.sendBroadcast(new Intent("OrderContentRefresh").putExtra("stu", "2"));
                } else if (position == 3) {
                    context.sendBroadcast(new Intent("OrderContentRefresh").putExtra("stu", "3"));
                } else if (position == 4) {
                    context.sendBroadcast(new Intent("OrderContentRefresh").putExtra("stu", "4"));
                } else if (position == 5) {
                    context.sendBroadcast(new Intent("OrderContentRefresh").putExtra("stu", "-1"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabs.setViewPager(VpNews_context);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        context.sendBroadcast(new Intent("OrderContentRefresh").putExtra("unRegister", "unRegister"));
        super.onDestroy();
    }
}
