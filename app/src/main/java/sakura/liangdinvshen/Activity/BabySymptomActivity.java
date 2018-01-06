package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
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
 * @date 2017/12/28
 * 功能描述：
 */
public class BabySymptomActivity extends BaseActivity {


    ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    private FrameLayout rl_back;
    private TextView tv_submit;
    private CheckBox btn_0;
    private CheckBox btn_1;
    private CheckBox btn_2;
    private CheckBox btn_3;
    private CheckBox btn_4;
    private CheckBox btn_5;
    private CheckBox btn_6;
    private CheckBox btn_7;
    private CheckBox btn_8;
    private CheckBox btn_9;
    private CheckBox btn_10;
    private CheckBox btn_11;
    private CheckBox btn_12;
    private CheckBox btn_13;
    private CheckBox btn_14;
    private CheckBox btn_18;
    private CheckBox btn_19;
    private CheckBox btn_20;
    private CheckBox btn_23;
    private CheckBox btn_24;
    private CheckBox btn_25;
    private CheckBox btn_26;
    private CheckBox btn_27;
    private CheckBox btn_28;
    private CheckBox btn_29;
    private CheckBox btn_30;
    private CheckBox btn_31;
    private CheckBox btn_32;
    private CheckBox btn_33;
    private CheckBox btn_34;

    @Override
    protected int setthislayout() {
        return R.layout.activity_baby_symptom;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        btn_0 = (CheckBox) findViewById(R.id.btn_0);
        btn_1 = (CheckBox) findViewById(R.id.btn_1);
        btn_2 = (CheckBox) findViewById(R.id.btn_2);
        btn_3 = (CheckBox) findViewById(R.id.btn_3);
        btn_4 = (CheckBox) findViewById(R.id.btn_4);
        btn_5 = (CheckBox) findViewById(R.id.btn_5);
        btn_6 = (CheckBox) findViewById(R.id.btn_6);
        btn_7 = (CheckBox) findViewById(R.id.btn_7);
        btn_8 = (CheckBox) findViewById(R.id.btn_8);
        btn_9 = (CheckBox) findViewById(R.id.btn_9);
        btn_10 = (CheckBox) findViewById(R.id.btn_10);
        btn_11 = (CheckBox) findViewById(R.id.btn_11);
        btn_12 = (CheckBox) findViewById(R.id.btn_12);
        btn_13 = (CheckBox) findViewById(R.id.btn_13);
        btn_14 = (CheckBox) findViewById(R.id.btn_14);
        btn_18 = (CheckBox) findViewById(R.id.btn_18);
        btn_19 = (CheckBox) findViewById(R.id.btn_19);
        btn_20 = (CheckBox) findViewById(R.id.btn_20);
        btn_23 = (CheckBox) findViewById(R.id.btn_23);
        btn_24 = (CheckBox) findViewById(R.id.btn_24);
        btn_25 = (CheckBox) findViewById(R.id.btn_25);
        btn_29 = (CheckBox) findViewById(R.id.btn_29);
        btn_30 = (CheckBox) findViewById(R.id.btn_30);
        btn_31 = (CheckBox) findViewById(R.id.btn_31);
        btn_32 = (CheckBox) findViewById(R.id.btn_32);
        btn_33 = (CheckBox) findViewById(R.id.btn_33);
        btn_34 = (CheckBox) findViewById(R.id.btn_34);
        checkBoxes.add(btn_0);
        checkBoxes.add(btn_1);
        checkBoxes.add(btn_2);
        checkBoxes.add(btn_3);
        checkBoxes.add(btn_4);
        checkBoxes.add(btn_5);
        checkBoxes.add(btn_6);
        checkBoxes.add(btn_7);
        checkBoxes.add(btn_8);
        checkBoxes.add(btn_9);
        checkBoxes.add(btn_10);
        checkBoxes.add(btn_11);
        checkBoxes.add(btn_12);
        checkBoxes.add(btn_13);
        checkBoxes.add(btn_14);
        checkBoxes.add(btn_18);
        checkBoxes.add(btn_19);
        checkBoxes.add(btn_20);
        checkBoxes.add(btn_23);
        checkBoxes.add(btn_24);
        checkBoxes.add(btn_25);
        checkBoxes.add(btn_29);
        checkBoxes.add(btn_30);
        checkBoxes.add(btn_31);
        checkBoxes.add(btn_32);
        checkBoxes.add(btn_33);
        checkBoxes.add(btn_34);
    }

    private Dialog dialog;

    @Override
    protected void initListener() {
        tv_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < checkBoxes.size(); i++) {
                    if (checkBoxes.get(i).isChecked()) {
                        if (stringBuilder.length() == 0) {
                            stringBuilder.append(checkBoxes.get(i).getText().toString());
                        } else {
                            stringBuilder.append(",");
                            stringBuilder.append(checkBoxes.get(i).getText().toString());
                        }
                    }
                }
                if (Utils.isConnected(context)) {
                    dialog = Utils.showLoadingDialog(context);
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                    lifeAddSick(stringBuilder.toString());
                } else {
                    EasyToast.showShort(context, "网络未连接");
                }
                EventBus.getDefault().post(
                        new BankEvent(stringBuilder.toString(), "babyzhengzhuang"));
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
        String zhengzhuang = getIntent().getStringExtra("babyzhengzhuang");

        if (!TextUtils.isEmpty(zhengzhuang)) {

            String[] split = zhengzhuang.split(",");

            for (int i = 0; i < split.length; i++) {
                for (int i1 = 0; i1 < checkBoxes.size(); i1++) {
                    if (split[i].equals(checkBoxes.get(i1).getText())) {
                        checkBoxes.get(i1).setChecked(true);
                    }
                }
            }

        }


    }

    /**
     * 添加症状
     */
    private void lifeAddSick(String symptom) {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        params.put("symptom", symptom);
        params.put("user", "2");
        Log.e("RegisterActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/add_sick", "life/add_sick", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
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
