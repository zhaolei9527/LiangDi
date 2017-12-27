package sakura.liangdinvshen.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Fragment.BackCardListFragment;
import sakura.liangdinvshen.R;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/23
 * 功能描述：
 */
public class BankCardActivity extends BaseActivity {

    private FrameLayout rl_back;
    private ImageView img_add;

    @Override
    protected int setthislayout() {
        return R.layout.activity_backcard_list_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        img_add = (ImageView) findViewById(R.id.img_add);
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddBankCardActivity.class).putExtra("type", "add"));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, new BackCardListFragment()).commit();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BankEvent event) {
        if ("nodata".equals(event.getMsg())) {
            img_add.setVisibility(View.VISIBLE);
        } else {
            img_add.setVisibility(View.GONE);
        }
    }


}
