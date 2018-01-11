package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.XfListAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.XflistBean;
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

public class XfListActivity extends BaseActivity {

    private FrameLayout rl_back;
    private LiangDiRecycleView rv_purchaserecord;
    private SakuraLinearLayoutManager line;
    private int p = 1;
    private XfListAdapter evaluationAdapter;
    private Dialog dialog;
    private EditText et_search;
    private ImageView img_search;
    private RelativeLayout ll_empty;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("wang/xflist");
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_evaluation;
    }

    @Override
    protected void initview() {
        ll_empty = (RelativeLayout) findViewById(R.id.LL_empty);
        et_search = (EditText) findViewById(R.id.et_search);
        img_search = (ImageView) findViewById(R.id.img_search);
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
                sucklePingjia();
            }
        });
        dialog = Utils.showLoadingDialog(context);
    }

    public void getData() {
        dialog.show();
        if (Utils.isConnected(context)) {
            sucklePingjia();
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

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_search.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    EasyToast.showShort(context, et_search.getHint().toString());
                } else {
                    lastKeywords = et_search.getText().toString();
                    if (Utils.isConnected(context)) {
                        getData();
                    } else {
                        EasyToast.showShort(context, "网络未连接");
                    }
                }
            }
        });

    }

    @Override
    protected void initData() {
        getData();
    }

    /**
     * 消费记录管理
     */
    private void sucklePingjia() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("p", String.valueOf(p));
        params.put("keywords", lastKeywords);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "wang/xflist", "wang/xflist", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    XflistBean xflistBean = new Gson().fromJson(result, XflistBean.class);
                    rv_purchaserecord.loadMoreComplete();
                    if ("1".equals(String.valueOf(xflistBean.getCode()))) {
                        ll_empty.setVisibility(View.GONE);
                        if (p == 1) {
                            evaluationAdapter = new XfListAdapter(xflistBean.getList(), context);
                            rv_purchaserecord.setAdapter(evaluationAdapter);
                            rv_purchaserecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    startActivity(new Intent(context, XfDetailsActivity.class).putExtra("id", evaluationAdapter.getDatas().get(position).getId()));
                                }
                            });
                        } else {
                            evaluationAdapter.setDatas(xflistBean.getList());
                        }
                        if (rv_purchaserecord != null) {
                            rv_purchaserecord.loadMoreComplete();
                        }
                        if (xflistBean.getList().size() < 10) {
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
