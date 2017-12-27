package sakura.liangdinvshen.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.NewsListAdapter;
import sakura.liangdinvshen.Bean.NewsListBean;
import sakura.liangdinvshen.R;
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

public class NewsListFragment extends BaseLazyFragment {
    private SwipeRefreshLayout refresh;
    private LiangDiRecycleView mRecyclerView;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private NewsListAdapter adapter;
    private int height;
    private Context context;
    private View news_content_fragment_layout;

    /**
     * 缓存获取
     */
    private void getNewsListCache() {
        String NewsCache = (String) SpUtil.get(context, "index" + String.valueOf(news_content_fragment_layout.getTag()), "");
        if (!TextUtils.isEmpty(NewsCache)) {
            NewsListBean newsListBean = new Gson().fromJson(NewsCache, NewsListBean.class);
            if ("1".equals(String.valueOf(newsListBean.getCode()))) {
                SpUtil.putAndApply(context, "index" + String.valueOf(news_content_fragment_layout.getTag()), NewsCache);
                if (mRecyclerView != null) {
                    mRecyclerView.setEnabled(true);
                    mRecyclerView.loadMoreComplete();
                }
                if (refresh != null) {
                    refresh.setRefreshing(false);
                }
                if (p == 1) {
                    adapter = new NewsListAdapter(newsListBean.getRes(), context);
                    mRecyclerView.setAdapter(adapter);
                    if (newsListBean.getRes().size() < 10) {
                        refresh.setRefreshing(false);
                        mRecyclerView.setCanloadMore(false);
                        mRecyclerView.loadMoreEnd();
                    }
                } else {
                    adapter.setDatas((ArrayList) newsListBean.getRes());
                }
            } else {
                Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                if (p != 1) {
                    p = p - 1;
                }
                refresh.setRefreshing(false);
                mRecyclerView.setCanloadMore(false);
                mRecyclerView.loadMoreEnd();
            }
            newsListBean = null;
            NewsCache = null;
        }

    }

    /**
     * 新闻列表获取
     */
    private void getNewsList() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("p", String.valueOf(p));
        params.put("cid", String.valueOf(news_content_fragment_layout.getTag()));
        Log.e("NewsListFragment", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "news/index", "news/index" + getTag(), params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = result;
                try {
                    NewsListBean newsListBean = new Gson().fromJson(decode, NewsListBean.class);
                    if ("1".equals(String.valueOf(newsListBean.getCode()))) {
                        SpUtil.putAndApply(context, "index" + String.valueOf(news_content_fragment_layout.getTag()), decode);
                        if (mRecyclerView != null) {
                            mRecyclerView.setEnabled(true);
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setCanloadMore(true);
                        }
                        if (refresh != null) {
                            refresh.setRefreshing(false);
                        }
                        if (p == 1) {
                            adapter = new NewsListAdapter(newsListBean.getRes(), context);
                            mRecyclerView.setAdapter(adapter);
                            if (newsListBean.getRes().size() < 10) {
                                refresh.setRefreshing(false);
                                mRecyclerView.setCanloadMore(false);
                                mRecyclerView.loadMoreEnd();
                            } else {
                                mRecyclerView.setCanloadMore(true);
                            }
                        } else {
                            adapter.setDatas((ArrayList) newsListBean.getRes());
                        }
                    } else {
                        if (p != 1) {
                            p = p - 1;
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                        }
                        refresh.setRefreshing(false);
                        mRecyclerView.setCanloadMore(false);
                        mRecyclerView.loadMoreEnd();
                    }
                    newsListBean = null;
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

    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        news_content_fragment_layout = View.inflate(getActivity(), R.layout.news_content_fragment_layout, null);
        String pageId = getArguments().getString("pageId");
        news_content_fragment_layout.setTag(pageId);
        NewsFragment.ll_head.post(new Runnable() {
            @Override
            public void run() {
                NewsFragment.ll_head.measure(0, 0);
                height = NewsFragment.ll_head.getHeight();
            }
        });
        refresh = (SwipeRefreshLayout) news_content_fragment_layout.findViewById(R.id.refresh);
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

        return news_content_fragment_layout;
    }

}
