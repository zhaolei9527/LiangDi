package sakura.liangdinvshen.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Fragment.IntegralListFragment;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.EasyToast;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/23
 * 功能描述：
 */
public class IntegralActiivity extends BaseActivity {
    private FrameLayout rl_back;
    private RelativeLayout rl_start_time;
    private RelativeLayout rl_end_time;
    private TimePickerView pvTime;
    private TextView tv_start;
    private TextView tv_end;

    private long start = 0l;
    private long end = 0l;
    private Button btn_search;


    @Override
    protected int setthislayout() {
        return R.layout.activity_integral_list_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        rl_start_time = (RelativeLayout) findViewById(R.id.rl_start_time);
        rl_end_time = (RelativeLayout) findViewById(R.id.rl_end_time);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_end = (TextView) findViewById(R.id.tv_end);
        btn_search = (Button) findViewById(R.id.btn_search);
    }

    private void initTimePicker(String title, final String TYPE) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1972, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 12, 31);
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if ("start".equals(TYPE)) {
                    tv_start.setText(DateUtils.getDay(date.getTime()));
                    start = date.getTime();
                } else {
                    end = date.getTime();
                    tv_end.setText(DateUtils.getDay(date.getTime()));
                }

            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                .setCancelColor(getResources().getColor(R.color.text))
                .setSubmitColor(getResources().getColor(R.color.text))
                .setTitleText(title)
                .setTitleColor(getResources().getColor(R.color.text))
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();

    }


    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rl_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker("开始时间", "start");
                pvTime.show();
            }
        });


        rl_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker("结束时间", "end");
                pvTime.show();

            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (end < start) {
                    EasyToast.showShort(context, "结束时间不能小于开始时间");
                    return;
                }

                if (TextUtils.isEmpty(tv_start.getText().toString()) || TextUtils.isEmpty(tv_end.getText().toString())) {
                    EasyToast.showShort(context, "请选择查询起止时间");
                    return;
                }
                IntegralListFragment IntegralListFragment = new IntegralListFragment();
                Bundle args = new Bundle();
                args.putString("start", tv_start.getText().toString());
                args.putString("end", tv_end.getText().toString());
                IntegralListFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, IntegralListFragment).commit();

            }
        });

    }

    @Override
    protected void initData() {
        IntegralListFragment IntegralListFragment = new IntegralListFragment();
        Bundle args = new Bundle();
        args.putString("start", tv_start.getText().toString());
        args.putString("end", tv_end.getText().toString());
        IntegralListFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, IntegralListFragment).commit();

    }
}
