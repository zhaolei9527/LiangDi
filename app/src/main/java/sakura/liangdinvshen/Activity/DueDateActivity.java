package sakura.liangdinvshen.Activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.LifePeriodBean;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

public class DueDateActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout rl_back;
    private ImageView img_chanqi;
    private TextView tv_chanqitime;
    private RelativeLayout rl_chanqi;
    private TimePickerView pvTime;

    private String pregnant_expected = "";

    @Override
    protected int setthislayout() {
        return R.layout.due_date_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        img_chanqi = (ImageView) findViewById(R.id.img_chanqi);
        tv_chanqitime = (TextView) findViewById(R.id.tv_chanqitime);
        rl_chanqi = (RelativeLayout) findViewById(R.id.rl_chanqi);
        initTimePicker();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1972, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 11, 28);
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tv_chanqitime.setText(getTime(date));
                pregnant_expected = getTime(date);
                lifeDoExpected();
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                .setCancelColor(getResources().getColor(R.color.text))
                .setSubmitColor(getResources().getColor(R.color.text))
                .setTitleText("选择预产期")
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
        rl_back.setOnClickListener(this);
        rl_chanqi.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        lifePeriod();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_chanqi:
                pvTime.show();
                break;
            default:

                break;
        }
    }

    /**
     * 经期设置信息获取
     */
    private void lifePeriod() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("type", "3");
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/period", "life/period", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    LifePeriodBean lifePeriodBean = new Gson().fromJson(result, LifePeriodBean.class);
                    if ("1".equals(String.valueOf(lifePeriodBean.getStu()))) {
                        if (!TextUtils.isEmpty(lifePeriodBean.getRes().getPregnant_expected())) {
                            pregnant_expected = lifePeriodBean.getRes().getPregnant_expected();
                            tv_chanqitime.setText(DateUtils.getDay(Long.parseLong(lifePeriodBean.getRes().getPregnant_expected()) * 1000));
                        }
                    }
                    lifePeriodBean = null;
                    result = null;
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

    /**
     * 记录-只记经期-周期长度
     */
    private void lifeDoExpected() {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("pregnant_expected", pregnant_expected);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/do_expected", "life/do_expected", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(context, "切换成功");
                    } else {
                        EasyToast.showShort(context, "设置失败");
                    }
                    result = null;
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


}
