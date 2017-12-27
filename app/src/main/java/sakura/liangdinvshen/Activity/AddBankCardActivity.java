package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.BankIndexBean;
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
public class AddBankCardActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout rl_back;
    private EditText et_name;
    private EditText et_bank;
    private EditText et_bank_number;
    private EditText et_bank_number2;
    private Button btn_add_bankcard;
    private String type;
    private String id;
    private String name;
    private String bank;
    private String banknumber;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_add_bankcard_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        et_name = (EditText) findViewById(R.id.et_name);
        et_bank = (EditText) findViewById(R.id.et_bank);
        et_bank_number = (EditText) findViewById(R.id.et_bank_number);
        et_bank_number2 = (EditText) findViewById(R.id.et_bank_number2);
        btn_add_bankcard = (Button) findViewById(R.id.btn_add_bankcard);

    }

    @Override
    protected void initListener() {
        btn_add_bankcard.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        bank = intent.getStringExtra("bank");
        banknumber = intent.getStringExtra("banknumber");

        if ("save".equals(type)){
            et_bank.setText(bank);
            et_bank_number.setText(banknumber);
            et_bank_number2.setText(banknumber);
            et_name.setText(name);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_bankcard:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        String bank = et_bank.getText().toString().trim();
        if (TextUtils.isEmpty(bank)) {
            Toast.makeText(this, "请输入开户行银行", Toast.LENGTH_SHORT).show();
            return;
        }

        String number = et_bank_number.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "请输入银行账户", Toast.LENGTH_SHORT).show();
            return;
        }

        String number2 = et_bank_number2.getText().toString().trim();
        if (TextUtils.isEmpty(number2)) {
            Toast.makeText(this, "请确认银行账户", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!number.equals(number2)) {
            Toast.makeText(this, "两次输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Utils.isConnected(context)) {
            // TODO validate success, do something
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            if ("add".equals(type)) {
                bankAdd_bank();
            } else if ("save".equals(type)) {
                bankSave_bank();
            }
        } else {
            EasyToast.showShort(context, "网络未连接");
        }

    }

    /**
     * 添加银行卡
     */
    private void bankAdd_bank() {
        HashMap<String, String> params = new HashMap<>(5);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("name", et_name.getText().toString());
        params.put("bank", et_bank.getText().toString());
        params.put("no", et_bank_number2.getText().toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "bank/add_bank", "bank/add_bank", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    BankIndexBean bankIndexBean = new Gson().fromJson(result, BankIndexBean.class);
                    if ("1".equals(String.valueOf(bankIndexBean.getStu()))) {
                        finish();
                        EventBus.getDefault().post(
                                new BankEvent("initiated"));
                    }
                    result = null;
                    bankIndexBean = null;
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
     * 添加银行卡
     */
    private void bankSave_bank() {
        HashMap<String, String> params = new HashMap<>(5);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        params.put("name", et_name.getText().toString());
        params.put("bank", et_bank.getText().toString());
        params.put("no", et_bank_number2.getText().toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "bank/save_bank", "bank/save_bank", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    BankIndexBean bankIndexBean = new Gson().fromJson(result, BankIndexBean.class);
                    if ("1".equals(String.valueOf(bankIndexBean.getStu()))) {
                        finish();
                        EventBus.getDefault().post(
                                new BankEvent("hasdata"));
                    }
                    result = null;
                    bankIndexBean = null;
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
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("bank/save_bank");
        App.getQueues().cancelAll("bank/add_bank");
    }
}
