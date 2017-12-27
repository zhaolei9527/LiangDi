package sakura.liangdinvshen.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Activity.MessageDetailsActivity;
import sakura.liangdinvshen.Adapter.MessageListAdapter;
import sakura.liangdinvshen.Bean.MynewsNewsListBean;
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

public class MessageListFragment extends Fragment {
    private SwipeRefreshLayout refresh;
    private LiangDiRecycleView mRecyclerView;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private MessageListAdapter adapter;
    private int height;
    private Context context;
    private View news_content_fragment_layout;
    private RelativeLayout ll_empty;

    /**
     * 消息列表获取
     */
    private void mynewsNews_list() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("p", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "mynews/news_list", "mynews/news_list" + getTag(), params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("MessageListFragment", result);
                String decode = result;
                try {
                    MynewsNewsListBean mynewsNewsListBean = new Gson().fromJson(decode, MynewsNewsListBean.class);
                    if ("1".equals(String.valueOf(mynewsNewsListBean.getStu()))) {
                        if (mRecyclerView != null) {
                            mRecyclerView.setEnabled(true);
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setCanloadMore(true);
                        }
                        if (refresh != null) {
                            refresh.setRefreshing(false);
                        }
                        if (p == 1) {
                            adapter = new MessageListAdapter(mynewsNewsListBean.getRes(), context);
                            mRecyclerView.setAdapter(adapter);
                            mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String id1 = adapter.getDatas().get(position).getId();
                                    adapter.getDatas().get(position).setStatus("0");
                                    adapter.notifyDataSetChanged();
                                    startActivity(new Intent(context, MessageDetailsActivity.class).putExtra("id", id1));
                                }
                            });
                        } else {
                            adapter.setDatas((ArrayList) mynewsNewsListBean.getRes());
                        }
                        if (mynewsNewsListBean.getRes().size() < 10) {
                            refresh.setRefreshing(false);
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
                        refresh.setRefreshing(false);
                        mRecyclerView.setCanloadMore(false);
                        mRecyclerView.loadMoreEnd();
                    }
                    mynewsNewsListBean = null;
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
        mynewsNews_list();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        news_content_fragment_layout = View.inflate(context, R.layout.news_content_fragment_layout, null);
        ll_empty = (RelativeLayout) news_content_fragment_layout.findViewById(R.id.LL_empty);
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
        refresh.setRefreshing(true);
        getData();
        return news_content_fragment_layout;
    }

}
