package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;

import sakura.liangdinvshen.Adapter.AskListAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.ProblemGetProblemBean;
import sakura.liangdinvshen.Fragment.RecordFragment;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.LiangDiRecycleView;
import sakura.liangdinvshen.View.SakuraLinearLayoutManager;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

public class AskListActivity extends BaseActivity {

    private FrameLayout rl_back;
    private LiangDiRecycleView rv_purchaserecord;
    private SakuraLinearLayoutManager line;
    private int p = 1;
    private AskListAdapter evaluationAdapter;
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
        return R.layout.activity_ask;
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
        TextView textView = new TextView(context);
        textView.setText("-我也是有底线的-");
        rv_purchaserecord.setFootEndView(textView);
        rv_purchaserecord.setCanloadMore(false);
        dialog = Utils.showLoadingDialog(context);
    }

    public void getData() {
        if (!dialog.isShowing()){
            dialog.show();
        }
        if (Utils.isConnected(context)) {
            problemGetProblem();
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
     * 问题获取
     */
    private void problemGetProblem() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        Log.e("AskListActivity", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "problem/pro_list", "problem/pro_list", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    ProblemGetProblemBean problemGetProblemBean = new Gson().fromJson(result, ProblemGetProblemBean.class);
                    rv_purchaserecord.loadMoreComplete();
                    if ("1".equals(String.valueOf(problemGetProblemBean.getStu()))) {
                        ll_empty.setVisibility(View.GONE);
                        if (p == 1) {
                            evaluationAdapter = new AskListAdapter(problemGetProblemBean.getRes(), context);
                            rv_purchaserecord.setAdapter(evaluationAdapter);
                        } else {
                            evaluationAdapter.setDatas(problemGetProblemBean.getRes());
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

}
