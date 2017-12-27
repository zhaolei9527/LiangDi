package sakura.liangdinvshen.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
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
import sakura.liangdinvshen.Adapter.CollectionNewsListAdapter;
import sakura.liangdinvshen.Bean.CollectionNewsBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.View.LiangDiRecycleView;
import sakura.liangdinvshen.View.ProgressView;
import sakura.liangdinvshen.View.SakuraLinearLayoutManager;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * Created by 赵磊 on 2017/9/19.
 */

public class CollectionNewsListFragment extends Fragment {
    private SwipeRefreshLayout refresh;
    private LiangDiRecycleView mRecyclerView;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private CollectionNewsListAdapter adapter;
    private Context context;
    private View news_content_fragment_layout;
    private RelativeLayout ll_empty;

    @Override
    public void onResume() {
        super.onResume();
        p = 1;
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        news_content_fragment_layout = View.inflate(getActivity(), R.layout.news_content_fragment_layout, null);
        refresh = (SwipeRefreshLayout) news_content_fragment_layout.findViewById(R.id.refresh);
        mRecyclerView = (LiangDiRecycleView) news_content_fragment_layout.findViewById(R.id.ce_shi_lv);
        ll_empty = (RelativeLayout) news_content_fragment_layout.findViewById(R.id.LL_empty);
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
        refresh.setProgressViewEndTarget(false, (int) getResources().getDimension(R.dimen.x105));
        refresh.setColorSchemeResources(R.color.colorAccent);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setEnabled(false);
                        p = 1;
                        getData();
                    }
                }, 0);
            }
        });
        TextView textView = new TextView(context);
        textView.setText("-我也是有底线的-");
        mRecyclerView.setFootEndView(textView);
        getNewsListCache();
        refresh.setRefreshing(true);
        getData();
        return news_content_fragment_layout;
    }

    /**
     * 缓存获取
     */
    private void getNewsListCache() {
        String NewsCache = (String) SpUtil.get(context, "CollectionNews" + String.valueOf(news_content_fragment_layout.getTag()), "");
        if (!TextUtils.isEmpty(NewsCache)) {
            CollectionNewsBean collectionNewsBean = new Gson().fromJson(NewsCache, CollectionNewsBean.class);
            if ("1".equals(String.valueOf(collectionNewsBean.getStu()))) {
                if (mRecyclerView != null) {
                    mRecyclerView.setEnabled(true);
                    mRecyclerView.loadMoreComplete();
                }
                if (refresh != null) {
                    refresh.setRefreshing(false);
                }
                if (p == 1) {
                    adapter = new CollectionNewsListAdapter(collectionNewsBean.getRes(), context);
                    mRecyclerView.setAdapter(adapter);
                    if (collectionNewsBean.getRes().size() < 10) {
                        refresh.setRefreshing(false);
                        mRecyclerView.setCanloadMore(false);
                        mRecyclerView.loadMoreEnd();
                    }
                } else {
                    adapter.setDatas((ArrayList) collectionNewsBean.getRes());
                }
            } else {
                if (p != 1) {
                    p = p - 1;
                    Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                } else {
                    ll_empty.setVisibility(View.VISIBLE);
                }
                refresh.setRefreshing(false);
                mRecyclerView.setCanloadMore(false);
                mRecyclerView.loadMoreEnd();
            }
            collectionNewsBean = null;
            NewsCache = null;
        }

    }

    /**
     * 收藏新闻获取
     */
    private void getNewsList() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("p", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("NewsListFragment", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "collection/news", "collection/news" + getTag(), params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                Log.e("NewsListFragment", decode);
                try {
                    CollectionNewsBean collectionNewsBean = new Gson().fromJson(decode, CollectionNewsBean.class);
                    SpUtil.putAndApply(context, "CollectionNews" + String.valueOf(news_content_fragment_layout.getTag()), decode);
                    if ("1".equals(String.valueOf(collectionNewsBean.getStu()))) {

                        if (mRecyclerView != null) {
                            mRecyclerView.setEnabled(true);
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setCanloadMore(true);
                        }
                        if (refresh != null) {
                            refresh.setRefreshing(false);
                        }
                        if (p == 1) {
                            ll_empty.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            adapter = new CollectionNewsListAdapter(collectionNewsBean.getRes(), context);
                            mRecyclerView.setAdapter(adapter);
                            if (collectionNewsBean.getRes().size() < 10) {
                                mRecyclerView.setCanloadMore(false);
                                mRecyclerView.loadMoreEnd();
                            } else {
                                mRecyclerView.setCanloadMore(true);
                            }
                        } else {
                            adapter.setDatas((ArrayList) collectionNewsBean.getRes());
                        }
                    } else {
                        if (p != 1) {
                            p = p - 1;
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                        } else {
                            EasyToast.showShort(context, "暂无内容");
                            ll_empty.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        }
                        refresh.setRefreshing(false);
                        mRecyclerView.setCanloadMore(false);
                        mRecyclerView.loadMoreEnd();
                    }
                    collectionNewsBean = null;
                    decode = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    refresh.setRefreshing(false);
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {
        getNewsList();
    }


}
