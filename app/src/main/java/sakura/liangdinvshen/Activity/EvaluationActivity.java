package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.EvaluationAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.SucklePingjiaBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.LiangDiRecycleView;
import sakura.liangdinvshen.View.ProgressView;
import sakura.liangdinvshen.View.SakuraLinearLayoutManager;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

public class EvaluationActivity extends BaseActivity {

    private FrameLayout rl_back;
    private LiangDiRecycleView rv_purchaserecord;
    private SakuraLinearLayoutManager line;
    private int p = 1;
    private EvaluationAdapter evaluationAdapter;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_all_pingjia;
    }

    @Override
    protected void initview() {
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
                getData();
            }
        });
    }

    public void getData() {
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
        if (Utils.isConnected(context)) {
            sucklePingjia();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
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
        getData();
    }

    /**
     * 评价记录获取
     */
    private void sucklePingjia() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("id", getIntent().getStringExtra("id"));
        params.put("p", String.valueOf(p));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "suckle/pingjia", "suckle/pingjia", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    SucklePingjiaBean sucklePingjiaBean = new Gson().fromJson(result, SucklePingjiaBean.class);
                    if (p == 1) {
                        evaluationAdapter = new EvaluationAdapter(sucklePingjiaBean.getRes(), context);
                        rv_purchaserecord.setAdapter(evaluationAdapter);
                    } else {
                        evaluationAdapter.setDatas((ArrayList<SucklePingjiaBean.ResBean>) sucklePingjiaBean.getRes());
                    }
                    if (rv_purchaserecord != null) {
                        rv_purchaserecord.loadMoreComplete();
                    }
                    if (sucklePingjiaBean.getRes().size() < 10) {
                        rv_purchaserecord.setCanloadMore(false);
                        rv_purchaserecord.loadMoreEnd();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("suckle/pingjia");
    }
}
