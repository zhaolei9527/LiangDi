package sakura.liangdinvshen.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Fragment.StaffManagementListFragment;
import sakura.liangdinvshen.R;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/25
 * 功能描述：
 */
public class StaffManagementActivity extends BaseActivity {

    private FrameLayout rl_back;
    private ImageView img_add;

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, new StaffManagementListFragment()).commit();
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_staffmanagement;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        img_add = (ImageView) findViewById(R.id.img_add);
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
                startActivity(new Intent(context, AddStaffManagmentActivity.class));
            }
        });
    }


    @Override
    protected void initData() {

    }

}
