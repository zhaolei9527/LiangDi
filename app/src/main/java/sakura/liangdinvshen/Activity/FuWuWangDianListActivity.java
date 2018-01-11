package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.FuWuWangDianListAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.WangListBean;
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

public class FuWuWangDianListActivity extends BaseActivity {

    private FrameLayout rl_back;
    private LiangDiRecycleView rv_purchaserecord;
    private SakuraLinearLayoutManager line;
    private int p = 1;
    private FuWuWangDianListAdapter adapter;
    private Dialog dialog;
    private RelativeLayout ll_empty;
    private RelativeLayout rl_search;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("wang/wang_list");
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_fuwuwangdianlist;
    }

    @Override
    protected void initview() {
        rl_search = (RelativeLayout) findViewById(R.id.rl_search);
        ll_empty = (RelativeLayout) findViewById(R.id.LL_empty);
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        rv_purchaserecord = (LiangDiRecycleView) findViewById(R.id.rv_purchaserecord);
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        rv_purchaserecord.setLayoutManager(line);
        rv_purchaserecord.setItemAnimator(new DefaultItemAnimator());
        ProgressView progressView = new ProgressView(context);
        progressView.setIndicatorId(ProgressView.BallRotate);
        progressView.setIndicatorColor(getResources().getColor(R.color.colorAccent));
        rv_purchaserecord.setFootLoadingView(progressView);
        TextView textView = new TextView(context);
        textView.setText("-我也是有底线的-");
        rv_purchaserecord.setFootEndView(textView);
        rv_purchaserecord.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                wangQb_list();
            }
        });
        dialog = Utils.showLoadingDialog(context);
    }

    public void getData() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
        if (Utils.isConnected(context)) {
            wangQb_list();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
    }

    private String lastKeywords = "";

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, FuWuWangDianSearchActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        getData();
    }

    /**
     * 服务网点获取
     */
    private void wangQb_list() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("p", String.valueOf(p));
        params.put("keywords", lastKeywords);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "wang/wang_list", "wang/wang_list", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    WangListBean wangListBean = new Gson().fromJson(result, WangListBean.class);
                    rv_purchaserecord.loadMoreComplete();
                    if ("1".equals(String.valueOf(wangListBean.getCode()))) {
                        ll_empty.setVisibility(View.GONE);
                        if (p == 1) {
                            adapter = new FuWuWangDianListAdapter(wangListBean.getList(), context);
                            rv_purchaserecord.setAdapter(adapter);
                        } else {
                            adapter.setDatas(wangListBean.getList());
                        }
                        if (rv_purchaserecord != null) {
                            rv_purchaserecord.loadMoreComplete();
                        }
                        if (wangListBean.getList().size() < 10) {
                            rv_purchaserecord.setCanloadMore(false);
                            if (p != 1) {
                                rv_purchaserecord.loadMoreEnd();
                            }
                        }
                    } else {
                        ll_empty.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                EasyToast.showShort(context, getString(R.string.Abnormalserver));
            }
        });
    }

}
