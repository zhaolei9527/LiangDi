package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.HashMap;

import sakura.liangdinvshen.Activity.RankingActivity;
import sakura.liangdinvshen.Activity.YJxiangqingActivity;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.Fragment.RecordFragment;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * sakura.liangdinvshen.Adapter
 *
 * @author 赵磊
 * @date 2017/12/27
 * 功能描述：
 */
public class CalendarsAdapter extends RecyclerView.Adapter<CalendarsAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context context;
    ArrayList<String> wenduList = new ArrayList<>();
    ArrayList<String> tizhongList = new ArrayList<>();
    ArrayList<String> xinqingList = new ArrayList<>();

    private int maxwendu = 42;
    private int maxtizhong = 100;

    public CalendarsAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        wenduList.clear();
        tizhongList.clear();
        xinqingList.clear();
        initWendu();
        initTiZhong();
        xinqingList.add("开心");
        xinqingList.add("一般");
        xinqingList.add("郁闷");
        xinqingList.add("特差");
    }

    private void initTiZhong() {
        for (int i = 20; i < maxtizhong; i++) {
            for (int j = 0; j < 10; j++) {
                tizhongList.add(String.valueOf(i) + "." + String.valueOf(j) + "Kg");
            }
        }
    }

    private void initWendu() {
        for (int i = 35; i < maxwendu; i++) {
            for (int j = 0; j < 10; j++) {
                wenduList.add(String.valueOf(i) + "." + String.valueOf(j) + "°C");
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.calendarsitem_1, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /**
         * 名词解释
         * */
        holder.ll_mingcijieshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RankingActivity.class));
            }
        });

        /**
         * 经期开始
         * */
        holder.ll_jingqi_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.sb_jingqi_start.setChecked(!holder.sb_jingqi_start.isChecked());
                holder.sb_jingqi_end.setChecked(!holder.sb_jingqi_start.isChecked());
                if (holder.sb_jingqi_start.isChecked()) {
                    lifePeriodStart();
                } else {
                    lifePeriodEnd();
                }
            }
        });

        /**
         * 经期结束
         * */
        holder.ll_jingqi_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.sb_jingqi_end.setChecked(!holder.sb_jingqi_end.isChecked());
                holder.sb_jingqi_start.setChecked(!holder.sb_jingqi_end.isChecked());
                if (holder.sb_jingqi_end.isChecked()) {
                    lifePeriodEnd();
                } else {
                    lifePeriodStart();
                }
            }
        });

        /**
         * 爱爱
         * */
        holder.ll_aiai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.sb_aiai.setChecked(!holder.sb_aiai.isChecked());
                if (holder.sb_aiai.isChecked()) {
                    lifeLoveLove("1");
                } else {
                    lifeLoveLove("2");
                }
            }
        });


        /**
         * 月经详情
         * */
        holder.ll_yuejingxiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, YJxiangqingActivity.class));
            }
        });

        /**
         * 月经体温
         * */
        holder.ll_body_tiwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("体温", "tiwen", wenduList, holder.tv_body_tiwen, 15);
            }
        });

        /**
         * 怀孕体重
         * */
        holder.ll_huaiyun_tizhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("体重", "tizhong", tizhongList, holder.tv_body_tizhong, 250);
            }
        });

        /**
         * 月经体重
         * */
        holder.ll_body_tizhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("体重", "tizhong", tizhongList, holder.tv_body_tizhong, 250);
            }
        });

        /**
         * 月经心情
         * */
        holder.ll_body_xinqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("心情", "xinqing", xinqingList, holder.tv_body_xinqing, 0);
            }
        });

        /**
         * 怀孕心情
         * */
        holder.ll_huaiyun_xinqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("心情", "xinqing", xinqingList, holder.tv_body_xinqing, 0);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public LinearLayout ll_mingcijieshi;
        public LinearLayout ll_jingqi_start;
        public LinearLayout ll_jingqi_end;

        public SwitchButton sb_jingqi_start;
        public SwitchButton sb_jingqi_end;
        public TextView tv_yuejingxiangqing;
        public LinearLayout ll_yuejingxiangqing;
        public LinearLayout ll_aiai;
        public SwitchButton sb_aiai;
        public TextView tv_body_tiwen;
        public LinearLayout ll_body_tiwen;
        public TextView tv_body_tizhong;
        public LinearLayout ll_body_tizhong;
        public TextView tv_body_xinqing;
        public LinearLayout ll_body_xinqing;
        public TextView tv_body_zhengzhuang;
        public LinearLayout ll_body_zhengzhuang;
        public TextView tv_body_riji;
        public LinearLayout ll_body_riji;
        public LinearLayout ll_body;
        public TextView tv_baby_buru;
        public LinearLayout ll_baby_buru;
        public TextView tv_baby_chouchou;
        public LinearLayout ll_baby_chouchou;
        public TextView tv_baby_chengzhang;
        public LinearLayout ll_baby_chengzhang;
        public TextView tv_baby_bushufu;
        public LinearLayout ll_baby_bushufu;
        public LinearLayout ll_jilu_baby;
        public TextView tv_huaiyun_xinqing;
        public LinearLayout ll_huaiyun_xinqing;
        public TextView tv_huaiyun_daduzhao;
        public LinearLayout ll_huaiyun_daduzhao;
        public TextView tv_huaiyun_tizhong;
        public LinearLayout ll_huaiyun_tizhong;
        public TextView tv_huaiyun_zhengzhuang;
        public LinearLayout ll_huaiyun_zhengzhuang;
        public TextView tv_huaiyun_taidong;
        public LinearLayout ll_huaiyun_taidong;
        public TextView tv_huaiyun_wenti;
        public LinearLayout ll_huaiyun_wenti;
        public LinearLayout ll_jilu_huaiyun;
        public LinearLayout cv_item;

        ViewHolder(View view) {
            super(view);
            this.rootView = view;
            this.ll_mingcijieshi = (LinearLayout) rootView.findViewById(R.id.ll_mingcijieshi);
            this.ll_mingcijieshi = (LinearLayout) rootView.findViewById(R.id.ll_mingcijieshi);
            this.ll_jingqi_start = (LinearLayout) rootView.findViewById(R.id.ll_jingqi_start);
            this.sb_jingqi_start = (SwitchButton) rootView.findViewById(R.id.sb_jingqi_start);
            this.sb_jingqi_end = (SwitchButton) rootView.findViewById(R.id.sb_jingqi_end);
            this.ll_jingqi_end = (LinearLayout) rootView.findViewById(R.id.ll_jingqi_end);
            this.tv_yuejingxiangqing = (TextView) rootView.findViewById(R.id.tv_yuejingxiangqing);
            this.ll_yuejingxiangqing = (LinearLayout) rootView.findViewById(R.id.ll_yuejingxiangqing);
            this.ll_aiai = (LinearLayout) rootView.findViewById(R.id.ll_aiai);
            this.sb_aiai = (SwitchButton) rootView.findViewById(R.id.sb_aiai);
            this.tv_body_tiwen = (TextView) rootView.findViewById(R.id.tv_body_tiwen);
            this.ll_body_tiwen = (LinearLayout) rootView.findViewById(R.id.ll_body_tiwen);
            this.tv_body_tizhong = (TextView) rootView.findViewById(R.id.tv_body_tizhong);
            this.ll_body_tizhong = (LinearLayout) rootView.findViewById(R.id.ll_body_tizhong);
            this.tv_body_xinqing = (TextView) rootView.findViewById(R.id.tv_body_xinqing);
            this.ll_body_xinqing = (LinearLayout) rootView.findViewById(R.id.ll_body_xinqing);
            this.tv_body_zhengzhuang = (TextView) rootView.findViewById(R.id.tv_body_zhengzhuang);
            this.ll_body_zhengzhuang = (LinearLayout) rootView.findViewById(R.id.ll_body_zhengzhuang);
            this.tv_body_riji = (TextView) rootView.findViewById(R.id.tv_body_riji);
            this.ll_body_riji = (LinearLayout) rootView.findViewById(R.id.ll_body_riji);
            this.ll_body = (LinearLayout) rootView.findViewById(R.id.ll_body);
            this.tv_baby_buru = (TextView) rootView.findViewById(R.id.tv_baby_buru);
            this.ll_baby_buru = (LinearLayout) rootView.findViewById(R.id.ll_baby_buru);
            this.tv_baby_chouchou = (TextView) rootView.findViewById(R.id.tv_baby_chouchou);
            this.ll_baby_chouchou = (LinearLayout) rootView.findViewById(R.id.ll_baby_chouchou);
            this.tv_baby_chengzhang = (TextView) rootView.findViewById(R.id.tv_baby_chengzhang);
            this.ll_baby_chengzhang = (LinearLayout) rootView.findViewById(R.id.ll_baby_chengzhang);
            this.tv_baby_bushufu = (TextView) rootView.findViewById(R.id.tv_baby_bushufu);
            this.ll_baby_bushufu = (LinearLayout) rootView.findViewById(R.id.ll_baby_bushufu);
            this.ll_jilu_baby = (LinearLayout) rootView.findViewById(R.id.ll_jilu_baby);
            this.tv_huaiyun_xinqing = (TextView) rootView.findViewById(R.id.tv_huaiyun_xinqing);
            this.ll_huaiyun_xinqing = (LinearLayout) rootView.findViewById(R.id.ll_huaiyun_xinqing);
            this.tv_huaiyun_daduzhao = (TextView) rootView.findViewById(R.id.tv_huaiyun_daduzhao);
            this.ll_huaiyun_daduzhao = (LinearLayout) rootView.findViewById(R.id.ll_huaiyun_daduzhao);
            this.tv_huaiyun_tizhong = (TextView) rootView.findViewById(R.id.tv_huaiyun_tizhong);
            this.ll_huaiyun_tizhong = (LinearLayout) rootView.findViewById(R.id.ll_huaiyun_tizhong);
            this.tv_huaiyun_zhengzhuang = (TextView) rootView.findViewById(R.id.tv_huaiyun_zhengzhuang);
            this.ll_huaiyun_zhengzhuang = (LinearLayout) rootView.findViewById(R.id.ll_huaiyun_zhengzhuang);
            this.tv_huaiyun_taidong = (TextView) rootView.findViewById(R.id.tv_huaiyun_taidong);
            this.ll_huaiyun_taidong = (LinearLayout) rootView.findViewById(R.id.ll_huaiyun_taidong);
            this.tv_huaiyun_wenti = (TextView) rootView.findViewById(R.id.tv_huaiyun_wenti);
            this.ll_huaiyun_wenti = (LinearLayout) rootView.findViewById(R.id.ll_huaiyun_wenti);
            this.ll_jilu_huaiyun = (LinearLayout) rootView.findViewById(R.id.ll_jilu_huaiyun);
            this.cv_item = (LinearLayout) rootView.findViewById(R.id.cv_item);
        }
    }

    /**
     * 经期开始
     */
    private void lifePeriodStart() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/period_start", "life/period_start", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if (!"1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(context, "操作失败");
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 经期结束
     */
    private void lifePeriodEnd() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/period_end", "life/period_end", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if (!"1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(context, "操作失败");
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 是否爱爱
     */
    private void lifeLoveLove(String lovelove) {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        params.put("lovelove", lovelove);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/love_love", "life/love_love", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if (!"1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(context, "操作失败");
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 体温
     */
    private void lifeBodyTemperature(String temperature) {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        params.put("temperature", temperature);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/body_temperature", "life/body_temperature", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if (!"1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(context, "操作失败");
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 体重
     */
    private void lifeBodyWeight(String weight) {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        params.put("weight", weight);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/body_weight", "life/body_weight", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if (!"1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(context, "操作失败");
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 心情
     */
    private void lifeMood(String mood) {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        params.put("mood", mood);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/mood", "life/mood", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if (!"1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(context, "操作失败");
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void ShowPickerView(String TITLE, final String type, ArrayList<String> list, final TextView tv, int SelectOptions) {// 弹出选择器
        if (!list.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if ("tiwen".equals(type)) {
                        tv.setText(wenduList.get(options1));
                        lifeBodyTemperature(wenduList.get(options1));
                    } else if ("tizhong".equals(type)) {
                        tv.setText(tizhongList.get(options1));
                        lifeBodyWeight(tizhongList.get(options1));
                    } else if ("xinqing".equals(type)) {
                        tv.setText(xinqingList.get(options1));
                        lifeMood(xinqingList.get(options1));
                    } else if ("4".equals(type)) {

                    }
                }
            })
                    .setTitleBgColor(context.getResources().getColor(R.color.pressedColor))
                    .setCancelColor(context.getResources().getColor(R.color.text))
                    .setSubmitColor(context.getResources().getColor(R.color.text))
                    .setTitleText(TITLE)
                    .setSelectOptions(SelectOptions)
                    .setTitleColor(context.getResources().getColor(R.color.text))
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(list);//三级选择器
            pvOptions.show();
        }
    }


}
