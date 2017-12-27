package sakura.liangdinvshen.Fragment;

import android.app.Dialog;
import android.content.Context;
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

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.StaffManagemntListAdapter;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.CodeBean;
import sakura.liangdinvshen.Bean.WangCenterBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.CommomDialog;
import sakura.liangdinvshen.View.LiangDiRecycleView;
import sakura.liangdinvshen.View.ProgressView;
import sakura.liangdinvshen.View.SakuraLinearLayoutManager;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * Created by 赵磊 on 2017/9/19.
 */

public class StaffManagementListFragment extends Fragment {
    private SwipeRefreshLayout refresh;
    private LiangDiRecycleView mRecyclerView;
    private int p = 1;
    private SakuraLinearLayoutManager line;
    private StaffManagemntListAdapter adapter;
    private Context context;
    private View news_content_fragment_layout;
    private RelativeLayout ll_empty;

    /**
     * 消息列表获取
     */
    private void wangYglist() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("p", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "wang/yglist", "wang/yglist", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                refresh.setEnabled(false);
                Log.e("wangCenter", result);
                String decode = result;
                try {
                    WangCenterBean wangCenterBean = new Gson().fromJson(decode, WangCenterBean.class);
                    if ("1".equals(String.valueOf(wangCenterBean.getCode()))) {
                        EventBus.getDefault().post(
                                new BankEvent("hasdata"));
                        if (mRecyclerView != null) {
                            mRecyclerView.setEnabled(true);
                            mRecyclerView.loadMoreComplete();
                            mRecyclerView.setCanloadMore(true);
                        }
                        if (refresh != null) {
                            refresh.setRefreshing(false);
                        }
                        if (p == 1) {
                            adapter = new StaffManagemntListAdapter(wangCenterBean.getList(), context, ll_empty);
                            mRecyclerView.setAdapter(adapter);
                            mRecyclerView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                                    new CommomDialog(context, R.style.dialog, "您确定删除此员工吗？", new CommomDialog.OnCloseListener() {
                                        @Override
                                        public void onClick(Dialog dialog, boolean confirm) {
                                            if (confirm) {
                                                dialog.dismiss();
                                            } else {
                                                dialog.dismiss();
                                                if (Utils.isConnected(context)) {
                                                    wangYgdel(adapter.getDatas().get(position).getId());
                                                    adapter.getDatas().remove(position);
                                                    adapter.notifyDataSetChanged();
                                                    if (adapter.getDatas().size() == 0) {
                                                        ll_empty.setVisibility(View.VISIBLE);
                                                    }
                                                } else {
                                                    EasyToast.showShort(context, "网络未连接");
                                                }
                                            }
                                        }
                                    }).setTitle("提示").show();
                                    return false;
                                }
                            });
                        } else {
                            adapter.setDatas(wangCenterBean.getList());
                        }
                        refresh.setRefreshing(false);
                        mRecyclerView.loadMoreComplete();
                    } else {
                        // TODO Auto-generated method stub
                        EventBus.getDefault().post(
                                new BankEvent("nodata"));
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
                    wangCenterBean = null;
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
        wangYglist();
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


    /**
     * 删除员工
     */
    private void wangYgdel(String id) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("uid", id);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "wang/ygdel", "wang/ygdel", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {
                        EasyToast.showShort(context, "删除成功");
                    } else {
                        EasyToast.showShort(context, "删除失败");
                    }
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
