package sakura.liangdinvshen.Activity;

import android.app.Dialog;
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

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.Fragment.RecordFragment;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/27
 * 功能描述：
 */
public class AddFetaMovementActivity extends BaseActivity {

    private FrameLayout rl_back;
    private RelativeLayout rl_start_time;
    private RelativeLayout rl_end_time;
    private RelativeLayout rl_size;

    ArrayList<String> mlList = new ArrayList<>();
    private int maxML = 100;

    private TextView tv_start;
    private TextView tv_endtime;
    private TextView tv_size;
    private TextView tv_submit;

    @Override
    protected int setthislayout() {
        return R.layout.activity_addfetamovement;
    }

    @Override
    protected void initview() {
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);
        tv_size = (TextView) findViewById(R.id.tv_size);
        rl_start_time = (RelativeLayout) findViewById(R.id.rl_start_time);
        rl_end_time = (RelativeLayout) findViewById(R.id.rl_end_time);
        rl_size = (RelativeLayout) findViewById(R.id.rl_size);
        tv_start.setText(getMillon(System.currentTimeMillis()));
        tv_endtime.setText(getMillon(System.currentTimeMillis()));
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
                initTimePicker();
            }
        });

        rl_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker2();
            }
        });

        rl_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("胎动次数", "1", mlList);
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Date date = DateUtils.str2Date(RecordFragment.currentDate.toString() + " " + tv_start.getText().toString(), "yyyy-MM-dd HH:mm");
                Date date1 = DateUtils.str2Date(RecordFragment.currentDate.toString() + " " + tv_endtime.getText().toString(), "yyyy-MM-dd HH:mm");
                if (date1.getTime() < date.getTime()) {
                    EasyToast.showShort(context, "结束时间不能小于开始时间");
                    return;
                }

                if (Utils.isConnected(context)) {
                    dialog = Utils.showLoadingDialog(context);
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                    suckleFetusDoadd(tv_start.getText().toString(), tv_endtime.getText().toString(), tv_size.getText().toString());
                } else {
                    EasyToast.showShort(context, "网络未连接");
                }


            }
        });

    }

    private Dialog dialog;

    @Override
    protected void initData() {
        for (int i = 1; i < maxML; i++) {
            mlList.add(String.valueOf(i) + "次");
        }
    }

    /**
     * 格式到秒
     *
     * @return time -> yyyy-MM-dd-HH-mm-ss
     */
    public String getMillon(long time) {
        return new SimpleDateFormat("HH:mm").format(time);
    }

    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1972, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 11, 28);
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tv_start.setText(getMillon(date.getTime()));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                .setCancelColor(getResources().getColor(R.color.text))
                .setSubmitColor(getResources().getColor(R.color.text))
                .setTitleText("开始时间")
                .setTitleColor(getResources().getColor(R.color.text))
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
        pvTime.show();
    }


    private void initTimePicker2() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1972, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 11, 28);
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tv_endtime.setText(getMillon(date.getTime()));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                .setCancelColor(getResources().getColor(R.color.text))
                .setSubmitColor(getResources().getColor(R.color.text))
                .setTitleText("开始时间")
                .setTitleColor(getResources().getColor(R.color.text))
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
        pvTime.show();
    }


    private void ShowPickerView(String TITLE, final String type, final ArrayList<String> list) {// 弹出选择器
        if (!list.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    tv_size.setText(list.get(options1));
                }
            })
                    .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                    .setCancelColor(getResources().getColor(R.color.text))
                    .setSubmitColor(getResources().getColor(R.color.text))
                    .setTitleText(TITLE)
                    .setSelectOptions(0)
                    .setTitleColor(getResources().getColor(R.color.text))
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(list);//三级选择器
            pvOptions.show();
        }
    }

    /**
     * 增加胎动记录
     */
    private void suckleFetusDoadd(final String start_time, String end_time, final String amount) {
        HashMap<String, String> params = new HashMap<>(5);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        params.put("start_time", start_time);
        params.put("end_time", end_time);
        params.put("amount", amount);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "suckle/fetus_doadd", "suckle/fetus_doadd", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                dialog.dismiss();
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        EventBus.getDefault().post(
                                new BankEvent(amount, "taidong"));
                        finish();
                    } else {
                        EasyToast.showShort(context, "提交失败");
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

    @Override
    protected void onPause() {
        super.onPause();
    }
}
