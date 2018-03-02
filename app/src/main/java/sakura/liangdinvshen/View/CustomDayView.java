package sakura.liangdinvshen.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ldf.calendar.Utils;
import com.ldf.calendar.component.State;
import com.ldf.calendar.interf.IDayRenderer;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.DayView;

import sakura.liangdinvshen.R;

/**
 * Created by ldf on 17/6/26.
 */

@SuppressLint("ViewConstructor")
public class CustomDayView extends DayView {

    private TextView dateTv;
    private ImageView marker;
    private View selectedBackground;
    private View todayBackground;
    private final CalendarDate today = new CalendarDate();
    private final RelativeLayout rl_bg;
    private final ImageView imgAiai;

    /**
     * 构造器
     *
     * @param context        上下文
     * @param layoutResource 自定义DayView的layout资源
     */
    public CustomDayView(Context context, int layoutResource) {
        super(context, layoutResource);
        dateTv = (TextView) findViewById(R.id.date);
        marker = (ImageView) findViewById(R.id.maker);
        selectedBackground = findViewById(R.id.selected_background);
        todayBackground = findViewById(R.id.today_background);
        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
        imgAiai = (ImageView) findViewById(R.id.img_aiai);
    }

    @Override
    public void refreshContent() {
        renderToday(day.getDate());
        renderSelect(day.getState());
        renderMarker(day.getDate(), day.getState());
        super.refreshContent();
    }

    private void renderMarker(CalendarDate date, State state) {
        String Year = String.valueOf(date.getYear());
        String Month = String.valueOf(date.getMonth());
        String Day = String.valueOf(date.getDay());
        if (Month.length() == 1) {
            Month = "0" + Month;
        }
        if (Utils.loadMarkData().containsKey(Year + "-" + Month + "-" + Day)) {
            String s = Utils.loadMarkData().get(Year + "-" + Month + "-" + Day);
            /**
             * 月经期
             * 排卵期
             * 实际经期
             * 爱爱
             * 月经期爱爱
             * 排卵期爱爱
             * 实际经期爱爱
             */
            if ("月经期".equals(s)) {
                rl_bg.setBackgroundColor(getResources().getColor(R.color.yuejing));
                imgAiai.setVisibility(GONE);

            } else if ("排卵期".equals(s)) {
                rl_bg.setBackgroundColor(getResources().getColor(R.color.pairuan));
                imgAiai.setVisibility(GONE);

            } else if ("实际经期".equals(s)) {
                rl_bg.setBackgroundColor(getResources().getColor(R.color.textAccent));
                imgAiai.setVisibility(GONE);

            } else if ("安全期爱爱".equals(s)) {
                imgAiai.setVisibility(VISIBLE);
            } else if ("月经期爱爱".equals(s)) {
                rl_bg.setBackgroundColor(getResources().getColor(R.color.yuejing));

                imgAiai.setVisibility(VISIBLE);
            } else if ("排卵期爱爱".equals(s)) {
                rl_bg.setBackgroundColor(getResources().getColor(R.color.pairuan));

                imgAiai.setVisibility(VISIBLE);
            } else if ("实际经期爱爱".equals(s)) {

                rl_bg.setBackgroundColor(getResources().getColor(R.color.textAccent));

                imgAiai.setVisibility(VISIBLE);
            } else if ("安全期".equals(s)) {
                rl_bg.setBackgroundColor(getResources().getColor(R.color.white));
                imgAiai.setVisibility(GONE);
            }

        } else {
            rl_bg.setBackgroundColor(getResources().getColor(R.color.white));
            imgAiai.setVisibility(GONE);
        }
    }

    private void renderSelect(State state) {
        if (state == State.SELECT) {
            selectedBackground.setVisibility(VISIBLE);
            // dateTv.setTextColor(Color.WHITE);
            dateTv.setTextColor(Color.parseColor("#111111"));
        } else if (state == State.NEXT_MONTH || state == State.PAST_MONTH) {
            selectedBackground.setVisibility(GONE);
            dateTv.setTextColor(Color.parseColor("#d5d5d5"));
        } else {
            selectedBackground.setVisibility(GONE);
            dateTv.setTextColor(Color.parseColor("#111111"));
        }
    }

    private void renderToday(CalendarDate date) {
        if (date != null) {
            if (date.equals(today)) {
                dateTv.setText("今");
                todayBackground.setVisibility(VISIBLE);
            } else {
                dateTv.setText(date.day + "");
                todayBackground.setVisibility(GONE);
            }
        }
    }

    @Override
    public IDayRenderer copy() {
        return new CustomDayView(context, layoutResource);
    }
}
