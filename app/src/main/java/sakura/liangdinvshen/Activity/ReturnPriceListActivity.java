package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.ReturnPriceAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.OrderThindexBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.LiangDiRecycleView;
import sakura.liangdinvshen.View.ProgressView;
import sakura.liangdinvshen.View.SakuraLinearLayoutManager;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

public class ReturnPriceListActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout rl_back;
    private LiangDiRecycleView rc_my_return_price;
    private SakuraLinearLayoutManager line;
    private int p = 1;
    private ReturnPriceAdapter returnPriceAdapter;
    private RelativeLayout ll_empty;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_return_price;
    }

    @Override
    protected void initview() {
        ll_empty = (RelativeLayout) findViewById(R.id.LL_empty);
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        rc_my_return_price = (LiangDiRecycleView) findViewById(R.id.rc_my_return_price);
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        rc_my_return_price.setLayoutManager(line);
        rc_my_return_price.setItemAnimator(new DefaultItemAnimator());
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        ProgressView progressView = new ProgressView(context);
        progressView.setIndicatorId(ProgressView.BallRotate);
        progressView.setIndicatorColor(getResources().getColor(R.color.colorAccent));
        rc_my_return_price.setFootLoadingView(progressView);
        TextView textView = new TextView(context);
        textView.setText("-NOTMORE-");
        rc_my_return_price.setFootEndView(textView);
        rc_my_return_price.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                getData();
            }
        });
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getData();
    }

    public void getData() {
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            orderThindex();
        } else {
            EasyToast.showShort(context, "网络未连接");
            finish();
        }

    }

    /**
     * 退换货列表获取
     */
    private void orderThindex() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("p", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "order/thindex", "order/thindex", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    OrderThindexBean orderThindexBean = new Gson().fromJson(result, OrderThindexBean.class);

                    if ("0".equals(String.valueOf(orderThindexBean.getStu()))) {
                        ll_empty.setVisibility(View.VISIBLE);
                        return;
                    }

                    if (p == 1) {
                        if ("1".equals(String.valueOf(orderThindexBean.getStu()))) {
                            returnPriceAdapter = new ReturnPriceAdapter(orderThindexBean.getRes(), context);
                            rc_my_return_price.setAdapter(returnPriceAdapter);
                            rc_my_return_price.setEnabled(true);
                            rc_my_return_price.loadMoreComplete();
                            if (orderThindexBean.getRes().size() < 10) {
                                rc_my_return_price.setCanloadMore(false);
                                rc_my_return_price.loadMoreEnd();
                            }
                        } else {
                            rc_my_return_price.loadMoreComplete();
                            rc_my_return_price.setCanloadMore(false);
                        }
                    } else {
                        if ("1".equals(String.valueOf(orderThindexBean.getStu()))) {
                            returnPriceAdapter.setDatas(orderThindexBean.getRes());
                            rc_my_return_price.setEnabled(true);
                            rc_my_return_price.loadMoreComplete();
                            if (orderThindexBean.getRes().size() < 10) {
                                rc_my_return_price.setCanloadMore(false);
                                rc_my_return_price.loadMoreEnd();
                            }
                        } else {
                            rc_my_return_price.setVisibility(View.GONE);
                            ll_empty.setVisibility(View.VISIBLE);
                        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("order/thindex");
        System.gc();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            default:
                break;
        }
    }
}
