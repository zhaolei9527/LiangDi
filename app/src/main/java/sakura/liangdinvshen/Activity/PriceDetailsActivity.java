package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.VisitorTrack;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.hintview.IconHintView;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;

import sakura.liangdinvshen.Adapter.LoopAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.CountCartBean;
import sakura.liangdinvshen.Bean.GoodsCangBean;
import sakura.liangdinvshen.Bean.GoodsDetailBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.DensityUtils;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.PixelUtils;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Utils.WindowUtil;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

import static sakura.liangdinvshen.R.id.sdv_pingjia;
import static sakura.liangdinvshen.R.id.tv_countCart;

public class PriceDetailsActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout mRlBack;
    private RelativeLayout mRlShoppingcart;
    private com.jude.rollviewpager.RollPagerView RollPagerView;
    private TextView mTvTitle;
    private TextView mTvPrice;
    private TextView mTvPingjiaMax;
    private SimpleDraweeView mSdvPingjia;
    private TextView mTvPingjiaName;
    private LinearLayout mLlStar;
    private TextView mTvPingjiaTime;
    private TextView mTvPingjiaContent;
    private Button mBtnAllpingjia;
    private LinearLayout mLlHasPingjia;
    private TextView mTvNoPingjia;
    private SimpleDraweeView mSdvJilu;
    private TextView mTvJiluName;
    private TextView mTvJiluMax;
    private TextView mTvJiluTime;
    private Button mBtnAlljilu;
    private WebView mWb;
    private LinearLayout mLlHasJilv;
    private LinearLayout mLlShoucang;
    private LinearLayout mLlKefu;
    private TextView mTvAddshop;
    private TextView mShopnow;
    private Dialog dialog;
    private TextView mtvCountCart;
    private ImageView mImgShouCang;
    private GoodsDetailBean goodsDetailBean;
    private ImageView mimgPingjiaHuifu;
    private TextView mtvPingjiaHuifu;
    private String uid;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("goods/detail");
        App.getQueues().cancelAll("goods/cang");
        App.getQueues().cancelAll("goods/oncang");
        App.getQueues().cancelAll("cart/join_cart");
        System.gc();
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_price_details;
    }

    @Override
    protected void initview() {
        mimgPingjiaHuifu = (ImageView) findViewById(R.id.img_pingjia_huifu);
        mtvPingjiaHuifu = (TextView) findViewById(R.id.tv_pingjia_huifu);
        mRlBack = (FrameLayout) findViewById(R.id.rl_back);
        mImgShouCang = (ImageView) findViewById(R.id.img_shoucang);
        mRlShoppingcart = (RelativeLayout) findViewById(R.id.rl_shoppingcart);
        RollPagerView = (com.jude.rollviewpager.RollPagerView) findViewById(R.id.RollPagerView);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mtvCountCart = (TextView) findViewById(tv_countCart);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvPingjiaMax = (TextView) findViewById(R.id.tv_pingjia_max);
        mSdvPingjia = (SimpleDraweeView) findViewById(sdv_pingjia);
        mTvPingjiaName = (TextView) findViewById(R.id.tv_pingjia_name);
        mLlStar = (LinearLayout) findViewById(R.id.ll_star);
        mTvPingjiaTime = (TextView) findViewById(R.id.tv_pingjia_time);
        mTvPingjiaContent = (TextView) findViewById(R.id.tv_pingjia_content);
        mBtnAllpingjia = (Button) findViewById(R.id.btn_allpingjia);
        mLlHasPingjia = (LinearLayout) findViewById(R.id.ll_has_pingjia);
        mTvNoPingjia = (TextView) findViewById(R.id.tv_no_pingjia);
        mWb = (WebView) findViewById(R.id.wb);
        mLlShoucang = (LinearLayout) findViewById(R.id.ll_shoucang);
        mLlKefu = (LinearLayout) findViewById(R.id.ll_kefu);
        mTvAddshop = (TextView) findViewById(R.id.tv_addshop);
        mShopnow = (TextView) findViewById(R.id.shopnow);
        mBtnAllpingjia.setOnClickListener(this);
        String countCart = getIntent().getStringExtra("CountCart");
        if (!TextUtils.isEmpty(countCart)) {
            mtvCountCart.setText(countCart);
        }
        uid = (String) SpUtil.get(context, "uid", "");
    }

    @Override
    protected void initListener() {
        RollPagerView.setHintView(new IconHintView(context, R.drawable.shape_selected, R.drawable.shape_noraml, DensityUtils.dp2px(context, getResources().getDimension(R.dimen.x7))));
        RollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        RollPagerView.setPlayDelay(30000);
        ViewGroup.LayoutParams layoutParams = RollPagerView.getLayoutParams();
        layoutParams.height = (int) (WindowUtil.getScreenWidth(context) * 0.8);
        RollPagerView.setLayoutParams(layoutParams);
        mRlBack.setOnClickListener(this);
        mLlShoucang.setOnClickListener(this);
        mTvAddshop.setOnClickListener(this);
        mRlShoppingcart.setOnClickListener(this);
        mShopnow.setOnClickListener(this);
        mLlKefu.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        // 开启 localStorage
        mWb.getSettings().setDomStorageEnabled(true);
        // 设置支持javascript
        mWb.getSettings().setJavaScriptEnabled(true);
        // 启动缓存
        mWb.getSettings().setAppCacheEnabled(true);
        // 设置缓存模式
        mWb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //使用自定义的WebViewClient
        mWb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                // forumContext.loadUrl("javascript:(" + readJS() + ")()");
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                webView.measure(w, h);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                Toast.makeText(context, getString(R.string.hasError), Toast.LENGTH_SHORT).show();

            }
        });
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
        goodsDetailCache();
        goodsDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_kefu:
                if (TextUtils.isEmpty(uid)) {
                    EasyToast.showShort(context, "请先登录");
                    startActivity(new Intent(context, LoginActivity.class));
                    return;
                }

                if (Utils.isConnected(context)) {
                    if (ChatClient.getInstance().isLoggedInBefore()) {
                        //已经登录，可以直接进入会话界面
                        Intent intent = new IntentBuilder(context)
                                .setServiceIMNumber("liangdinvshen") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                                .setTitleName("靓蒂女神客服")
                                .setVisitorInfo(ContentFactory.createVisitorInfo(null)
                                        .phone(String.valueOf(SpUtil.get(context, "account", "")))
                                        .name(String.valueOf(SpUtil.get(context, "username", "")))
                                        .nickName(String.valueOf(SpUtil.get(context, "username", ""))))
                                .build();
                        startActivity(intent);
                        VisitorTrack track = ContentFactory.createVisitorTrack(null);
                        track.title("我正在看")  //显示标题
                                .price(mTvPrice.getText().toString()) //显示价格
                                .desc(mTvTitle.getText().toString()) //描述
                                .imageUrl(UrlUtils.URL + keFuImageUrl);//显示图片
                        final Message message = Message.createTxtSendMessage("我正在看," + mTvTitle.getText().toString(), "liangdinvshen");
                        message.addContent(track);
                        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
                            @Override
                            public void onSuccess() {
                                ChatClient.getInstance().chatManager().getConversation("liangdinvshen").removeMessage(message.getMsgId());
                            }

                            @Override
                            public void onError(int i, String s) {
                                ChatClient.getInstance().chatManager().getConversation("liangdinvshen").removeMessage(message.getMsgId());
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });

                    } else {
                        //未登录，需要登录后，再进入会话界面
                        dialog.show();
                        ChatClient.getInstance().login(String.valueOf(SpUtil.get(context, "uid", "")), String.valueOf(SpUtil.get(context, "uid", ""))
                                , new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        dialog.dismiss();
                                        Intent intent = new IntentBuilder(context)
                                                .setServiceIMNumber("liangdinvshen") //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                                                .setTitleName("靓蒂女神客服")
                                                .setVisitorInfo(ContentFactory.createVisitorInfo(null)
                                                        .phone(String.valueOf(SpUtil.get(context, "account", "")))
                                                        .name(String.valueOf(SpUtil.get(context, "username", "")))
                                                        .nickName(String.valueOf(SpUtil.get(context, "username", ""))))
                                                .build();
                                        startActivity(intent);

                                        VisitorTrack track = ContentFactory.createVisitorTrack(null);
                                        track.title("我正在看")  //显示标题
                                                .price(mTvPrice.getText().toString()) //显示价格
                                                .desc(mTvTitle.getText().toString()) //描述
                                                .imageUrl(UrlUtils.URL + keFuImageUrl);//显示图片
                                        final Message message = Message.createTxtSendMessage("", "liangdinvshen");
                                        message.addContent(track);
                                        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                ChatClient.getInstance().chatManager().getConversation("liangdinvshen").removeMessage(message.getMsgId());
                                            }

                                            @Override
                                            public void onError(int i, String s) {
                                                ChatClient.getInstance().chatManager().getConversation("liangdinvshen").removeMessage(message.getMsgId());
                                            }

                                            @Override
                                            public void onProgress(int i, String s) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        dialog.dismiss();
                                        EasyToast.showShort(context, "暂时无法访问客服");
                                    }

                                    @Override
                                    public void onProgress(int i, String s) {

                                    }
                                });
                    }
                } else {
                    EasyToast.showShort(context, "网络未连接");
                }
                break;
            case R.id.shopnow:
                if (TextUtils.isEmpty(uid)) {
                    EasyToast.showShort(context, "请先登录");
                    startActivity(new Intent(context, LoginActivity.class));
                    return;
                }



                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.valueOf(goodsDetailBean.getGoods().getId()));
                stringBuilder.append("," + goodsDetailBean.getGoods().getImg().get(0));
                stringBuilder.append("," + goodsDetailBean.getGoods().getTitle());
                stringBuilder.append("," + goodsDetailBean.getGoods().getPrice());
                stringBuilder.append("," + "1");
                stringBuilder.append("," + goodsDetailBean.getGoods().getId());
                Log.e("PriceDetailsActivity", stringBuilder.toString());
                Intent OrderActivityintent = new Intent(context, OrderActivity.class);
                OrderActivityintent.putExtra("order", stringBuilder.toString());
                OrderActivityintent.putExtra("price", goodsDetailBean.getGoods().getPrice());
                startActivity(OrderActivityintent);
                break;
            case R.id.btn_allpingjia:



                Intent intent = new Intent(context, EvaluationActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_shoucang:

                if (TextUtils.isEmpty(uid)) {
                    EasyToast.showShort(context, "请先登录");
                    startActivity(new Intent(context, LoginActivity.class));
                    return;
                }



                if ("0".equals(String.valueOf(goodsDetailBean.getIs_cang()))) {
                    goodsCang();
                    mImgShouCang.setBackground(getResources().getDrawable(R.mipmap.new_sc2));
                    EasyToast.showShort(context, "收藏成功");
                    goodsDetailBean.setIs_cang("1");
                } else {
                    goodsOnCang();
                    mImgShouCang.setBackground(getResources().getDrawable(R.mipmap.new_sc1));
                    EasyToast.showShort(context, "取消收藏");
                    goodsDetailBean.setIs_cang("0");
                }
                break;
            case R.id.tv_addshop:

                if (TextUtils.isEmpty(uid)) {
                    EasyToast.showShort(context, "请先登录");
                    startActivity(new Intent(context, LoginActivity.class));
                    return;
                }




                String kucun = goodsDetailBean.getGoods().getKucun();
                int kucuni = Integer.parseInt(kucun);
                if (kucuni > 1) {
                    goodsDetailBean.getGoods().setKucun(String.valueOf(kucuni - 1));
                    dialog.show();
                    cartJoinCart();
                } else {
                    EasyToast.showShort(context, "库存不足");
                }
                break;
            case R.id.rl_shoppingcart:

                if (TextUtils.isEmpty(uid)) {
                    EasyToast.showShort(context, "请先登录");
                    startActivity(new Intent(context, LoginActivity.class));
                    return;
                }

                startActivity(new Intent(context, ShopCarActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        uid = (String) SpUtil.get(context, "uid", "");
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        countCart();
    }

    /**
     * 购物车数量获取
     */
    private void countCart() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "cart/count_cart", "cart/count_cart", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    CountCartBean countCartBean = new Gson().fromJson(result, CountCartBean.class);
                    if ("1".equals(String.valueOf(countCartBean.getStatus()))) {
                        mtvCountCart.setText(countCartBean.getRes());
                    } else {
                        Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                    }
                    result = null;
                } catch (Exception e) {
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


    private String keFuImageUrl = "";

    /**
     * 产品缓存获取
     */
    private void goodsDetailCache() {
        String goodsDetail = (String) SpUtil.get(context, "goodsDetail" + String.valueOf(getIntent().getStringExtra("id")), "");
        if (!TextUtils.isEmpty(goodsDetail)) {
            try {
                GoodsDetailBean goodsDetailBean = new Gson().fromJson(goodsDetail, GoodsDetailBean.class);
                if ("310".equals(goodsDetailBean.getCode())) {
                    keFuImageUrl = goodsDetailBean.getGoods().getImg().get(0);
                    mTvTitle.setText(goodsDetailBean.getGoods().getTitle());
                    mTvPrice.setText("￥" + goodsDetailBean.getGoods().getPrice());
                    RollPagerView.setAdapter(new LoopAdapter(RollPagerView, goodsDetailBean.getGoods()));
                    if ("0".equals(String.valueOf(goodsDetailBean.getPj().getCount()))) {
                        mTvNoPingjia.setVisibility(View.VISIBLE);
                        mLlHasPingjia.setVisibility(View.GONE);
                    } else {
                        mTvNoPingjia.setVisibility(View.GONE);
                        mLlHasPingjia.setVisibility(View.VISIBLE);
                    }
                    if ("0".equals(String.valueOf(goodsDetailBean.getIs_cang()))) {
                        mImgShouCang.setBackground(getResources().getDrawable(R.mipmap.new_sc1));
                    } else {
                        mImgShouCang.setBackground(getResources().getDrawable(R.mipmap.new_sc2));
                    }

                } else {
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
                goodsDetail = null;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        } else {

        }
    }

    /**
     * 产品详情获取
     */
    private void goodsDetail() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("gid", String.valueOf(getIntent().getStringExtra("id")));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "goods/detail", "goods/detail", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    goodsDetailBean = new Gson().fromJson(result, GoodsDetailBean.class);
                    if ("310".equals(goodsDetailBean.getCode())) {
                        SpUtil.putAndApply(context, "goodsDetail" + String.valueOf(getIntent().getStringExtra("id")), result);
                        mTvTitle.setText(goodsDetailBean.getGoods().getTitle());
                        mTvPrice.setText("￥" + goodsDetailBean.getGoods().getPrice());
                        RollPagerView.setAdapter(new LoopAdapter(RollPagerView, goodsDetailBean.getGoods()));
                        Spanned spanned = Html.fromHtml(Utils.decode(goodsDetailBean.getGoods().getContent()));
                        Utils.inSetWebView(spanned.toString(), mWb, context);
                        if ("0".equals(String.valueOf(goodsDetailBean.getPj().getCount()))) {
                            mTvPingjiaMax.setText("宝贝评价（" + goodsDetailBean.getPj().getCount() + ")");
                            mTvNoPingjia.setVisibility(View.VISIBLE);
                            mLlHasPingjia.setVisibility(View.GONE);
                        } else {
                            mTvNoPingjia.setVisibility(View.GONE);
                            mLlHasPingjia.setVisibility(View.VISIBLE);
                            mTvPingjiaMax.setText("宝贝评价(" + goodsDetailBean.getPj().getCount() + ")");
                            mSdvPingjia.setImageURI(UrlUtils.URL + goodsDetailBean.getPj().getHeadimg());
                            mTvPingjiaName.setText(goodsDetailBean.getPj().getNickname());
                            mTvPingjiaContent.setText(goodsDetailBean.getPj().getContent());
                            String addtime = goodsDetailBean.getPj().getAddtime();
                            mTvPingjiaTime.setText(String.valueOf(DateUtils.getDay(Long.parseLong(addtime) * 1000)));
                            String xing = goodsDetailBean.getPj().getXing();
                            Double i = Double.parseDouble(xing);
                            for (int i1 = 0; i1 < 5; i1++) {
                                ImageView imageView = new ImageView(context);
                                imageView.setPadding(PixelUtils.dip2px(context, 3), 0, 0, 0);
                                if (i1 < i) {
                                    imageView.setBackgroundResource(R.mipmap.shoucang);
                                } else {
                                    imageView.setBackgroundResource(R.mipmap.new_sc1);
                                }
                                mLlStar.addView(imageView);
                            }
                            if (TextUtils.isEmpty(goodsDetailBean.getPj().getHf())) {
                                mimgPingjiaHuifu.setVisibility(View.GONE);
                                mtvPingjiaHuifu.setVisibility(View.GONE);
                            } else {
                                mimgPingjiaHuifu.setVisibility(View.VISIBLE);
                                mtvPingjiaHuifu.setVisibility(View.VISIBLE);
                                mtvPingjiaHuifu.setText("掌柜回复：" + goodsDetailBean.getPj().getHf());
                            }

                        }
                        if ("0".equals(String.valueOf(goodsDetailBean.getIs_cang()))) {
                            mImgShouCang.setBackground(getResources().getDrawable(R.mipmap.new_sc1));
                        } else {
                            mImgShouCang.setBackground(getResources().getDrawable(R.mipmap.new_sc2));
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    result = null;
                } catch (Exception e) {
                    dialog.dismiss();
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
     * 收藏产品
     */
    private void goodsCang() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("gid", String.valueOf(getIntent().getStringExtra("id")));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "goods/cang", "goods/cang", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    GoodsCangBean goodsCangBean = new Gson().fromJson(result, GoodsCangBean.class);
                    if ("307".equals(String.valueOf(goodsCangBean.getCode()))) {
                    } else {
                        goodsDetailBean.setIs_cang("0");
                        mImgShouCang.setBackgroundResource(R.mipmap.new_sc1);
                        EasyToast.showShort(context, "收藏失败");
                    }
                    goodsCangBean = null;
                    result = null;
                } catch (Exception e) {
                    dialog.dismiss();
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
     * 取消收藏
     */
    private void goodsOnCang() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("gid", String.valueOf(getIntent().getStringExtra("id")));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("PriceDetailsActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "goods/nocang", "goods/nocang", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    GoodsCangBean goodsCangBean = new Gson().fromJson(result, GoodsCangBean.class);
                    if ("310".equals(String.valueOf(goodsCangBean.getCode()))) {
                    } else {
                        goodsDetailBean.setIs_cang("1");
                        mImgShouCang.setBackgroundResource(R.mipmap.new_sc2);
                        EasyToast.showShort(context, "取消失败");
                    }
                    goodsCangBean = null;
                    result = null;
                } catch (Exception e) {
                    dialog.dismiss();
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
     * 加入购物车
     */
    private void cartJoinCart() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("gid", String.valueOf(getIntent().getStringExtra("id")));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("number", "1");
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "cart/join_cart", "cart/join_cart", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    GoodsCangBean goodsCangBean = new Gson().fromJson(result, GoodsCangBean.class);
                    if ("323".equals(String.valueOf(goodsCangBean.getCode()))) {
                        EasyToast.showShort(context, "已成功加入购物车");
                        if (!"1".equals(String.valueOf(goodsCangBean.getIs_have()))) {
                            String s = mtvCountCart.getText().toString();
                            int i = Integer.parseInt(s);
                            mtvCountCart.setText(String.valueOf(i + 1));
                        }
                    } else if ("327".equals(String.valueOf(goodsCangBean.getCode()))) {
                        EasyToast.showShort(context, "加入失败，库存不足");
                        String s = mtvCountCart.getText().toString();
                        int i = Integer.parseInt(s);
                        mtvCountCart.setText(String.valueOf(i - 1));
                    } else {
                        EasyToast.showShort(context, "加入购物车失败");
                        String kucun = goodsDetailBean.getGoods().getKucun();
                        int kucuni = Integer.parseInt(kucun);
                        goodsDetailBean.getGoods().setKucun(String.valueOf(kucuni + 1));
                    }
                    goodsCangBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                String s = mtvCountCart.getText().toString();
                int i = Integer.parseInt(s);
                mtvCountCart.setText(String.valueOf(i - 1));
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

