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
public class AddBreastFeedActivity extends BaseActivity {

    private FrameLayout rl_back;
    private RelativeLayout rl_start_time;
    private RelativeLayout rl_type;
    private RelativeLayout rl_size;

    ArrayList<String> typeList = new ArrayList<>();
    ArrayList<String> minutesList = new ArrayList<>();
    ArrayList<String> secondsList = new ArrayList<>();
    ArrayList<String> mlList = new ArrayList<>();
    ArrayList<String> dayList = new ArrayList<>();
    ArrayList<String> dayMinutesList = new ArrayList<>();


    private int maxtime = 60;
    private int maxML = 100;
    private int maxday = 25;

    private TextView tv_start;
    private TextView tv_type;
    private TextView tv_size;

    @Override
    protected int setthislayout() {
        return R.layout.activity_addbrastfeed;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_size = (TextView) findViewById(R.id.tv_size);
        rl_start_time = (RelativeLayout) findViewById(R.id.rl_start_time);
        rl_type = (RelativeLayout) findViewById(R.id.rl_type);
        rl_size = (RelativeLayout) findViewById(R.id.rl_size);
        tv_start.setText(getMillon(System.currentTimeMillis()));
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

        rl_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tv_type.getText().toString())) {
                    EasyToast.showShort(context, "请先选择喂养模式");
                    return;
                }
                if ("亲喂母乳".equals(tv_type.getText().toString())) {
                    ShowPickerView("喂养量", "2", minutesList, secondsList);
                } else {
                    ShowPickerView("喂养量", "1", mlList);
                }
            }
        });

        rl_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("喂养类型", "3", typeList);
            }
        });


    }

    @Override
    protected void initData() {
        typeList.add("奶粉");
        typeList.add("甁喂母乳");
        typeList.add("亲喂母乳");

        for (int i = 0; i < maxtime; i++) {
            minutesList.add(i + "分");
            secondsList.add(i + "秒");
            dayMinutesList.add(String.valueOf(i));
        }

        for (int i = 10; i < maxML; i = i + 10) {
            mlList.add(i + "mL");
        }

        for (int i = 1; i < maxday; i++) {
            dayList.add(String.valueOf(i));
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

    private Dialog dialog;

    private void ShowPickerView(String TITLE, final String type, ArrayList<String> list) {// 弹出选择器
        if (!list.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {


                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if ("1".equals(type)) {
                        tv_size.setText(mlList.get(options1));
                        if (Utils.isConnected(context)) {
                            dialog = Utils.showLoadingDialog(context);
                            dialog.show();
                            suckleSuckleDoadd(tv_type.getText().toString(), tv_size.getText().toString(), tv_start.getText().toString());
                        } else {
                            EasyToast.showShort(context, "网络未连接");
                        }
                    } else if ("3".equals(type)) {
                        tv_type.setText(typeList.get(options1));
                    }
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

    private void ShowPickerView(String TITLE, final String type, ArrayList<String> list, ArrayList<String> list2) {// 弹出选择器
        if (!list.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if ("2".equals(type)) {
                        tv_size.setText(minutesList.get(options1) + secondsList.get(options2));
                        if (Utils.isConnected(context)) {
                            dialog = Utils.showLoadingDialog(context);
                            dialog.show();
                            suckleSuckleDoadd(tv_type.getText().toString(), tv_size.getText().toString(), tv_start.getText().toString());
                        } else {
                            EasyToast.showShort(context, "网络未连接");
                        }
                    } else if ("4".equals(type)) {
                        tv_start.setText(dayList.get(options1) + ":" + dayMinutesList.get(options2));
                    }
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
            pvOptions.setNPicker(list, list2, null);//三级选择器
            pvOptions.show();
        }
    }


    /**
     * 哺乳记录
     */
    private void suckleSuckleDoadd(String type, String amount, final String start_time) {
        HashMap<String, String> params = new HashMap<>(5);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        params.put("type", type);
        params.put("amount", amount);
        params.put("start_time", start_time);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "suckle/suckle_doadd", "suckle/suckle_doadd", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        EventBus.getDefault().post(
                                new BankEvent(start_time, "buru"));
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

}
