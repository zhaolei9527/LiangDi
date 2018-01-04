package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.text.TextUtils;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.LifePeriodBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

public class LaMaActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout rl_back;
    private TimePickerView pvTime;
    private TextView tv_baby_sex;
    private RelativeLayout rl_baby_sex;
    private TextView tv_baby_born_time;
    private RelativeLayout rl_baby_born;
    private TextView tv_jingqi;
    private RelativeLayout rl_jingqi;
    private TextView tv_zhouqi;
    private RelativeLayout rl_zhouqi;
    private ArrayList<String> jingqitems = new ArrayList<>();
    private ArrayList<String> babysexs = new ArrayList<>();
    int jingqichangdu = 32;
    private String period_length = "";
    private String period_cycle = "";
    private String baby_birthday = "";
    private String baby_sex = "";
    private String jin_time = "";
    private RelativeLayout rl_jingqishijian;
    private TextView tv_jingqishijian;
    private TextView tv_submit;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_lama_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_baby_sex = (TextView) findViewById(R.id.tv_baby_sex);
        tv_baby_born_time = (TextView) findViewById(R.id.tv_baby_born_time);
        tv_jingqi = (TextView) findViewById(R.id.tv_jingqi);
        tv_zhouqi = (TextView) findViewById(R.id.tv_zhouqi);
        rl_baby_sex = (RelativeLayout) findViewById(R.id.rl_baby_sex);
        rl_baby_born = (RelativeLayout) findViewById(R.id.rl_baby_born);
        rl_jingqi = (RelativeLayout) findViewById(R.id.rl_jingqi);
        rl_zhouqi = (RelativeLayout) findViewById(R.id.rl_zhouqi);
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
        endDate.set(2050, 11, 28);
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                baby_birthday = getTime(date);
                tv_baby_born_time.setText(getTime(date));
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

    private void ShowPickerView_changdu(String TITLE) {// 弹出选择器
        if (!jingqitems.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    tv_jingqi.setText(jingqitems.get(options1));
                    period_length = String.valueOf(options1 + 1);
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
                    tv_zhouqi.setText(jingqitems.get(options1));
                    period_cycle = String.valueOf(options1 + 1);
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

    private void ShowPickerView_babysex(String TITLE) {// 弹出选择器
        if (!babysexs.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    tv_baby_sex.setText(babysexs.get(options1));
                    baby_sex = String.valueOf(options1 + 1);
                }
            })
                    .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                    .setCancelColor(getResources().getColor(R.color.text))
                    .setSubmitColor(getResources().getColor(R.color.text))
                    .setTitleText(TITLE)
                    .setTitleColor(getResources().getColor(R.color.text))
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(babysexs);//三级选择器
            pvOptions.show();
        }
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(this);
        rl_baby_born.setOnClickListener(this);
        rl_jingqi.setOnClickListener(this);
        rl_zhouqi.setOnClickListener(this);
        rl_baby_sex.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        for (int i = 1; i < jingqichangdu; i++) {
            jingqitems.add(i + "天");
        }
        babysexs.add("小王子");
        babysexs.add("小公主");
        lifePeriod();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_baby_born:
                //选择宝宝出生日
                pvTime.show();
                break;
            case R.id.rl_jingqi:
                //选择经期长度
                ShowPickerView_changdu("选择经期长度");
                break;
            case R.id.rl_zhouqi:
                //选择经期间隔
                ShowPickerView_jiange("选择经期间隔");
                break;
            case R.id.rl_baby_sex:
                //选择宝宝性别
                ShowPickerView_babysex("选择宝宝性别");
                break;
            case R.id.tv_submit:
                if ("".equals(tv_zhouqi.getText())) {
                    EasyToast.showShort(context, "请选择周期长度");
                    return;
                }
                if ("".equals(tv_jingqi.getText())) {
                    EasyToast.showShort(context, "请选择经期长度");
                    return;
                }
                if ("".equals(tv_jingqishijian.getText())) {
                    EasyToast.showShort(context, "请选择经期时间");
                    return;
                }

                if ("".equals(tv_baby_sex.getText())) {
                    EasyToast.showShort(context, "请选择宝宝性别");
                    return;
                }

                if ("".equals(tv_baby_born_time.getText())) {
                    EasyToast.showShort(context, "请选择宝宝出生日");
                    return;
                }

                if (Utils.isConnected(context)) {
                    dialog = Utils.showLoadingDialog(context);
                    dialog.show();
                    userLama();
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
    private void userLama() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("jin_time", jin_time);
        params.put("period_length", period_length);
        params.put("period_cycle", period_cycle);
        params.put("baby_sex", baby_sex);
        params.put("baby_birthday", baby_birthday);
        Log.e("RegisterActivity", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "user/lama", "user/lama", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    dialog.dismiss();
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
        params.put("type", "4");
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/period", "life/period", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    LifePeriodBean lifePeriodBean = new Gson().fromJson(result, LifePeriodBean.class);
                    if ("1".equals(String.valueOf(lifePeriodBean.getStu()))) {
                        if (!TextUtils.isEmpty(lifePeriodBean.getRes().getBaby_birthday())) {
                            tv_baby_born_time.setText(lifePeriodBean.getRes().getBaby_birthday());
                        }
                        if (!TextUtils.isEmpty(lifePeriodBean.getRes().getBaby_sex())) {
                            if ("1".equals(String.valueOf(lifePeriodBean.getRes().getBaby_sex()))) {
                                tv_baby_sex.setText("小王子");
                            } else {
                                tv_baby_sex.setText("小公主");
                            }
                        }
                        if (!TextUtils.isEmpty(lifePeriodBean.getRes().getPeriod_length())) {
                            tv_jingqi.setText(lifePeriodBean.getRes().getPeriod_length() + "天");
                        }
                        if (!TextUtils.isEmpty(lifePeriodBean.getRes().getPeriod_cycle())) {
                            tv_zhouqi.setText(lifePeriodBean.getRes().getPeriod_cycle() + "天");
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


}
