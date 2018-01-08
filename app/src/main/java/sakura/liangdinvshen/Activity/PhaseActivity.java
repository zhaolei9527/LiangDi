package sakura.liangdinvshen.Activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.LifeStageBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

public class PhaseActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout rl_back;
    private TextView tv_jingqi_content;
    private TextView tv_beiyun_content;
    private TextView tv_huaiyun_content;
    private TextView tv_lama_content;

    private TextView tv_jiedaun;
    private CheckBox Choosed_jingqi;
    private CheckBox Choosed_beiyun;
    private CheckBox Choosed_huanyun;
    private CheckBox Choosed_lama;
    private String type;

    @Override
    protected int setthislayout() {
        return R.layout.activity_phase;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_jingqi_content = (TextView) findViewById(R.id.tv_jingqi_content);
        tv_beiyun_content = (TextView) findViewById(R.id.tv_beiyun_content);
        tv_huaiyun_content = (TextView) findViewById(R.id.tv_huaiyun_content);
        tv_lama_content = (TextView) findViewById(R.id.tv_lama_content);


        tv_jiedaun = (TextView) findViewById(R.id.tv_jiedaun);
        Choosed_jingqi = (CheckBox) findViewById(R.id.Choosed_jingqi);
        Choosed_beiyun = (CheckBox) findViewById(R.id.Choosed_beiyun);
        Choosed_huanyun = (CheckBox) findViewById(R.id.Choosed_huanyun);
        Choosed_lama = (CheckBox) findViewById(R.id.Choosed_lama);
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(this);
        Choosed_jingqi.setOnClickListener(this);
        Choosed_beiyun.setOnClickListener(this);
        Choosed_huanyun.setOnClickListener(this);
        Choosed_lama.setOnClickListener(this);

        String jieduan = (String) SpUtil.get(context, "jieduan", "");
        if (!TextUtils.isEmpty(jieduan)) {
            if ("1".equals(jieduan)) {
                Choosed_jingqi.setChecked(true);
                tv_jiedaun.setText("人生阶段：只记经期");
            } else if ("2".equals(jieduan)) {
                Choosed_beiyun.setChecked(true);
                tv_jiedaun.setText("人生阶段：我在备孕");
            } else if ("3".equals(jieduan)) {
                Choosed_huanyun.setChecked(true);
                tv_jiedaun.setText("人生阶段：我怀孕了");
            } else if ("4".equals(jieduan)) {
                Choosed_lama.setChecked(true);
                tv_jiedaun.setText("人生阶段：我是辣妈");
            }
        }

    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        if (!TextUtils.isEmpty(type)) {
            if ("chage".equals(type)) {
                rl_back.setVisibility(View.GONE);
            }
        } else {
            rl_back.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.Choosed_jingqi:
                Choosed_beiyun.setChecked(false);
                Choosed_huanyun.setChecked(false);
                Choosed_lama.setChecked(false);
                Choosed_jingqi.setChecked(true);
                tv_jiedaun.setText("人生阶段：只记经期");
                SpUtil.putAndApply(context, "jieduan", "1");
                if (!TextUtils.isEmpty(type)) {
                    context.startActivity(new Intent(context, MenstrualPeriodActivity.class)
                            .putExtra("type", "jingqi").putExtra("type2", "change"));
                } else {
                    context.startActivity(new Intent(context, MenstrualPeriodActivity.class)
                            .putExtra("type", "jingqi"));
                }
                break;
            case R.id.Choosed_beiyun:
                Choosed_jingqi.setChecked(false);
                Choosed_huanyun.setChecked(false);
                Choosed_lama.setChecked(false);
                Choosed_beiyun.setChecked(true);
                tv_jiedaun.setText("人生阶段：我在备孕");
                SpUtil.putAndApply(context, "jieduan", "2");
                if (!TextUtils.isEmpty(type)) {
                    context.startActivity(new Intent(context, MenstrualPeriodActivity.class)
                            .putExtra("type", "beiyun").putExtra("type2", "change"));
                } else {
                    context.startActivity(new Intent(context, MenstrualPeriodActivity.class)
                            .putExtra("type", "beiyun"));
                }
                break;
            case R.id.Choosed_huanyun:
                Choosed_jingqi.setChecked(false);
                Choosed_beiyun.setChecked(false);
                Choosed_lama.setChecked(false);
                Choosed_huanyun.setChecked(true);
                tv_jiedaun.setText("人生阶段：我怀孕了");
                SpUtil.putAndApply(context, "jieduan", "3");

                if (!TextUtils.isEmpty(type)) {
                    context.startActivity(new Intent(context, DueDateActivity.class)
                            .putExtra("type", "change"));
                } else {
                    context.startActivity(new Intent(context, DueDateActivity.class));
                }
                break;
            case R.id.Choosed_lama:
                Choosed_jingqi.setChecked(false);
                Choosed_beiyun.setChecked(false);
                Choosed_huanyun.setChecked(false);
                Choosed_lama.setChecked(true);
                tv_jiedaun.setText("人生阶段：我是辣妈");
                SpUtil.putAndApply(context, "jieduan", "4");
                if (!TextUtils.isEmpty(type)) {
                    context.startActivity(new Intent(context, LaMaActivity.class)
                            .putExtra("type", "change"));
                } else {
                    context.startActivity(new Intent(context, LaMaActivity.class));
                }
                break;
            default:
                break;
        }
    }

    /**
     * 人生阶段数据获取
     */
    private void lifeStage() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/stage", "life/stage", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    LifeStageBean lifeStageBean = new Gson().fromJson(result, LifeStageBean.class);
                    if ("1".equals(String.valueOf(lifeStageBean.getStu()))) {
                        if (!TextUtils.isEmpty(lifeStageBean.getPeriod_cycle())) {
                            tv_jingqi_content.setText("周期为" + lifeStageBean.getPeriod_cycle() + "天" + "经期" + lifeStageBean.getPeriod_length() + "天");
                        }
                        tv_jiedaun.setText("人生阶段：只记经期");
                        Choosed_beiyun.setChecked(false);
                        Choosed_huanyun.setChecked(false);
                        Choosed_lama.setChecked(false);
                        Choosed_jingqi.setChecked(true);
                    } else if ("2".equals(String.valueOf(lifeStageBean.getStu()))) {
                        if (!TextUtils.isEmpty(lifeStageBean.getPeriod_cycle())) {
                            tv_beiyun_content.setText("周期为" + lifeStageBean.getPeriod_cycle() + "天" + "经期" + lifeStageBean.getPeriod_length() + "天");
                        }
                        tv_jiedaun.setText("人生阶段：我在备孕");
                        Choosed_jingqi.setChecked(false);
                        Choosed_huanyun.setChecked(false);
                        Choosed_lama.setChecked(false);
                        Choosed_beiyun.setChecked(true);
                    } else if ("3".equals(String.valueOf(lifeStageBean.getStu()))) {
                        if (!TextUtils.isEmpty(lifeStageBean.getPregnant_expected())) {
                            tv_huaiyun_content.setText("待产期" + DateUtils.getDay(Long.parseLong(lifeStageBean.getPregnant_expected()) * 1000));
                        }
                        tv_jiedaun.setText("人生阶段：我怀孕了");
                        Choosed_jingqi.setChecked(false);
                        Choosed_beiyun.setChecked(false);
                        Choosed_lama.setChecked(false);
                        Choosed_huanyun.setChecked(true);
                    } else if ("4".equals(String.valueOf(lifeStageBean.getStu()))) {
                        if (!TextUtils.isEmpty(lifeStageBean.getBaby_birthday())) {
                            long l = Long.parseLong(lifeStageBean.getBaby_birthday()) * 1000;
                            tv_lama_content.setText("宝贝生日:" + DateUtils.getDay(l));
                        }
                        tv_jiedaun.setText("人生阶段：我是辣妈");
                        Choosed_jingqi.setChecked(false);
                        Choosed_beiyun.setChecked(false);
                        Choosed_huanyun.setChecked(false);
                        Choosed_lama.setChecked(true);
                    }
                    lifeStageBean = null;
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
    protected void onResume() {
        super.onResume();
        tv_jingqi_content.setText("");
        tv_beiyun_content.setText("");
        tv_huaiyun_content.setText("");
        tv_lama_content.setText("");
        lifeStage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("life/stage");
    }
}
