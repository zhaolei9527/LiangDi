package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.LifePeriodBean;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;


public class MenstrualPeriodActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout rl_back;
    private RelativeLayout rl_jingqi;
    private RelativeLayout rl_zhouqi;
    private ArrayList<String> jingqitems = new ArrayList<>();
    int jingqichangdu = 32;
    private TextView tv_changdu;
    private TextView tv_zhouchang;
    private RelativeLayout rl_jingqishijian;
    private TextView tv_jingqishijian;
    private TextView tv_submit;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_menstural_period;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        rl_jingqi = (RelativeLayout) findViewById(R.id.rl_jingqi);
        rl_zhouqi = (RelativeLayout) findViewById(R.id.rl_zhouqi);
        tv_changdu = (TextView) findViewById(R.id.tv_changdu);
        tv_zhouchang = (TextView) findViewById(R.id.tv_zhouchang);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        rl_jingqishijian = (RelativeLayout) findViewById(R.id.rl_jingqishijian);
        tv_jingqishijian = (TextView) findViewById(R.id.tv_jingqishijian);
        rl_jingqishijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar selectedDate = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                startDate.set(1972, 0, 23);
                Calendar endDate = Calendar.getInstance();
                endDate.set(2050, 11, 28);
                TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                        tv_jingqishijian.setText(DateUtils.getDay(date.getTime()));
                        jin_time = DateUtils.getDay(date.getTime());
                    }
                })
                        //年月日时分秒 的显示与否，不设置则默认全部显示
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setLabel("年", "月", "日", "", "", "")
                        .isCenterLabel(false)
                        .setDividerColor(Color.DKGRAY)
                        .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                        .setCancelColor(getResources().getColor(R.color.text))
                        .setSubmitColor(getResources().getColor(R.color.text))
                        .setTitleText("最后经期时间")
                        .setTitleColor(getResources().getColor(R.color.text))
                        .setContentSize(21)
                        .setDate(selectedDate)
                        .setRangDate(startDate, endDate)
                        .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                        .setDecorView(null)
                        .build();
                pvTime.show();
            }
        });

    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(this);
        rl_jingqi.setOnClickListener(this);
        rl_zhouqi.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        for (int i = 1; i < jingqichangdu; i++) {
            jingqitems.add(i + "天");
        }
        lifePeriod();
    }

    private String period_length = "0";
    private String period_cycle = "0";
    private String jin_time = "0";

    private void ShowPickerView_changdu(String TITLE) {// 弹出选择器
        if (!jingqitems.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if (!period_length.equals(String.valueOf(options1 + 1))) {
                        period_length = String.valueOf(options1 + 1);
                        tv_changdu.setText(String.valueOf(jingqitems.get(options1)));
                    }
                }
            })
                    .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                    .setCancelColor(getResources().getColor(R.color.text))
                    .setSubmitColor(getResources().getColor(R.color.text))
                    .setTitleText(TITLE)
                    .setSelectOptions(6)
                    .setTitleColor(getResources().getColor(R.color.text))
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(jingqitems);//三级选择器
            pvOptions.show();
        }
    }

    private void ShowPickerView_jiange(String TITLE) {// 弹出选择器
        if (!jingqitems.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if (!period_cycle.equals(String.valueOf(options1 + 1))) {
                        period_cycle = String.valueOf(options1 + 1);
                        tv_zhouchang.setText(String.valueOf(jingqitems.get(options1)));
                    }
                }
            })
                    .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                    .setCancelColor(getResources().getColor(R.color.text))
                    .setSubmitColor(getResources().getColor(R.color.text))
                    .setTitleText(TITLE)
                    .setSelectOptions(25)
                    .setTitleColor(getResources().getColor(R.color.text))
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(jingqitems);//三级选择器
            pvOptions.show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_jingqi:
                ShowPickerView_changdu("选择经期长度");
                break;
            case R.id.rl_zhouqi:
                ShowPickerView_jiange("选择经期间隔");
                break;
            case R.id.tv_submit:
                if ("请选择周期长度".equals(tv_zhouchang.getText())) {
                    EasyToast.showShort(context, "请选择周期长度");
                    return;
                }
                if ("请选择经期长度".equals(tv_changdu.getText())) {
                    EasyToast.showShort(context, "请选择经期长度");
                    return;
                }
                if ("请选择经期时间".equals(tv_jingqishijian.getText())) {
                    EasyToast.showShort(context, "请选择经期时间");
                    return;
                }

                if (Utils.isConnected(context)) {
                    dialog = Utils.showLoadingDialog(context);
                    dialog.show();
                    userZhiji();
                } else {
                    EasyToast.showShort(context, "网络未连接~");
                }


                break;
            default:
                break;
        }
    }


    /**
     * 人生状态切换  1：只记经期，，2：我要备孕
     */
    private void userZhiji() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("jin_time", jin_time);
        params.put("period_length", period_length);
        params.put("period_cycle", period_cycle);
        if ("jingqi".equals(getIntent().getStringExtra("type"))) {
            params.put("type", "1");
        } else if ("beiyun".equals(getIntent().getStringExtra("type"))) {
            params.put("type", "2");
        }
        Log.e("RegisterActivity", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "user/zhiji", "user/zhiji", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    dialog.dismiss();
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        if ("change".equals(getIntent().getStringExtra("type"))) {
                            startActivity(new Intent(context, MainActivity.class));
                            finish();
                        } else {
                            EasyToast.showShort(context, "切换成功");
                        }
                        SpUtil.putAndApply(context, "jieduan", getIntent().getStringExtra("type"));
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 经期设置信息获取
     */
    private void lifePeriod() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        if ("jingqi".equals(getIntent().getStringExtra("type"))) {
            params.put("type", "1");
        } else {
            params.put("type", "2");
        }
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/period", "life/period", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    LifePeriodBean lifePeriodBean = new Gson().fromJson(result, LifePeriodBean.class);
                    if ("1".equals(String.valueOf(lifePeriodBean.getStu()))) {
                        period_length = lifePeriodBean.getRes().getPeriod_length();
                        period_cycle = lifePeriodBean.getRes().getPeriod_cycle();
                        tv_changdu.setText(lifePeriodBean.getRes().getPeriod_length());
                        tv_zhouchang.setText(lifePeriodBean.getRes().getPeriod_cycle());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("life/period");
    }
}
