package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.MyServiceEvaluationAdapter;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.FuwuDetailBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.LiangDiRecycleView;
import sakura.liangdinvshen.View.SakuraLinearLayoutManager;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

public class MyServiceDetailsActivity extends BaseActivity {

    private FrameLayout rl_back;
    private SimpleDraweeView SimpleDraweeView;
    private TextView tv_servive_order_number;
    private TextView tv_service_name;
    private TextView tv_service_price;
    private TextView tv_service_time;
    private TextView tv_servive_type;
    private LiangDiRecycleView rc_myservice_jilu;
    private SakuraLinearLayoutManager line;
    private int p = 1;
    private MyServiceEvaluationAdapter serviceEvaluationAdapter;
    private String id;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_service_details;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        SimpleDraweeView = (SimpleDraweeView) findViewById(R.id.SimpleDraweeView);
        tv_servive_order_number = (TextView) findViewById(R.id.tv_servive_order_number);
        tv_service_name = (TextView) findViewById(R.id.tv_service_name);
        tv_service_price = (TextView) findViewById(R.id.tv_service_price);
        tv_service_time = (TextView) findViewById(R.id.tv_service_time);
        tv_servive_type = (TextView) findViewById(R.id.tv_servive_type);
        rc_myservice_jilu = (LiangDiRecycleView) findViewById(R.id.rc_myservice_jilu);
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        rc_myservice_jilu.setLayoutManager(line);
        rc_myservice_jilu.setItemAnimator(new DefaultItemAnimator());
        rc_myservice_jilu.setCanloadMore(false);
        TextView textView = new TextView(context);
        textView.setText("-NOTMORE-");
        rc_myservice_jilu.setFootEndView(textView);
        rc_myservice_jilu.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                getData();
            }
        });
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
        id = getIntent().getStringExtra("id");
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            getData();
        } else {
            EasyToast.showShort(context, "网络未连接");
            finish();
        }
    }

    public void getData() {

        if (Utils.isConnected(context)) {
            fuwuDetail(id);
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
    }

    /**
     * 服务内容获取
     */
    private void fuwuDetail(String id) {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        params.put("p", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "fuwu/detail", "fuwu/detail", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("MyServiceDetails", result);
                try {
                    FuwuDetailBean fuwuDetailBean = new Gson().fromJson(result, FuwuDetailBean.class);
                    if ("1".equals(String.valueOf(fuwuDetailBean.getStu()))) {
                        SimpleDraweeView.setImageURI(UrlUtils.URL + fuwuDetailBean.getGoods().getImg());
                        tv_servive_order_number.setText("服务码:" + fuwuDetailBean.getInfo().getFworder());
                        tv_service_price.setText("￥" + fuwuDetailBean.getInfo().getPrice());
                        tv_service_time.setText(DateUtils.getMillon(Long.parseLong(fuwuDetailBean.getInfo().getAddtime()) * 1000));
                        tv_service_name.setText(fuwuDetailBean.getGoods().getTitle());
                        if ("0".equals(String.valueOf(fuwuDetailBean.getInfo().getYfwnum()))) {
                            tv_servive_type.setText("未消费");
                        } else {
                            if (String.valueOf(fuwuDetailBean.getInfo().getYfwnum()).equals(String.valueOf(fuwuDetailBean.getInfo().getPjnum()))) {
                                tv_servive_type.setText("已完成");
                            } else {
                                tv_servive_type.setText("已消费" + fuwuDetailBean.getInfo().getYfwnum() + "/" + fuwuDetailBean.getInfo().getPjnum());
                            }
                        }

                        if (fuwuDetailBean.getFuwu() != null) {
                            if (p == 1) {
                                serviceEvaluationAdapter = new MyServiceEvaluationAdapter(fuwuDetailBean.getFuwu(), context, tv_service_name.getText().toString());
                                rc_myservice_jilu.setAdapter(serviceEvaluationAdapter);
                            } else {
                                serviceEvaluationAdapter.setDatas(fuwuDetailBean.getFuwu());
                            }
                            if (rc_myservice_jilu != null) {
                                rc_myservice_jilu.setEnabled(true);
                                rc_myservice_jilu.loadMoreComplete();
                            }
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
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


}
