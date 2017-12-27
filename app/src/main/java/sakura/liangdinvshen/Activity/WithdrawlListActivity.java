package sakura.liangdinvshen.Activity;

import android.view.View;
import android.widget.FrameLayout;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Fragment.WithdrawListFragment;
import sakura.liangdinvshen.R;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/24
 * 功能描述：
 */
public class WithdrawlListActivity extends BaseActivity {

    private FrameLayout rl_back;

    @Override
    protected int setthislayout() {
        return R.layout.activity_backcard_list_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, new WithdrawListFragment()).commit();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
