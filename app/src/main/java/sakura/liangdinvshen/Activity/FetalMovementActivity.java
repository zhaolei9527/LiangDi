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
import sakura.liangdinvshen.Adapter.FetaMovemontAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.SuckleSuckleBean;
import sakura.liangdinvshen.Fragment.RecordFragment;
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

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/28
 * 功能描述：
 */
public class FetalMovementActivity extends BaseActivity {
    private SakuraLinearLayoutManager line;
    private int p = 1;
    private FrameLayout rl_back;
    private LiangDiRecycleView rv_purchaserecord;
    private Dialog dialog;
    private RelativeLayout ll_empty;
    private FetaMovemontAdapter evaluationAdapter;
    private TextView tv_add;
    private TextView tv_baby_time;


    @Override
    protected int setthislayout() {
        return R.layout.activity_fetamovement;
    }

    @Override
    protected void initview() {
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_baby_time = (TextView) findViewById(R.id.tv_baby_time);
        ll_empty = (RelativeLayout) findViewById(R.id.LL_empty);
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        rv_purchaserecord = (LiangDiRecycleView) findViewById(R.id.rv_purchaserecord);
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

            }
        });
        rv_purchaserecord.setCanloadMore(false);
        dialog = Utils.showLoadingDialog(context);
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddFetaMovementActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.isConnected(context)) {
            dialog.show();
            suckleSuckle();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
    }

    @Override
    protected void initData() {
    }

    /**
     * 哺乳记录
     */
    private void suckleSuckle() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "suckle/fetus", "suckle/fetus", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    SuckleSuckleBean suckleSuckleBean = new Gson().fromJson(result, SuckleSuckleBean.class);
                    tv_baby_time.setText(suckleSuckleBean.getTime() + " 宝宝" + suckleSuckleBean.getMonth() + "个月" + suckleSuckleBean.getDay() + "天");
                    rv_purchaserecord.loadMoreComplete();
                    if ("1".equals(String.valueOf(suckleSuckleBean.getStu()))) {
                        ll_empty.setVisibility(View.GONE);
                        if (p == 1) {
                            evaluationAdapter = new FetaMovemontAdapter(suckleSuckleBean.getRes(), context);
                            rv_purchaserecord.setAdapter(evaluationAdapter);
                        } else {
                            evaluationAdapter.setDatas(suckleSuckleBean.getRes());
                        }
                        if (rv_purchaserecord != null) {
                            rv_purchaserecord.loadMoreComplete();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("suckle/fetus");
    }

}
