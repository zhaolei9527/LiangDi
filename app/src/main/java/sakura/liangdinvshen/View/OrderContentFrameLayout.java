package sakura.liangdinvshen.View;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Activity.MyOrderDetailsActivity;
import sakura.liangdinvshen.Adapter.MyOrderAdapter;
import sakura.liangdinvshen.Bean.OrderListsBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

import static sakura.liangdinvshen.R.id.LL_empty;

/**
 * Created by 赵磊 on 2017/9/19.
 */

public class OrderContentFrameLayout extends LinearLayout {
    private final BroadcastReceiver receiver;
    private LiangDiRecycleView mRecyclerView;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private MyOrderAdapter adapter;
    private int height;
    private Context context;
    private String stu = "";
    private RelativeLayout ll_empty;
    private final Dialog dialog;

    public OrderContentFrameLayout(@NonNull Context context, final String stu) {
        super(context);
        this.context = context;
        this.stu = stu;
        dialog = Utils.showLoadingDialog(context);
        if (Utils.isConnected(context)) {
            if (TextUtils.isEmpty(stu)) {
                dialog.show();
            }
        } else {
            dialog.dismiss();
        }
        onCreateView();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String stu1 = intent.getStringExtra("stu");
                if (!TextUtils.isEmpty(stu1)) {
                    if (stu.equals(stu1)) {
                        p = 1;
                        getData();
                    }
                } else {
                    if (stu.equals(stu1)) {
                        p = 1;
                        getData();
                    }
                }
                String unRegister = intent.getStringExtra("unRegister");
                if (!TextUtils.isEmpty(unRegister)) {
                    if ("unRegister".equals(unRegister)) {
                        context.unregisterReceiver(receiver);
                    }
                }
            }
        };
        context.registerReceiver(receiver, new IntentFilter("OrderContentRefresh"));
    }

    public void onCreateView() {
        View news_content_fragment_layout = View.inflate(context, R.layout.order_content_fragment_layout, null);
        initView(news_content_fragment_layout);
        addView(news_content_fragment_layout);
        getData();
    }

    private void initView(View view) {
        mRecyclerView = (LiangDiRecycleView) view.findViewById(R.id.ce_shi_lv);
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(line);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ProgressView progressView = new ProgressView(context);
        progressView.setIndicatorId(ProgressView.BallRotate);
        progressView.setIndicatorColor(getResources().getColor(R.color.colorAccent));
        mRecyclerView.setFootLoadingView(progressView);
        TextView textView = new TextView(context);
        textView.setText("-我也是有底线的-");
        mRecyclerView.setFootEndView(textView);
        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                getData();
            }
        });
        ll_empty = (RelativeLayout) view.findViewById(LL_empty);
    }

    public void getData() {
        orderLists(stu);
    }

    /**
     * 订单列表获取
     */
    private void orderLists(final String stu) {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("p", String.valueOf(p));
        params.put("stu", stu);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "order/lists", "order/lists", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", stu);
                Log.e("RegisterActivity", result);
                dialog.dismiss();
                try {
                    final OrderListsBean orderListsBean = new Gson().fromJson(result, OrderListsBean.class);
                    if ("1111".equals(String.valueOf(orderListsBean.getStu()))) {
                        ll_empty.setVisibility(GONE);

                        if (p == 1) {
                            adapter = new MyOrderAdapter(orderListsBean.getRes(), context, OrderContentFrameLayout.this,ll_empty);
                            mRecyclerView.setAdapter(adapter);
                            mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    context.startActivity(new Intent(context, MyOrderDetailsActivity.class)
                                            .putExtra("orderid", orderListsBean.getRes().get(position).getId()));
                                }
                            });
                        } else {
                            adapter.setDatas((ArrayList<OrderListsBean.ResBean>) orderListsBean.getRes());
                            adapter.notifyDataSetChanged();
                        }

                        if (orderListsBean.getRes().size() < 10) {
                            if (mRecyclerView != null) {
                                mRecyclerView.loadMoreComplete();
                                mRecyclerView.setCanloadMore(false);
                                mRecyclerView.loadMoreEnd();
                            }
                        } else {
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setCanloadMore(true);
                        }

                    } else if ("111".equals(String.valueOf(orderListsBean.getStu()))) {
                        if (1 == p) {
                            ll_empty.setVisibility(VISIBLE);
                        } else {
                            if (mRecyclerView != null) {
                                mRecyclerView.setEnabled(true);
                                mRecyclerView.loadMoreComplete();
                                mRecyclerView.setCanloadMore(false);
                                mRecyclerView.loadMoreEnd();
                            }
                        }
                    }

                } catch (Exception e) {
                    dialog.dismiss();
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


}
