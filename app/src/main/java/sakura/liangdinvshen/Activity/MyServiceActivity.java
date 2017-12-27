package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.MyServiceAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.FuwuIndexBean;
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

public class MyServiceActivity extends BaseActivity {

    private FrameLayout rl_back;
    private LiangDiRecycleView rc_my_service;
    private SakuraLinearLayoutManager line;
    private int p = 1;
    private MyServiceAdapter myServiceAdapter;
    private Dialog dialog;
    private RelativeLayout ll_empty;

    @Override
    protected int setthislayout() {
        return R.layout.activity_my_service;
    }

    @Override
    protected void initview() {
        ll_empty = (RelativeLayout) findViewById(R.id.LL_empty);
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        rc_my_service = (LiangDiRecycleView) findViewById(R.id.rc_my_service);
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        rc_my_service.setLayoutManager(line);
        rc_my_service.setItemAnimator(new DefaultItemAnimator());
        TextView textView = new TextView(context);
        textView.setText("-NOTMORE-");
        rc_my_service.setFootEndView(textView);
        rc_my_service.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                getData();
            }
        });
        ProgressView progressView = new ProgressView(context);
        progressView.setIndicatorId(ProgressView.BallRotate);
        progressView.setIndicatorColor(getResources().getColor(R.color.colorAccent));
        rc_my_service.setFootLoadingView(progressView);
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
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            getData();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
    }

    public void getData() {
        if (Utils.isConnected(context)) {
            fuwuIndex();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
    }

    /**
     * 免责声明获取
     */
    private void fuwuIndex() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("p", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "fuwu/index", "fuwu/index", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    final FuwuIndexBean fuwuIndexBean = new Gson().fromJson(result, FuwuIndexBean.class);
                    if ("1".equals(String.valueOf(fuwuIndexBean.getCode()))) {
                        if (p == 1) {
                            myServiceAdapter = new MyServiceAdapter(fuwuIndexBean.getList(), context);
                            rc_my_service.setAdapter(myServiceAdapter);
                            rc_my_service.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    startActivity(new Intent(context, MyServiceDetailsActivity.class).putExtra("id", fuwuIndexBean.getList().get(position).getId()));
                                }
                            });
                        } else {
                            myServiceAdapter.setDatas(fuwuIndexBean.getList());
                        }
                        rc_my_service.setEnabled(true);
                        rc_my_service.loadMoreComplete();
                    } else {
                        if (p == 1) {
                            rc_my_service.setEnabled(true);
                            rc_my_service.loadMoreComplete();
                            rc_my_service.setCanloadMore(false);
                            EasyToast.showShort(context, "暂无内容");
                            ll_empty.setVisibility(View.VISIBLE);
                        } else {
                            rc_my_service.setEnabled(true);
                            rc_my_service.loadMoreComplete();
                            rc_my_service.setCanloadMore(false);
                            rc_my_service.loadMoreEnd();
                            EasyToast.showShort(context, "没有更多了");
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("fuwu/index");
    }
}
