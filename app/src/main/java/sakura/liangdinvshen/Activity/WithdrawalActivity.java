package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.Bean.WithdrawIndexBean;
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
 * @date 2017/12/23
 * 功能描述：
 */
public class WithdrawalActivity extends BaseActivity implements View.OnClickListener {


    private FrameLayout rl_back;
    private TextView tv_withdrawl_list;
    private TextView tv_bank;
    private EditText et_money;
    private TextView tv_money;
    private EditText et_phonecode;
    private Button btn_getSMScode;
    private Dialog dialog;
    private Button btn_submit;

    @Override
    protected int setthislayout() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void initview() {
        btn_submit = (Button) findViewById(R.id.btn_submit);
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_withdrawl_list = (TextView) findViewById(R.id.tv_withdrawl_list);
        tv_bank = (TextView) findViewById(R.id.tv_bank);
        et_money = (EditText) findViewById(R.id.et_money);
        tv_money = (TextView) findViewById(R.id.tv_money);
        et_phonecode = (EditText) findViewById(R.id.et_phonecode);
        btn_getSMScode = (Button) findViewById(R.id.btn_getSMScode);


    }

    @Override
    protected void initListener() {
        btn_getSMScode.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        tv_withdrawl_list.setOnClickListener(this);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            withdrawIndex();
        } else {
            EasyToast.showShort(context, "网络未连接");
            finish();
        }
    }

    private Timer timer;
    private TimerTask task;
    private int time = 60;

    private void getcaptcha() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time--;
                        btn_getSMScode.setText("" + time);
                        if (time < 0) {
                            timer.cancel();
                            btn_getSMScode.setText("获取验证码");
                            btn_getSMScode.setEnabled(true);
                            time = 60;
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
        //// TODO: 2017/5/18  发送验证码
        if (Utils.isConnected(context)) {
            withdrawGetSms();
        } else {
            Toast.makeText(context, getString(R.string.Networkexception), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getSMScode:
                if (time == 60) {
                    getcaptcha();
                }
                break;
            case R.id.btn_submit:
                submit();
                break;
            case R.id.tv_withdrawl_list:
                startActivity(new Intent(context, WithdrawlListActivity.class));
                break;
            default:
                break;
        }
    }

    private void submit() {
        // validate
        String money = et_money.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            Toast.makeText(this, "请填写申请金额", Toast.LENGTH_SHORT).show();
            return;
        }

        String phonecode = et_phonecode.getText().toString().trim();
        if (TextUtils.isEmpty(phonecode)) {
            Toast.makeText(this, "请输入短信验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something

        double v = Double.parseDouble(money);

        if (v < 10) {
            Toast.makeText(this, "提现金额必须大于等于十元", Toast.LENGTH_SHORT).show();
            return;
        }

        double v1 = Double.parseDouble(withdrawIndexBean.getRes().getMoney());

        double v2 = Double.parseDouble(et_money.getText().toString());

        if (v2 > v1) {
            EasyToast.showShort(context, "超出可提现金额");
            return;
        }

        if (Utils.isConnected(context)) {
            dialog.show();
            withdrawSubmitForm();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
    }

    private WithdrawIndexBean withdrawIndexBean;

    /**
     * 银行卡信息获取
     */
    private void withdrawIndex() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "withdraw/index", "withdraw/index", params, new VolleyInterface(context) {


            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    withdrawIndexBean = new Gson().fromJson(result, WithdrawIndexBean.class);
                    if ("1".equals(String.valueOf(withdrawIndexBean.getStu()))) {
                        String no = withdrawIndexBean.getRes().getBank_detail().getNo();
                        String substring = no.substring(no.length() - 4, no.length());
                        tv_bank.setText(withdrawIndexBean.getRes().getBank_detail().getBank() + "(" + substring + ")");
                        tv_money.setText("￥" + withdrawIndexBean.getRes().getMoney());

                    } else {

                    }
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    EasyToast.showShort(context, "请先添加银行卡");
                    finish();
                    startActivity(new Intent(context, BankCardActivity.class));
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


    /**
     * 短信验证码获取
     */
    private void withdrawGetSms() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "withdraw/get_sms", "withdraw/get_sms", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(context, "发送成功");
                    } else {
                        time = 0;
                        EasyToast.showShort(context, "发送失败");
                    }
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                time = 0;
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 提现申请提交
     */
    private void withdrawSubmitForm() {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("bank_id", withdrawIndexBean.getRes().getBank_detail().getId());
        params.put("bank_num", et_money.getText().toString());
        params.put("yzm", et_phonecode.getText().toString());
        Log.e("WithdrawalActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "withdraw/submit_form", "withdraw/submit_form", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                dialog.dismiss();
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(context, "操作成功");
                        finish();
                    } else {
                        EasyToast.showShort(context, stuBean.getError_msg());
                    }
                    result = null;
                } catch (Exception e) {
                    dialog.dismiss();
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

}
