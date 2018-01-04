package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.Fragment.RecordFragment;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.Lauar;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/28
 * 功能描述：
 */
public class DiaryActivity extends BaseActivity {


    private FrameLayout rl_back;
    private ImageView img_submit;
    private TextView tv_year;
    private TextView tv_month;
    private TextView tv_week;
    private TextView tv_nongli;
    private EditText et_content;
    private Dialog dialog;
    private String s1;

    @Override
    protected int setthislayout() {
        return R.layout.activity_diary;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        img_submit = (ImageView) findViewById(R.id.img_submit);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_week = (TextView) findViewById(R.id.tv_week);
        tv_nongli = (TextView) findViewById(R.id.tv_nongli);
        et_content = (EditText) findViewById(R.id.et_content);
    }

    @Override
    protected void initListener() {
        img_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {
        tv_year.setText(RecordFragment.currentDate.getYear() + "年");
        tv_month.setText(RecordFragment.currentDate.getMonth() + "月" + RecordFragment.currentDate.getDay() + "日");
        String lunar = Lauar.getLunar(String.valueOf(RecordFragment.currentDate.getYear()), String.valueOf(RecordFragment.currentDate.getMonth()), String.valueOf(RecordFragment.currentDate.getDay()));
        tv_nongli.setText(lunar);

        String s = RecordFragment.currentDate.getYear() + "-";
        int month = RecordFragment.currentDate.getMonth();

        if (month < 10) {
            s1 = "0" + RecordFragment.currentDate.getMonth() + "-";
        } else {
            s1 = RecordFragment.currentDate.getMonth() + "-";
        }

        String s2 = String.valueOf(RecordFragment.currentDate.getDay());

        tv_week.setText(DateUtils.getWeekByDateStr(s + s1 + s2));
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            lifeShowDiary();
        } else {
            EasyToast.showShort(context, "网络未连接");
            finish();
        }
    }

    private void submit() {
        // validate
        String content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "来简单记录一下今天吧~", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Utils.isConnected(context)) {
            dialog.show();
            lifeDiary(content);
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
    }

    /**
     * 日记提交
     */
    private void lifeDiary(final String diary) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        params.put("diary", diary);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/diary", "life/diary", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        EventBus.getDefault().post(
                                new BankEvent(diary, "riji"));
                        finish();
                    }
                    result = null;
                    stuBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 日记提交
     */
    private void lifeShowDiary() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/show_diary", "life/show_diary", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();

                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        et_content.setText(stuBean.getRes());
                    }
                    result = null;
                    stuBean = null;
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
