package sakura.liangdinvshen.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/25
 * 功能描述：
 */
public class EmployeesActivity extends BaseActivity implements View.OnClickListener {


    private FrameLayout rl_back;
    private SimpleDraweeView SimpleDraweeView;
    private TextView tv_name;
    private TextView tv_xiaofei;
    private TextView tv_xiaofeiguanli;
    private TextView tv_yuangong;
    private TextView tv_shezhi;

    @Override
    protected int setthislayout() {
        return R.layout.activity_employees;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        SimpleDraweeView = (SimpleDraweeView) findViewById(R.id.SimpleDraweeView);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_xiaofei = (TextView) findViewById(R.id.tv_xiaofei);
        tv_xiaofeiguanli = (TextView) findViewById(R.id.tv_xiaofeiguanli);
        tv_yuangong = (TextView) findViewById(R.id.tv_yuangong);
        tv_shezhi = (TextView) findViewById(R.id.tv_shezhi);
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(this);
        tv_shezhi.setOnClickListener(this);
        tv_xiaofei.setOnClickListener(this);
        tv_xiaofeiguanli.setOnClickListener(this);
        tv_yuangong.setOnClickListener(this);
        tv_name.setText(String.valueOf(SpUtil.get(context, "username", "")));
        SimpleDraweeView.setImageURI(UrlUtils.URL + SpUtil.get(context, "img", ""));

        String role = (String) SpUtil.get(context, "Role", "");
        if ("2".equals(role)) {
            tv_yuangong.setVisibility(View.GONE);
            tv_shezhi.setVisibility(View.GONE);
        } else {
            tv_yuangong.setVisibility(View.VISIBLE);
            tv_shezhi.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_shezhi:
                startActivity(new Intent(context, ServiceSettingActivity.class));
                break;
            case R.id.tv_xiaofei:
                startActivity(new Intent(context, ConsumptionActivity.class));
                break;
            case R.id.tv_xiaofeiguanli:
                startActivity(new Intent(context, XfListActivity.class));
                break;
            case R.id.tv_yuangong:
                startActivity(new Intent(context, StaffManagementActivity.class));
                break;
            default:

                break;

        }
    }
}
