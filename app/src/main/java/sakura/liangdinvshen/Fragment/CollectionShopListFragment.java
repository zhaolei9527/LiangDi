package sakura.liangdinvshen.Fragment;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Activity.PriceDetailsActivity;
import sakura.liangdinvshen.Adapter.CollectionShopListAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Bean.CollectionGoodsBean;
import sakura.liangdinvshen.Bean.GoodsListBean;
import sakura.liangdinvshen.R;
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

public class CollectionShopListFragment extends Fragment {
    private LiangDiRecycleView mRcShopPrice;
    private SwipeRefreshLayout mRefresh;
    private int p = 1;
    private CollectionShopListAdapter adapter;
    private SakuraLinearLayoutManager line;
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
        View view = inflater.inflate(R.layout.shop_collection_layout, container, false);
        initView(view);
        goodsListCache();
        getData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("collection/goods");
        System.gc();
    }

    private void initView(View view) {
        ll_empty = (RelativeLayout) view.findViewById(R.id.LL_empty);
        mRcShopPrice = (LiangDiRecycleView) view.findViewById(R.id.rc_shopPrice);
        line = new SakuraLinearLayoutManager(getActivity());
        line.setOrientation(LinearLayoutManager.VERTICAL);
        mRcShopPrice.setLayoutManager(line);
        mRcShopPrice.setItemAnimator(new DefaultItemAnimator());
        ProgressView progressView = new ProgressView(getActivity());
        progressView.setIndicatorId(ProgressView.BallRotate);
        progressView.setIndicatorColor(getResources().getColor(R.color.colorAccent));
        mRcShopPrice.setFootLoadingView(progressView);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mRefresh.setProgressViewEndTarget(false, (int) getResources().getDimension(R.dimen.x105));
        mRefresh.setColorSchemeResources(R.color.colorAccent);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        p = 1;
                        goodsList();
                    }
                }, 0);
            }
        });
        TextView textView = new TextView(getActivity());
        textView.setText("-我也是有底线的-");
        mRcShopPrice.setFootEndView(textView);
        mRcShopPrice.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                goodsList();
            }
        });
    }

    public void getData() {
        mRefresh.setRefreshing(true);
        goodsList();
        if (!Utils.isConnected(getActivity())) {
            mRefresh.setRefreshing(false);
        }

    }

    /**
     * 商品列表获取
     */
    private void goodsList() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("p", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(getActivity(), "uid", "")));
        VolleyRequest.RequestPost(getActivity(), UrlUtils.BASE_URL + "collection/goods", "collection/goods", params, new VolleyInterface(getActivity()) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    SpUtil.putAndApply(getActivity(), "collection/goods", result);
                    CollectionGoodsBean collectionGoodsBean = new Gson().fromJson(result, CollectionGoodsBean.class);
                    if ("1".equals(String.valueOf(collectionGoodsBean.getStu()))) {
                        if (p == 1) {
                            ll_empty.setVisibility(View.GONE);
                            mRcShopPrice.setVisibility(View.VISIBLE);
                            GoodsListBean.ResBean resBean = new GoodsListBean.ResBean();
                            adapter = new CollectionShopListAdapter(collectionGoodsBean.getRes(), getActivity());
                            mRcShopPrice.setAdapter(adapter);
                            mRcShopPrice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getContext(), PriceDetailsActivity.class);
                                    intent.putExtra("id", adapter.getDatas().get(position).getId());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            adapter.setDatas((ArrayList<CollectionGoodsBean.ResBean>) collectionGoodsBean.getRes());
                        }
                        if (mRcShopPrice != null) {
                            mRcShopPrice.setEnabled(true);
                            mRcShopPrice.loadMoreComplete();
                        }
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                        if (collectionGoodsBean.getRes().size() < 10) {
                            mRcShopPrice.setCanloadMore(false);
                            mRcShopPrice.loadMoreEnd();
                        } else {
                            mRcShopPrice.setCanloadMore(true);
                        }
                    } else {
                        if (mRcShopPrice != null) {
                            mRcShopPrice.setEnabled(true);
                            mRcShopPrice.loadMoreComplete();
                            mRcShopPrice.setCanloadMore(false);
                            mRcShopPrice.loadMoreEnd();
                        }
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                        if (p != 1) {
                            p = p - 1;
                        } else {
                            if ("101".equals(String.valueOf(collectionGoodsBean.getCode()))) {
                                ll_empty.setVisibility(View.VISIBLE);
                                mRcShopPrice.setVisibility(View.GONE);
                            }
                        }
                    }
                    result = null;
                    collectionGoodsBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 商品列表缓存
     */
    private void goodsListCache() {
        String goods_list = (String) SpUtil.get(getActivity(), "collection/goods", "");
        if (!TextUtils.isEmpty(goods_list)) {
            try {
                CollectionGoodsBean collectionGoodsBean = new Gson().fromJson(goods_list, CollectionGoodsBean.class);
                if ("1".equals(String.valueOf(collectionGoodsBean.getStu()))) {
                    if (p == 1) {
                        GoodsListBean.ResBean resBean = new GoodsListBean.ResBean();
                        adapter = new CollectionShopListAdapter(collectionGoodsBean.getRes(), getActivity());
                        mRcShopPrice.setAdapter(adapter);
                    } else {

                    }
                    if (mRcShopPrice != null) {
                        mRcShopPrice.setEnabled(true);
                        mRcShopPrice.loadMoreComplete();
                    }
                    if (collectionGoodsBean.getRes().size() < 10) {
                        mRcShopPrice.setCanloadMore(false);
                        mRcShopPrice.loadMoreEnd();
                    } else {
                        mRcShopPrice.setCanloadMore(true);
                    }
                } else {
                    if (p != 1) {
                        p = p - 1;
                        if (mRcShopPrice != null) {
                            mRcShopPrice.setEnabled(true);
                            mRcShopPrice.loadMoreComplete();
                            mRcShopPrice.setCanloadMore(false);
                            mRcShopPrice.loadMoreEnd();
                        }
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                    } else {
                        if ("101".equals(String.valueOf(collectionGoodsBean.getCode()))) {
                            ll_empty.setVisibility(View.VISIBLE);
                        }
                    }
                }
                collectionGoodsBean = null;
                goods_list = null;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        }

    }


}