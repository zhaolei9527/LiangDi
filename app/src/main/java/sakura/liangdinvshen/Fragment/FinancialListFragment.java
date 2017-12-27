package sakura.liangdinvshen.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.FinanciaListAdapter;
import sakura.liangdinvshen.Bean.MingxiCaiwuBean;
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
 * Created by 赵磊 on 2017/9/19.
 */

public class FinancialListFragment extends Fragment {
    private LiangDiRecycleView mRecyclerView;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private FinanciaListAdapter adapter;
    private Context context;
    private View news_content_fragment_layout;
    private RelativeLayout ll_empty;
    private String start = "";
    private String end = "";
    private Dialog dialog;

    /**
     * 财务列表获取
     */
    private void mingxiCaiwu() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("p", String.valueOf(p));
        params.put("start", start);
        params.put("end", end);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "mingxi/caiwu", "mingxi/caiwu", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("mingxiCaiwu", result);
                String decode = result;
                try {
                    MingxiCaiwuBean mingxiCaiwuBean = new Gson().fromJson(decode, MingxiCaiwuBean.class);
                    new Gson().fromJson(decode, MingxiCaiwuBean.class);
                    if ("1".equals(String.valueOf(mingxiCaiwuBean.getCode()))) {
                        if (mRecyclerView != null) {
                            mRecyclerView.setEnabled(true);
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setCanloadMore(true);
                        }
                        if (p == 1) {
                            adapter = new FinanciaListAdapter(mingxiCaiwuBean.getList(), context);
                            mRecyclerView.setAdapter(adapter);
                        } else {
                            adapter.setDatas((ArrayList) mingxiCaiwuBean.getList());
                        }
                        if (mingxiCaiwuBean.getList().size() < 10) {
                            mRecyclerView.setCanloadMore(false);
                            mRecyclerView.loadMoreEnd();
                        } else {
                            mRecyclerView.setCanloadMore(true);
                        }
                    } else {
                        if (p != 1) {
                            p = p - 1;
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                        } else {
                            ll_empty.setVisibility(View.VISIBLE);
                        }
                        mRecyclerView.setCanloadMore(false);
                        mRecyclerView.loadMoreEnd();
                    }
                    mingxiCaiwuBean = null;
                    decode = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            mingxiCaiwu();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        news_content_fragment_layout = View.inflate(context, R.layout.financial_fragment_layout, null);
        ll_empty = (RelativeLayout) news_content_fragment_layout.findViewById(R.id.LL_empty);
        mRecyclerView = (LiangDiRecycleView) news_content_fragment_layout.findViewById(R.id.ce_shi_lv);
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(line);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ProgressView progressView = new ProgressView(context);
        progressView.setIndicatorId(ProgressView.BallRotate);
        progressView.setIndicatorColor(getResources().getColor(R.color.colorAccent));
        mRecyclerView.setFootLoadingView(progressView);
        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                getData();
            }
        });

        TextView textView = new TextView(context);
        textView.setText("-我也是有底线的-");
        mRecyclerView.setFootEndView(textView);
        start = getArguments().getString("start");
        end = getArguments().getString("end");
        getData();
        return news_content_fragment_layout;
    }

}
