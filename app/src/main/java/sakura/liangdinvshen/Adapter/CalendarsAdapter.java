package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import sakura.liangdinvshen.Activity.AskListActivity;
import sakura.liangdinvshen.Activity.BabySymptomActivity;
import sakura.liangdinvshen.Activity.BaringActivity;
import sakura.liangdinvshen.Activity.BreastFeedActivity;
import sakura.liangdinvshen.Activity.ChouChouActivity;
import sakura.liangdinvshen.Activity.DiaryActivity;
import sakura.liangdinvshen.Activity.FetalMovementActivity;
import sakura.liangdinvshen.Activity.GrowthRecordActivity;
import sakura.liangdinvshen.Activity.RankingActivity;
import sakura.liangdinvshen.Activity.SymptomActivity;
import sakura.liangdinvshen.Activity.YJxiangqingActivity;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.LifeUserDaysBean;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.Fragment.RecordFragment;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
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
    private ViewHolder viewHolder;

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

    public void setnow(String date) {

        Date date1 = DateUtils.str2Date(date, "yyyy-MM-dd");

        String day = DateUtils.getDay(System.currentTimeMillis());

        Date date2 = DateUtils.str2Date(day, "yyyy-MM-dd");

        if (date1.getTime() == date2.getTime()) {
            //今天
            viewHolder.ll_otherDay.setVisibility(View.GONE);
            String jieduan = (String) SpUtil.get(context, "jieduan", "");
            if ("1".equals(jieduan) || "2".equals(jieduan)) {
                viewHolder.ll_mingcijieshi.setVisibility(View.VISIBLE);
                viewHolder.ll_body.setVisibility(View.VISIBLE);
                viewHolder.ll_jilu_baby.setVisibility(View.GONE);
                viewHolder.ll_jilu_huaiyun.setVisibility(View.GONE);
            } else if ("3".equals(jieduan)) {
                viewHolder.ll_mingcijieshi.setVisibility(View.GONE);
                viewHolder.ll_body.setVisibility(View.GONE);
                viewHolder.ll_jilu_baby.setVisibility(View.GONE);
                viewHolder.ll_jilu_huaiyun.setVisibility(View.VISIBLE);
            } else if ("4".equals(jieduan)) {
                viewHolder.ll_mingcijieshi.setVisibility(View.GONE);
                viewHolder.ll_body.setVisibility(View.GONE);
                viewHolder.ll_jilu_baby.setVisibility(View.GONE);
                viewHolder.ll_jilu_huaiyun.setVisibility(View.VISIBLE);
            }
        } else {
            //其他
            if (DateUtils.str2Date(date, "yyyy-MM-dd").getTime() > System.currentTimeMillis()) {
                viewHolder.ll_otherDay.setVisibility(View.VISIBLE);
                viewHolder.ll_mingcijieshi.setVisibility(View.GONE);
                viewHolder.ll_body.setVisibility(View.GONE);
                viewHolder.ll_jilu_baby.setVisibility(View.GONE);
                viewHolder.ll_jilu_huaiyun.setVisibility(View.GONE);
                return;
            } else {
                viewHolder.ll_otherDay.setVisibility(View.GONE);
            }
        }
        lifeUserDays(date);
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
        viewHolder = new ViewHolder(layoutInflater.inflate(R.layout.calendarsitem_1, parent, false));
        return viewHolder;
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
                //注册EventBus
                EventBus.getDefault().register(CalendarsAdapter.this);
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
                ShowPickerView("心情", "xinqing", xinqingList, holder.tv_huaiyun_xinqing, 0);
            }
        });


        /**
         * 身体症状
         * */

        holder.ll_body_zhengzhuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SymptomActivity.class));
                //注册EventBus
                if (!EventBus.getDefault().isRegistered(CalendarsAdapter.this)) {
                    EventBus.getDefault().register(CalendarsAdapter.this);
                }
            }
        });


        /**
         *怀孕身体症状
         * */
        holder.ll_huaiyun_zhengzhuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SymptomActivity.class));
                //注册EventBus
                if (!EventBus.getDefault().isRegistered(CalendarsAdapter.this)) {
                    EventBus.getDefault().register(CalendarsAdapter.this);
                }
            }
        });

        /**
         * 宝宝不舒服
         * */
        holder.ll_baby_bushufu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BabySymptomActivity.class));
                //注册EventBus
                if (!EventBus.getDefault().isRegistered(CalendarsAdapter.this)) {
                    EventBus.getDefault().register(CalendarsAdapter.this);
                }
            }
        });


        /**
         * 哺乳记录
         * */
        holder.ll_baby_buru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BreastFeedActivity.class));
                //注册EventBus
                if (!EventBus.getDefault().isRegistered(CalendarsAdapter.this)) {
                    EventBus.getDefault().register(CalendarsAdapter.this);
                }
            }
        });


        /**
         * 日记
         * */

        holder.ll_body_riji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DiaryActivity.class));
                //注册EventBus
                if (!EventBus.getDefault().isRegistered(CalendarsAdapter.this)) {
                    EventBus.getDefault().register(CalendarsAdapter.this);
                }
            }
        });


        /**
         * 臭臭
         * */
        holder.ll_baby_chouchou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChouChouActivity.class));
                //注册EventBus
                if (!EventBus.getDefault().isRegistered(CalendarsAdapter.this)) {
                    EventBus.getDefault().register(CalendarsAdapter.this);
                }
            }
        });


        /**
         * 生长曲线
         * */
        holder.ll_baby_chengzhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, GrowthRecordActivity.class));
                //注册EventBus
                if (!EventBus.getDefault().isRegistered(CalendarsAdapter.this)) {
                    EventBus.getDefault().register(CalendarsAdapter.this);
                }
            }
        });

        /**
         * 胎动记录
         * */
        holder.ll_huaiyun_taidong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, FetalMovementActivity.class));
                //注册EventBus
                if (!EventBus.getDefault().isRegistered(CalendarsAdapter.this)) {
                    EventBus.getDefault().register(CalendarsAdapter.this);
                }
            }
        });

        /**
         *问题
         * */
        holder.ll_huaiyun_wenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AskListActivity.class));
                //注册EventBus
                if (!EventBus.getDefault().isRegistered(CalendarsAdapter.this)) {
                    EventBus.getDefault().register(CalendarsAdapter.this);
                }
            }
        });

        /**
         *大肚照
         * */
        holder.ll_huaiyun_daduzhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BaringActivity.class));
                //注册EventBus
                if (!EventBus.getDefault().isRegistered(CalendarsAdapter.this)) {
                    EventBus.getDefault().register(CalendarsAdapter.this);
                }
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
        public LinearLayout ll_otherDay;


        ViewHolder(View view) {
            super(view);
            this.rootView = view;
            this.ll_otherDay = (LinearLayout) rootView.findViewById(R.id.ll_otherDay);
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


    /**
     * 单日数据获取
     */
    private void lifeUserDays(final String date) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", date);
        Log.e("CalendarsAdapter", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/user_days", "life/user_days", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    LifeUserDaysBean lifeUserDaysBean = new Gson().fromJson(result, LifeUserDaysBean.class);
                    if ("1".equals(String.valueOf(lifeUserDaysBean.getStu()))) {
                        LifeUserDaysBean.DataBean data = lifeUserDaysBean.getData();
                        //其他
                        if (DateUtils.str2Date(RecordFragment.currentDate.toString(), "yyyy-MM-dd").getTime() > System.currentTimeMillis()) {
                            viewHolder.ll_otherDay.setVisibility(View.VISIBLE);
                            viewHolder.ll_mingcijieshi.setVisibility(View.GONE);
                            viewHolder.ll_body.setVisibility(View.GONE);
                            viewHolder.ll_jilu_baby.setVisibility(View.GONE);
                            viewHolder.ll_jilu_huaiyun.setVisibility(View.GONE);
                            return;
                        } else {
                            viewHolder.ll_otherDay.setVisibility(View.GONE);
                        }


                        if ("1".equals(data.getLive_stage()) || "2".equals(data.getLive_stage())) {
                            viewHolder.ll_mingcijieshi.setVisibility(View.VISIBLE);
                            viewHolder.ll_body.setVisibility(View.VISIBLE);
                            viewHolder.ll_jilu_baby.setVisibility(View.GONE);
                            viewHolder.ll_jilu_huaiyun.setVisibility(View.GONE);
                        } else if ("3".equals(data.getLive_stage())) {
                            viewHolder.ll_mingcijieshi.setVisibility(View.GONE);
                            viewHolder.ll_body.setVisibility(View.GONE);
                            viewHolder.ll_jilu_baby.setVisibility(View.GONE);
                            viewHolder.ll_jilu_huaiyun.setVisibility(View.VISIBLE);
                        } else if ("4".equals(data.getLive_stage())) {
                            viewHolder.ll_mingcijieshi.setVisibility(View.GONE);
                            viewHolder.ll_body.setVisibility(View.GONE);
                            viewHolder.ll_jilu_baby.setVisibility(View.GONE);
                            viewHolder.ll_jilu_huaiyun.setVisibility(View.VISIBLE);
                        }

                        if ("1".equals(data.getIs_yuejing())) {
                            viewHolder.sb_jingqi_start.setChecked(true);
                        } else {
                            viewHolder.sb_jingqi_start.setChecked(false);
                        }

                        viewHolder.tv_yuejingxiangqing.setText(data.getYuejing_detail());

                        if ("1".equals(data.getLove_love())) {
                            viewHolder.sb_aiai.setChecked(true);
                        } else {
                            viewHolder.sb_aiai.setChecked(false);
                        }

                        viewHolder.tv_body_tiwen.setText(data.getTi_wen());

                        viewHolder.tv_body_tizhong.setText(data.getTi_zhong());

                        viewHolder.tv_body_xinqing.setText(data.getXin_qing());

                        viewHolder.tv_huaiyun_xinqing.setText(data.getXin_qing());

                        viewHolder.tv_body_zhengzhuang.setText(data.getZheng_zhuang());

                        viewHolder.tv_huaiyun_zhengzhuang.setText(data.getZheng_zhuang());

                        viewHolder.tv_body_riji.setText(data.getRi_ji());

                        viewHolder.tv_baby_buru.setText(data.getBu_ru());

                        viewHolder.tv_baby_chouchou.setText(data.getChou_chou());

                        viewHolder.tv_baby_chengzhang.setText(data.getCz_quxian());

                        viewHolder.tv_baby_bushufu.setText(data.getBaby_bushufu());

                        viewHolder.tv_huaiyun_daduzhao.setText(data.getDa_du_pic());

                        viewHolder.tv_huaiyun_tizhong.setText(data.getTi_zhong());

                        viewHolder.tv_huaiyun_zhengzhuang.setText(data.getZheng_zhuang());

                        if (!"无记录".equals(data.getTai_dong())) {
                            viewHolder.tv_huaiyun_taidong.setText(data.getTai_dong() + "次");
                        }
                        viewHolder.tv_huaiyun_wenti.setText(data.getTi_wen());
                    }
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BankEvent event) {
        if (TextUtils.isEmpty(event.getmType())) {
            viewHolder.tv_yuejingxiangqing.setText(event.getMsg());
        } else if ("zhengzhuang".equals(event.getmType())) {
            viewHolder.tv_body_zhengzhuang.setText(event.getMsg());
            viewHolder.tv_huaiyun_zhengzhuang.setText(event.getMsg());
        } else if ("babyzhengzhuang".equals(event.getmType())) {
            viewHolder.tv_baby_bushufu.setText(event.getMsg());
        } else if ("riji".equals(event.getmType())) {
            viewHolder.tv_body_riji.setText(event.getMsg());
        } else if ("buru".equals(event.getmType())) {
            viewHolder.tv_baby_buru.setText(event.getMsg());
        } else if ("chouchou".equals(event.getmType())) {
            viewHolder.tv_baby_chouchou.setText(event.getMsg());
        } else if ("chengzhang".equals(event.getmType())) {
            viewHolder.tv_baby_chengzhang.setText(event.getMsg());
        } else if ("taidong".equals(event.getmType())) {
            viewHolder.tv_huaiyun_taidong.setText(event.getMsg());
        } else if ("ask".equals(event.getmType())) {
            viewHolder.tv_huaiyun_wenti.setText("已记录");
        }
        //反注册EventBus
        EventBus.getDefault().unregister(CalendarsAdapter.this);
    }


}
