package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.OrderPay;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.CommomDialog;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

public class PayActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout rl_back;
    private TextView tv_ordernumber;
    private TextView tv_totalmoney;
    private ImageView img_weixin;
    private CheckBox Choosedweixin;
    private ImageView img_alipay;
    private CheckBox Choosedalipay;
    private Button btn_paynow;
    private TextView tv_money;
    private String orderid;
    private String order;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_ordernumber = (TextView) findViewById(R.id.tv_ordernumber);
        tv_totalmoney = (TextView) findViewById(R.id.tv_totalmoney);
        img_weixin = (ImageView) findViewById(R.id.img_weixin);
        Choosedweixin = (CheckBox) findViewById(R.id.Choosedweixin);
        img_alipay = (ImageView) findViewById(R.id.img_alipay);
        Choosedalipay = (CheckBox) findViewById(R.id.Choosedalipay);
        btn_paynow = (Button) findViewById(R.id.btn_paynow);
        tv_money = (TextView) findViewById(R.id.tv_money);
        orderid = getIntent().getStringExtra("orderid");
        order = getIntent().getStringExtra("order");
        if (!TextUtils.isEmpty(order)) {
            tv_ordernumber.setText(order);
        }

        Choosedalipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Choosedalipay.isChecked()) {
                    Choosedweixin.setChecked(false);
                } else {
                    Choosedweixin.setChecked(true);
                }
            }
        });

        Choosedweixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Choosedweixin.isChecked()) {
                    Choosedalipay.setChecked(false);
                } else {
                    Choosedalipay.setChecked(true);
                }
            }
        });


    }

    @Override
    protected void initListener() {
        btn_paynow.setOnClickListener(this);
        rl_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            orderPay();
        } else {
            finish();
            EasyToast.showShort(context, "网络未连接");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_paynow:
                startActivity(new Intent(context, GoodPayActivity.class)
                        .putExtra("order", order)
                        .putExtra("orderid", orderid));
                break;
            case R.id.rl_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 订单支付
     */
    private void orderPay() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("id", orderid);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("RegisterActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "order/pay", "order/pay", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    OrderPay orderPay = new Gson().fromJson(result, OrderPay.class);
                    if ("1".equals(String.valueOf(orderPay.getCode()))) {
                        tv_totalmoney.setText("￥" + orderPay.getOrder().getTotalprice());
                        tv_money.setText("￥" + orderPay.getOrder().getSfmoney());
                        tv_ordernumber.setText(orderPay.getOrder().getOrderid());
                    } else {
                        new CommomDialog(context, R.style.dialog, orderPay.getMsg(), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                    finish();
                                } else {
                                    dialog.dismiss();
                                    finish();
                                }
                            }
                        }).setTitle("提示").show();
                    }

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

    public static boolean isfinish = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (isfinish) {
            isfinish = !isfinish;
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("order/pay");
        System.gc();
    }
}
