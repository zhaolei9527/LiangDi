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
import sakura.liangdinvshen.Activity.ShopCarActivity;
import sakura.liangdinvshen.Adapter.ShopListAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Bean.CountCartBean;
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

public class ShopFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout mRlShoppingcart;
    private LiangDiRecycleView mRcShopPrice;
    private SwipeRefreshLayout mRefresh;
    private int p = 1;
    private ShopListAdapter adapter;
    private SakuraLinearLayoutManager line;
    private TextView tv_countCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_fragment_layout, container, false);
        initView(view);
        goodsListCache();
        getData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRlShoppingcart.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        countCart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("goods/goods_list");
        App.getQueues().cancelAll("cart/count_cart");
        System.gc();
    }

    private void initView(View view) {
        tv_countCart = (TextView) view.findViewById(R.id.tv_countCart);
        mRlShoppingcart = (RelativeLayout) view.findViewById(R.id.rl_shoppingcart);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_shoppingcart:
                startActivity(new Intent(getContext(), ShopCarActivity.class));
                break;
            default:

                break;
        }
    }

    /**
     * 商品列表获取
     */
    private void goodsList() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("page", String.valueOf(p));
        VolleyRequest.RequestPost(getActivity(), UrlUtils.BASE_URL + "goods/goods_list", "goods/goods_list", params, new VolleyInterface(getActivity()) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    GoodsListBean goodsListBean = new Gson().fromJson(result, GoodsListBean.class);
                    if ("302".equals(String.valueOf(goodsListBean.getCode()))) {
                        if (p == 1) {
                            GoodsListBean.ResBean resBean = new GoodsListBean.ResBean();
                            resBean.setImg(goodsListBean.getTopimg().getImg());
                            goodsListBean.getRes().add(0, resBean);
                            SpUtil.putAndApply(getActivity(), "goods_list", result);
                            adapter = new ShopListAdapter(goodsListBean.getRes(), getActivity());
                            mRcShopPrice.setAdapter(adapter);
                            mRcShopPrice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != 0) {
                                        Intent intent = new Intent(getContext(), PriceDetailsActivity.class);
                                        intent.putExtra("id", adapter.getDatas().get(position).getId());
                                        intent.putExtra("CountCart", tv_countCart.getText().toString());
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            adapter.setDatas((ArrayList<GoodsListBean.ResBean>) goodsListBean.getRes());
                            adapter.notifyDataSetChanged();
                        }
                        if (mRcShopPrice != null) {
                            mRcShopPrice.setEnabled(true);
                            mRcShopPrice.loadMoreComplete();
                        }
                        if (mRefresh != null) {
                            mRefresh.setRefreshing(false);
                        }
                        if (goodsListBean.getRes().size() < 10) {
                            mRcShopPrice.setCanloadMore(false);
                            mRcShopPrice.loadMoreEnd();
                        } else {
                            mRcShopPrice.setCanloadMore(true);
                        }
                    } else {
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
                    }
                    result = null;
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
        String goods_list = (String) SpUtil.get(getActivity(), "goods_list", "");
        if (!TextUtils.isEmpty(goods_list)) {
            try {
                GoodsListBean goodsListBean = new Gson().fromJson(goods_list, GoodsListBean.class);
                if ("302".equals(String.valueOf(goodsListBean.getCode()))) {
                    if (p == 1) {
                        GoodsListBean.ResBean resBean = new GoodsListBean.ResBean();
                        resBean.setImg(goodsListBean.getTopimg().getImg());
                        goodsListBean.getRes().add(0, resBean);
                        adapter = new ShopListAdapter(goodsListBean.getRes(), getActivity());
                        mRcShopPrice.setAdapter(adapter);
                    } else {

                    }
                    if (mRcShopPrice != null) {
                        mRcShopPrice.setEnabled(true);
                        mRcShopPrice.loadMoreComplete();
                    }
                    if (goodsListBean.getRes().size() < 10) {
                        mRcShopPrice.setCanloadMore(false);
                        mRcShopPrice.loadMoreEnd();
                    } else {
                        mRcShopPrice.setCanloadMore(true);
                    }
                } else {

                }
                goods_list = null;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 购物车数量获取
     */
    private void countCart() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(getActivity(), "uid", "")));
        VolleyRequest.RequestPost(getActivity(), UrlUtils.BASE_URL + "cart/count_cart", "cart/count_cart", params, new VolleyInterface(getActivity()) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    CountCartBean countCartBean = new Gson().fromJson(result, CountCartBean.class);
                    if ("1".equals(String.valueOf(countCartBean.getStatus()))) {
                        tv_countCart.setText(countCartBean.getRes());
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                    }
                    result = null;
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

}
