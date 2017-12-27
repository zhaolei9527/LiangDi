package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hss01248.frescopicker.FrescoIniter;

import java.util.ArrayList;
import java.util.HashMap;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.OrderThdetailBean;
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
 * @date 2017/12/21
 * 功能描述：
 */
public class ReturnPriceDetailsActivity extends BaseActivity {

    private Dialog dialog;
    private String id;
    private FrameLayout rl_back;
    private TextView tv_type_content;
    private TextView tv_time;
    private SimpleDraweeView SimpleDraweeView;
    private TextView tv_title;
    private TextView tv_classify;
    private TextView tv_size;
    private FrameLayout item_orderIlayout;
    private TextView tv_shuoming;
    private me.iwf.photopicker.widget.MultiPickResultView gv_img;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("order/thdetail");
        System.gc();
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_return_price_details;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_type_content = (TextView) findViewById(R.id.tv_type_content);
        tv_time = (TextView) findViewById(R.id.tv_time);
        SimpleDraweeView = (SimpleDraweeView) findViewById(R.id.SimpleDraweeView);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_classify = (TextView) findViewById(R.id.tv_classify);
        tv_size = (TextView) findViewById(R.id.tv_size);
        item_orderIlayout = (FrameLayout) findViewById(R.id.item_orderIlayout);
        tv_shuoming = (TextView) findViewById(R.id.tv_shuoming);
        gv_img = (me.iwf.photopicker.widget.MultiPickResultView) findViewById(R.id.recycler_view);
        PhotoPickUtils.init(getApplicationContext(), new FrescoIniter());//第二个参数根据具体依赖库而定
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            if (Utils.isConnected(context)) {
                dialog = Utils.showLoadingDialog(context);
                dialog.show();
                orderThdetail();
            } else {
                EasyToast.showShort(context, "网络未连接");
                finish();
            }
        }
    }

    /**
     * 免责声明获取
     */
    private void orderThdetail() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("id", id);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "order/thdetail", "order/thdetail", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    OrderThdetailBean orderThdetailBean = new Gson().fromJson(result, OrderThdetailBean.class);
                    if ("1".equals(String.valueOf(orderThdetailBean.getStu()))) {
                        if ("-1".equals(String.valueOf(orderThdetailBean.getInfo().getStatus()))) {
                            tv_type_content.setText("退换状态：取消申请");
                        } else if ("1".equals(String.valueOf(orderThdetailBean.getInfo().getStatus()))) {
                            tv_type_content.setText("退换状态：申请中");
                        } else if ("2".equals(String.valueOf(orderThdetailBean.getInfo().getStatus()))) {
                            tv_type_content.setText("退换状态：通过");
                        } else if ("3".equals(String.valueOf(orderThdetailBean.getInfo().getStatus()))) {
                            tv_type_content.setText("退换状态：未通过");
                        }
                        tv_time.setText(DateUtils.getMillon(Long.parseLong(orderThdetailBean.getInfo().getAddtime()) * 1000));
                        tv_size.setText("×" + orderThdetailBean.getInfo().getNumber());
                        tv_title.setText(orderThdetailBean.getGoods().getTitle());
                        tv_title.setTag(orderThdetailBean.getGoods().getIs_show());
                        tv_classify.setText("￥" + orderThdetailBean.getGoods().getPrice());
                        tv_shuoming.setText(orderThdetailBean.getInfo().getThbeizhu());
                        SimpleDraweeView.setImageURI(UrlUtils.URL + orderThdetailBean.getGoods().getImg());
                        ArrayList<String> paths = new ArrayList<String>();
                        for (int i = 0; i < orderThdetailBean.getInfo().getImg().size(); i++) {
                            paths.add(UrlUtils.URL + orderThdetailBean.getInfo().getImg().get(i));
                        }
                        gv_img.init(ReturnPriceDetailsActivity.this, MultiPickResultView.ACTION_ONLY_SHOW, paths);
                        item_orderIlayout.setTag(orderThdetailBean.getGoods().getId());
                        item_orderIlayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("1".equals((String) tv_title.getTag())) {
                                    Intent intent = new Intent(context, PriceDetailsActivity.class);
                                    intent.putExtra("id", (String) item_orderIlayout.getTag());
                                    startActivity(intent);
                                } else {
                                    EasyToast.showShort(context, "商品已下架");
                                }
                            }
                        });

                    }
                    orderThdetailBean = null;
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
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
