package sakura.liangdinvshen.Activity;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.LifePeriodBean;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
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

    private void ShowPickerView_changdu(String TITLE) {// 弹出选择器
        if (!jingqitems.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if (!period_length.equals(String.valueOf(options1 + 1))) {
                        period_length = String.valueOf(options1 + 1);
                        tv_changdu.setText(String.valueOf(jingqitems.get(options1)));
                        lifeDoPeriodLength();
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
                        lifeDoPeriodCycle();
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

        }
    }

    /**
     * 记录-只记经期-经期长度
     */
    private void lifeDoPeriodLength() {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("period_length", period_length);
        if ("jingqi".equals(getIntent().getStringExtra("type"))) {
            params.put("type", "1");
        } else {
            params.put("type", "2");
        }
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/do_period_length", "life/do_period_length", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
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


    /**
     * 记录-只记经期-周期长度
     */
    private void lifeDoPeriodCycle() {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("period_cycle", period_cycle);
        if ("jingqi".equals(getIntent().getStringExtra("type"))) {
            params.put("type", "1");
        } else {
            params.put("type", "2");
        }
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/do_period_cycle", "life/do_period_cycle", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
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
