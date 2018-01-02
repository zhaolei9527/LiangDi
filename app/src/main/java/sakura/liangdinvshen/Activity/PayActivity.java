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
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.OrderPay;
import sakura.liangdinvshen.Bean.OrderWxpayBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.Constants;
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
    private IWXAPI api;

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
        //反注册EventBus
        EventBus.getDefault().unregister(PayActivity.this);
        System.gc();
    }

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

        //注册EventBus
        if (!EventBus.getDefault().isRegistered(PayActivity.this)) {
            EventBus.getDefault().register(PayActivity.this);
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

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        api.registerApp(Constants.APP_ID);
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
                if (Utils.isConnected(context)) {
                    dialog.show();
                    if (Choosedalipay.isChecked()) {
                    } else if (Choosedweixin.isChecked()) {
                        orderWxpay();
                    } else {
                        EasyToast.showShort(context, "请选择支付方式");
                    }
                } else {
                    EasyToast.showShort(context, "网络未连接");
                }
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

    /**
     * 订单支付
     */
    private void orderWxpay() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("id", orderid);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("RegisterActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "order/wxpay", "order/wxpay", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    OrderWxpayBean orderWxpayBean = new Gson().fromJson(result, OrderWxpayBean.class);
                    if (api != null) {
                        PayReq req = new PayReq();
                        req.appId = Constants.APP_ID;
                        req.partnerId = orderWxpayBean.getData().getMch_id();
                        req.prepayId = orderWxpayBean.getData().getPrepay_id();
                        req.packageValue = "Sign=WXPay";
                        req.nonceStr = orderWxpayBean.getData().getNonceStr();
                        req.timeStamp = orderWxpayBean.getData().getTimeStamp();
                        req.sign = "Sign=WXPay";
                        api.sendReq(req);
                    }
                    orderWxpayBean = null;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BankEvent event) {
        if (!TextUtils.isEmpty(event.getmType())) {
            if ("pay".equals(event.getmType())) {
                if ("good".equals(event.getMsg())) {
                    startActivity(new Intent(context, GoodPayActivity.class)
                            .putExtra("type", "good")
                            .putExtra("order", order)
                            .putExtra("orderid", orderid));
                    finish();
                } else if ("bad".equals(event.getMsg())) {
                    startActivity(new Intent(context, GoodPayActivity.class)
                            .putExtra("order", order)
                            .putExtra("orderid", orderid));
                    finish();
                }
            }
        }
    }

}
