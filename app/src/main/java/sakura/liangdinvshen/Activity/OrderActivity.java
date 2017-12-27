package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.OrderBean;
import sakura.liangdinvshen.Bean.OrderIndexBean;
import sakura.liangdinvshen.Bean.SuckleCartBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.CommomDialog;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

import static sakura.liangdinvshen.R.id.tv_money;

public class OrderActivity extends BaseActivity {

    private FrameLayout mRlBack;
    private RelativeLayout mRlTitle;
    private ImageView mImgDizhi;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvDizhi;
    private TextView mTvAddDizhi;
    private RelativeLayout mRlChangeDizhi;
    private SimpleDraweeView mSimpleDraweeView;
    private TextView mTvTitle;
    private TextView mTvClassify;
    private TextView mTvSize;
    private FrameLayout mItemOrderIlayout;
    private TextView mTvNtegral;
    private CheckBox mChoosedNtegral;
    private TextView mTvBalance;
    private CheckBox mChoosedBalance;
    private TextView mTvPrice;
    private TextView mTvFreight;
    private TextView mTvTotal;
    private TextView mShopnow;
    private TextView mTvMoney;
    private TextView mTvCheckAddress;
    private Dialog dialog;
    ArrayList<SuckleCartBean.ResBean> datas = new ArrayList<>();
    private LinearLayout ll_oreders;
    private double pricev;
    private double freightv1;
    private String jifen;
    private String yue;
    private double jifenv;
    private double yuev;
    private int addressCode = 200;
    private OrderIndexBean orderIndexBean;
    private RelativeLayout rl_jifen;
    private RelativeLayout rl_yue;
    private int jfdk;
    private String addressID = "";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("order/index");
        App.getQueues().cancelAll("order/order");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == addressCode) {
            String name = data.getStringExtra("name");
            mTvName.setText(name);
            String phone = data.getStringExtra("phone");
            mTvPhone.setText(phone);
            String address = data.getStringExtra("address");
            mTvDizhi.setText(address);
            addressID = data.getStringExtra("addressid");
        }
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_order;
    }

    @Override
    protected void initview() {
        rl_jifen = (RelativeLayout) findViewById(R.id.rl_jifen);
        rl_yue = (RelativeLayout) findViewById(R.id.rl_yue);
        mRlBack = (FrameLayout) findViewById(R.id.rl_back);
        ll_oreders = (LinearLayout) findViewById(R.id.ll_oreders);
        mRlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mImgDizhi = (ImageView) findViewById(R.id.img_dizhi);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvDizhi = (TextView) findViewById(R.id.tv_dizhi);
        mTvCheckAddress = (TextView) findViewById(R.id.tv_check_address);
        mTvAddDizhi = (TextView) findViewById(R.id.tv_add_dizhi);
        mRlChangeDizhi = (RelativeLayout) findViewById(R.id.rl_change_dizhi);
        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.SimpleDraweeView);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvClassify = (TextView) findViewById(R.id.tv_classify);
        mTvSize = (TextView) findViewById(R.id.tv_size);
        mItemOrderIlayout = (FrameLayout) findViewById(R.id.item_orderIlayout);
        mTvNtegral = (TextView) findViewById(R.id.tv_ntegral);
        mChoosedNtegral = (CheckBox) findViewById(R.id.Choosed_ntegral);
        mTvBalance = (TextView) findViewById(R.id.tv_balance);
        mChoosedBalance = (CheckBox) findViewById(R.id.Choosed_balance);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvFreight = (TextView) findViewById(R.id.tv_freight);
        mTvTotal = (TextView) findViewById(R.id.tv_total);
        mShopnow = (TextView) findViewById(R.id.shopnow);
        mTvMoney = (TextView) findViewById(tv_money);
    }

    @Override
    protected void initListener() {
        mRlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRlChangeDizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, AddressActivitry.class).putExtra("type", "backAddress"), addressCode);
            }
        });
        mShopnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < datas.size(); i++) {
                    if (stringBuilder.length() != 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(datas.get(i).getGid() + "_");
                    stringBuilder.append(datas.get(i).getId() + "_");
                    stringBuilder.append(datas.get(i).getNumber());
                }
                orderOrder(addressID, stringBuilder.toString());
            }
        });

        /**
         * 余额
         * */
        mChoosedBalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mChoosedBalance.setChecked(true);
                    mChoosedNtegral.setChecked(false);
                    double v = pricev + freightv1 - yuev;
                    if (v < 0) {
                        v = 0;
                    }
                    mTvMoney.setText("￥" + Utils.formatDouble(v));
                } else {
                    if (!mChoosedNtegral.isChecked()) {
                        double v = pricev + freightv1;
                        mTvMoney.setText("￥" + Utils.formatDouble(v));
                    }
                }
            }
        });

        /**
         * 积分
         * */
        mChoosedNtegral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mChoosedBalance.setChecked(false);
                    mChoosedNtegral.setChecked(true);
                    double v = pricev + freightv1 - jfdk;
                    if (v < 0) {
                        v = 0;
                    }
                    mTvMoney.setText("￥" + Utils.formatDouble(v));
                } else {
                    if (!mChoosedBalance.isChecked()) {
                        double v = pricev + freightv1;
                        mTvMoney.setText("￥" + Utils.formatDouble(v));
                    }
                }
            }
        });

    }

    @Override
    protected void initData() {
        String order = getIntent().getStringExtra("order");
        String price = getIntent().getStringExtra("price");
        jifen = (String) SpUtil.get(context, "jifen", "");
        if (!TextUtils.isEmpty(jifen)) {
            jifenv = Double.parseDouble(jifen);
            if (jifenv == 0) {
                rl_jifen.setVisibility(View.GONE);
            } else {
                rl_jifen.setVisibility(View.VISIBLE);
                mTvNtegral.setText(Utils.formatDouble(jifenv));
            }
        } else {
            rl_jifen.setVisibility(View.GONE);
        }
        yue = (String) SpUtil.get(context, "money", "");
        if (!TextUtils.isEmpty(yue)) {
            yuev = Double.parseDouble(yue);
            if (yuev == 0) {
                rl_yue.setVisibility(View.GONE);
            } else {
                rl_yue.setVisibility(View.VISIBLE);
                mTvBalance.setText(Utils.formatDouble(yuev));
            }
        } else {
            rl_yue.setVisibility(View.GONE);
        }
        mTvPrice.setText(price);
        Log.e("OrderActivity", order);
        String[] split = order.split("&");

        for (int i = 0; i < split.length; i++) {
            SuckleCartBean.ResBean resBean = new SuckleCartBean.ResBean();
            String[] split1 = split[i].toString().split(",");
            resBean.setGid(split1[0]);
            resBean.setImg(split1[1]);
            resBean.setTitle(split1[2]);
            resBean.setPrice(split1[3]);
            resBean.setNumber(split1[4]);
            resBean.setId(split1[5]);
            datas.add(resBean);
        }

        for (int i = 0; i < datas.size(); i++) {
            View inflate = View.inflate(context, R.layout.item_oreder_layout, null);
            SimpleDraweeView simpleDraweeView = (com.facebook.drawee.view.SimpleDraweeView) inflate.findViewById(R.id.SimpleDraweeView);
            simpleDraweeView.setImageURI(UrlUtils.URL + datas.get(i).getImg());
            TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
            tv_title.setText(datas.get(i).getTitle());
            TextView tv_classify = (TextView) inflate.findViewById(R.id.tv_classify);
            tv_classify.setText("￥" + datas.get(i).getPrice());
            TextView tv_size = (TextView) inflate.findViewById(R.id.tv_size);
            tv_size.setText("×" + datas.get(i).getNumber());
            ll_oreders.addView(inflate);
        }

        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            orderIndex();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
    }

    /**
     * 获取订单信息
     */
    private void orderIndex() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "order/index", "order/index", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("orderIndex", result);
                try {
                    orderIndexBean = new Gson().fromJson(result, OrderIndexBean.class);
                    if (!"2".equals(String.valueOf(orderIndexBean.getIs_addr()))) {
                        mTvAddDizhi.setVisibility(View.VISIBLE);
                        mTvName.setVisibility(View.INVISIBLE);
                        mTvDizhi.setVisibility(View.INVISIBLE);
                        mTvPhone.setVisibility(View.INVISIBLE);
                    } else {
                        mTvAddDizhi.setVisibility(View.INVISIBLE);
                        mTvName.setVisibility(View.VISIBLE);
                        mTvName.setText(orderIndexBean.getAddress().getName());
                        mTvDizhi.setVisibility(View.VISIBLE);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(orderIndexBean.getAddress().getProvince());
                        stringBuilder.append(orderIndexBean.getAddress().getCity());
                        stringBuilder.append(orderIndexBean.getAddress().getCountry());
                        stringBuilder.append(orderIndexBean.getAddress().getAddress());
                        mTvDizhi.setText(stringBuilder.toString());
                        mTvPhone.setVisibility(View.VISIBLE);
                        mTvPhone.setText(orderIndexBean.getAddress().getTel());
                        mTvFreight.setText("￥" + orderIndexBean.getYunfei());
                        String Price = mTvPrice.getText().toString().replace("￥", "");
                        String Freight = mTvFreight.getText().toString().replace("￥", "");
                        pricev = Double.parseDouble(Price);
                        freightv1 = Double.parseDouble(Freight);
                        double totol = pricev + freightv1;
                        mTvTotal.setText("￥" + totol);
                        mTvMoney.setText("￥" + totol);
                        //地址id
                        addressID = orderIndexBean.getAddress().getId();
                        //积分抵扣
                        jfdk = orderIndexBean.getJfdk();
                        //积分条件
                        String jf_tj = orderIndexBean.getJf_tj();
                        double jftjv = Double.parseDouble(jf_tj);

                        if (jftjv <= totol) {
                            rl_jifen.setVisibility(View.VISIBLE);
                        } else {
                            rl_jifen.setVisibility(View.GONE);
                        }


                        //矫正余额
                        yue = orderIndexBean.getMoney();
                        if (!TextUtils.isEmpty(yue)) {
                            yuev = Double.parseDouble(yue);
                            if (yuev == 0) {
                                rl_yue.setVisibility(View.GONE);
                            } else {
                                rl_yue.setVisibility(View.VISIBLE);
                                mTvBalance.setText(Utils.formatDouble(yuev));
                            }
                        } else {
                            rl_yue.setVisibility(View.GONE);
                        }
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
     * 订单生成
     */
    private void orderOrder(String addressId, String zhi) {
        HashMap<String, String> params = new HashMap<>(5);
        params.put("key", UrlUtils.KEY);
        params.put("zhi", zhi);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("addressid", addressId);
        if (mChoosedBalance.isChecked()) {
            params.put("syyue", "1");
        } else {
            params.put("syyue", "0");
        }
        if (mChoosedNtegral.isChecked()) {
            params.put("syjifen", "1");
        } else {
            params.put("syjifen", "0");
        }
        Log.e("RegisterActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "order/order", "order/order", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    OrderBean orderBean = new Gson().fromJson(result, OrderBean.class);
                    if ("1".equals(String.valueOf(orderBean.getCode()))) {
                        ShopCarActivity.makeref = true;
                        String Money = mTvMoney.getText().toString().replace("￥", "");
                        double v = Double.parseDouble(Money);
                        if (v == 0) {
                            startActivity(new Intent(context, GoodPayActivity.class)
                                    .putExtra("order", orderBean.getOrder())
                                    .putExtra("orderid", orderBean.getOrderid())
                                    .putExtra("type", "good"));
                        } else {
                            startActivity(new Intent(context, PayActivity.class)
                                    .putExtra("order", orderBean.getOrder())
                                    .putExtra("orderid", orderBean.getOrderid())
                                    .putExtra("money", Utils.formatDouble(v))
                                    .putExtra("id", orderBean.getOrderid()));
                        }
                        finish();
                    } else if ("211".equals(String.valueOf(orderBean.getCode()))) {
                        EasyToast.showShort(context, "商品已失效");
                    } else if ("212".equals(String.valueOf(orderBean.getCode()))) {
                        EasyToast.showShort(context, "订单信息错误");
                        finish();
                    } else if ("213".equals(String.valueOf(orderBean.getCode()))) {
                        EasyToast.showShort(context, "商品库存不足");
                    } else if ("214".equals(String.valueOf(orderBean.getCode()))) {
                        EasyToast.showShort(context, "该订单无法使用积分支付");
                    } else if ("215".equals(String.valueOf(orderBean.getCode()))) {
                        EasyToast.showShort(context, "订单生成失败！");
                    } else if ("902".equals(String.valueOf(orderBean.getCode()))) {
                        new CommomDialog(context, R.style.dialog, orderBean.getMsg(), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                } else {
                                    dialog.dismiss();
                                }
                            }
                        }).setTitle("提示").show();
                    } else if ("901".equals(String.valueOf(orderBean.getCode()))) {
                        new CommomDialog(context, R.style.dialog, orderBean.getMsg(), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                } else {
                                    dialog.dismiss();
                                }
                            }
                        }).setTitle("提示").show();
                    } else {
                        EasyToast.showShort(context, "订单生成失败！");
                    }
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
