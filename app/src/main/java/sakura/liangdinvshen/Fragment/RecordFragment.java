package sakura.liangdinvshen.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.MonthPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sakura.liangdinvshen.Activity.MainActivity;
import sakura.liangdinvshen.Adapter.CalendarsAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Bean.LifeUserSlqBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.View.CustomDayView;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * Created by 赵磊 on 2017/9/19.
 */

public class RecordFragment extends Fragment {

    TextView tvYear;
    TextView tvMonth;
    CoordinatorLayout content;
    MonthPager monthPager;
    RecyclerView rvToDoList;
    private ArrayList<com.ldf.calendar.view.Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private Context context;
    public static CalendarDate currentDate;
    private MainActivity activity;
    private HashMap<String, String> markData;
    private CustomDayView customDayView;
    private CalendarDate date;
    private CalendarsAdapter adapter;
    private TimePickerView pvTime;
    private LinearLayout choose_date_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_fragment_layout, container, false);
        initView(view);
        getData();
        initTimePicker();
        return view;
    }

    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2050, 11, 28);
        pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                String day = DateUtils.getDay(date.getTime());
                String[] split = day.split("-");
                CalendarDate today = new CalendarDate(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]));
                Toast.makeText(context, today.toString(), Toast.LENGTH_SHORT).show();
                calendarAdapter.notifyDataChanged(today);
                tvYear.setText(today.getYear() + "年");
                tvMonth.setText(today.getMonth() + "");
                lifeUserSlq(currentDate);
                RecordFragment.this.date = today;
                App.getQueues().cancelAll("life/user_days");
                adapter.setnow(DateUtils.getDay(System.currentTimeMillis()));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                .setCancelColor(getResources().getColor(R.color.text))
                .setSubmitColor(getResources().getColor(R.color.text))
                .setTitleText("选择日期")
                .setTitleColor(getResources().getColor(R.color.text))
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();

    }


    private void initView(View view) {
        markData = new HashMap<>();
        context = getActivity();
        choose_date_view = (LinearLayout) view.findViewById(R.id.choose_date_view);
        content = (CoordinatorLayout) view.findViewById(R.id.content);
        monthPager = (MonthPager) view.findViewById(R.id.calendar_view);
        //此处强行setViewHeight，毕竟你知道你的日历牌的高度
        monthPager.setViewHeight(Utils.dpi2px(context, 270));
        tvYear = (TextView) view.findViewById(R.id.show_year_view);
        tvMonth = (TextView) view.findViewById(R.id.show_month_view);
        rvToDoList = (RecyclerView) view.findViewById(R.id.list);
        rvToDoList.setHasFixedSize(true);
        //这里用线性显示 类似于listview
        rvToDoList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CalendarsAdapter(context);
        rvToDoList.setAdapter(adapter);
        initCurrentDate();
        initCalendarView();
        activity = (MainActivity) getActivity();
        activity.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshMonthPager();
            }
        }, 300);
        choose_date_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
    }

    public void getData() {
    }

    /**
     * 初始化currentDate
     *
     * @return void
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        tvYear.setText(currentDate.getYear() + "年");
        tvMonth.setText(currentDate.getMonth() + "");
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     */
    private void initCalendarView() {
        initListener();
        customDayView = new CustomDayView(context, R.layout.custom_day_focus);
        calendarAdapter = new CalendarViewAdapter(
                context,
                onSelectDateListener,
                CalendarAttr.CalendarType.MONTH,
                CalendarAttr.WeekArrayType.Sunday,
                customDayView);
        calendarAdapter.setOnCalendarTypeChangedListener(new CalendarViewAdapter.OnCalendarTypeChanged() {
            @Override
            public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {
                rvToDoList.scrollToPosition(0);
            }
        });
        initMonthPager();
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     * 如果存在异步的话，在使用setMarkData之后调用 calendarAdapter.notifyDataChanged();
     */
    private void initMarkData(List<LifeUserSlqBean.DataBean> list) {
        for (int i = 0; i < list.size(); i++) {
            markData.put(list.get(i).getTime(), list.get(i).getStu());
        }
        calendarAdapter.setMarkData(markData);
        calendarAdapter.notifyMonthDataChanged(date);
    }

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };
    }


    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        tvYear.setText(date.getYear() + "年");
        tvMonth.setText(date.getMonth() + "");
        adapter.setnow(date.toString());
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    tvYear.setText(date.getYear() + "年");
                    tvMonth.setText(date.getMonth() + "");
                    lifeUserSlq(date);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void refreshMonthPager() {
        if (RecordFragment.this != null && RecordFragment.this.isAdded()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CalendarDate today = new CalendarDate();
                    calendarAdapter.notifyDataChanged(today);
                    tvYear.setText(today.getYear() + "年");
                    tvMonth.setText(today.getMonth() + "");
                    lifeUserSlq(currentDate);
                    date = today;
                    App.getQueues().cancelAll("life/user_days");
                    adapter.setnow(DateUtils.getDay(System.currentTimeMillis()));
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /**
     * 月份数据获取
     */
    private void lifeUserSlq(CalendarDate date) {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", date.getYear() + "-" + date.getMonth());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/user_slq", "life/user_slq", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    LifeUserSlqBean lifeUserSlqBean = new Gson().fromJson(result, LifeUserSlqBean.class);
                    if ("1".equals(String.valueOf(lifeUserSlqBean.getStu()))) {
                        SpUtil.putAndApply(getActivity(), currentDate.getYear() + "-" + currentDate.getMonth(), result);
                        initMarkData(lifeUserSlqBean.getData());
                    }
                    lifeUserSlqBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
            }
        });
    }


}
