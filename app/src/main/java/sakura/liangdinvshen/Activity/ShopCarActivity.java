package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.ShopCarListAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.SuckleCartBean;
import sakura.liangdinvshen.Bean.SuckleCartDelBean;
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

public class ShopCarActivity extends BaseActivity {

    private FrameLayout rl_back;
    private TextView tv_bianji;
    private LiangDiRecycleView rv_purchaserecord;
    private CheckBox btnIsChoosed;
    private TextView shopnow;
    private TextView tv_money;
    private SakuraLinearLayoutManager line;
    private int p = 1;
    private ShopCarListAdapter shopCarListAdapter;
    private Dialog dialog;
    private RelativeLayout rl_buy;
    private Button btn_delete;
    private RelativeLayout rl_bianji;
    private boolean isbianji = false;
    private CheckBox btnChoosed;
    private BroadcastReceiver receiver;
    private RelativeLayout ll_empty;
    private LinearLayout ll_content;

    @Override
    protected int setthislayout() {
        return R.layout.activity_shop_car;
    }

    @Override
    protected void initview() {
        ll_empty = (RelativeLayout) findViewById(R.id.LL_empty);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        rl_buy = (RelativeLayout) findViewById(R.id.rl_buy);
        rl_bianji = (RelativeLayout) findViewById(R.id.rl_bianji);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_bianji = (TextView) findViewById(R.id.tv_bianji);
        rv_purchaserecord = (LiangDiRecycleView) findViewById(R.id.rv_purchaserecord);
        btnIsChoosed = (CheckBox) findViewById(R.id.btnIsChoosed);
        btnChoosed = (CheckBox) findViewById(R.id.btnChoosed);
        shopnow = (TextView) findViewById(R.id.shopnow);
        tv_money = (TextView) findViewById(R.id.tv_money);
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        rv_purchaserecord.setLayoutManager(line);
        rv_purchaserecord.setItemAnimator(new DefaultItemAnimator());
        ProgressView progressView = new ProgressView(context);
        progressView.setIndicatorId(ProgressView.BallRotate);
        progressView.setIndicatorColor(getResources().getColor(R.color.colorAccent));
        rv_purchaserecord.setFootLoadingView(progressView);
        TextView textView = new TextView(context);
        textView.setText("-没有更多了-");
        rv_purchaserecord.setFootEndView(textView);
        rv_purchaserecord.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                getData();
            }
        });

        dialog = Utils.showLoadingDialog(context);
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shopnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ischecked = 0;
                final StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
                    if (shopCarListAdapter.getDatas().get(i).isCheck()) {
                        ischecked = ischecked + 1;
                        if (stringBuilder.length() == 0) {
                            stringBuilder.append(shopCarListAdapter.getDatas().get(i).getGid());
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getImg());
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getTitle());
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getPrice());
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getNumber());
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getId());
                        } else {
                            stringBuilder.append("&" + shopCarListAdapter.getDatas().get(i).getGid());
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getImg());
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getTitle());
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getPrice());
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getNumber());
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getId());
                        }
                    }
                }
                if (ischecked == 0) {
                    EasyToast.showShort(context, "请选择商品");
                } else {
                    Intent intent = new Intent(context, OrderActivity.class);
                    intent.putExtra("order", stringBuilder.toString());
                    intent.putExtra("price", tv_money.getText());
                    startActivity(intent);
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringBuilder stringBuilder = new StringBuilder();
                int ischecked = 0;
                for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
                    if (shopCarListAdapter.getDatas().get(i).isCheck()) {
                        ischecked = ischecked + 1;
                        if (stringBuilder.length() == 0) {
                            stringBuilder.append(shopCarListAdapter.getDatas().get(i).getGid());
                        } else {
                            stringBuilder.append("," + shopCarListAdapter.getDatas().get(i).getGid());
                        }
                    }
                }
                if (ischecked != 0) {
                    new CommomDialog(context, R.style.dialog, "您确定删除所选宝贝吗？", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                                if (Utils.isConnected(context)) {
                                    ShopCarActivity.this.dialog.show();
                                    suckleCartDel(stringBuilder.toString());
                                } else {
                                    EasyToast.showShort(context, "网络未连接");
                                }
                            }

                        }
                    }).setTitle("提示").show();
                } else {
                    EasyToast.showShort(context, "请选择要删除的宝贝");
                }
            }
        });
        tv_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
                    shopCarListAdapter.getDatas().get(i).setCheck(false);
                }
                shopCarListAdapter.notifyDataSetChanged();
                tv_money.setText("￥0.0");
                if (isbianji) {
                    rl_bianji.setVisibility(View.GONE);
                    rl_buy.setVisibility(View.VISIBLE);
                    tv_bianji.setText("编辑");
                } else {
                    rl_buy.setVisibility(View.GONE);
                    rl_bianji.setVisibility(View.VISIBLE);
                    tv_bianji.setText("完成");
                }
                isbianji = !isbianji;
            }
        });

        btnChoosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btnChoosed.isChecked()) {
                    for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
                        shopCarListAdapter.getDatas().get(i).setCheck(false);
                    }
                    shopCarListAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
                        shopCarListAdapter.getDatas().get(i).setCheck(true);
                    }
                    shopCarListAdapter.notifyDataSetChanged();
                }
            }
        });

        btnIsChoosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btnIsChoosed.isChecked()) {
                    for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
                        shopCarListAdapter.getDatas().get(i).setCheck(false);
                    }
                    shopCarListAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
                        shopCarListAdapter.getDatas().get(i).setCheck(true);
                    }
                    shopCarListAdapter.notifyDataSetChanged();
                }
            }
        });

//
//        btnIsChoosed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int ischecked = 0;
//                for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
//                    if (shopCarListAdapter.getDatas().get(i).isCheck()) {
//                        ischecked = ischecked + 1;
//                    }
//                }
//                if (ischecked == 0) {
//                    for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
//                        shopCarListAdapter.getDatas().get(i).setCheck(isChecked);
//                    }
//                    shopCarListAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//
//        btnChoosed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int ischecked = 0;
//                for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
//                    if (shopCarListAdapter.getDatas().get(i).isCheck()) {
//                        ischecked = ischecked + 1;
//                    }
//                }
//                if (ischecked == 0) {
//                    for (int i = 0; i < shopCarListAdapter.getDatas().size(); i++) {
//                        shopCarListAdapter.getDatas().get(i).setCheck(isChecked);
//                    }
//                    shopCarListAdapter.notifyDataSetChanged();
//                }
//            }
//        });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean choosed = intent.getBooleanExtra("Choosed", false);
                if (choosed) {
                    btnIsChoosed.setChecked(true);
                    btnChoosed.setChecked(true);
                } else {
                    btnIsChoosed.setChecked(false);
                    btnChoosed.setChecked(false);
                }
            }
        };

        registerReceiver(receiver, new IntentFilter("shopCarChoosedAll"));
    }

    @Override
    protected void initData() {
        getData();
    }

    public void getData() {
        suckleCart();
    }

    /**
     * 购物车获取
     */
    private void suckleCart() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("p", String.valueOf(p));
        Log.e("ShopCarActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "suckle/cart", "suckle/cart", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("ShopCarActivity", result);
                try {
                    SuckleCartBean suckleCartBean = new Gson().fromJson(result, SuckleCartBean.class);
                    if ("1".equals(String.valueOf(suckleCartBean.getStu()))) {
                        ll_content.setVisibility(View.VISIBLE);
                        ll_empty.setVisibility(View.GONE);
                        if (rv_purchaserecord != null) {
                            rv_purchaserecord.setEnabled(true);
                            rv_purchaserecord.loadMoreComplete();
                        }
                        if (p == 1) {
                            shopCarListAdapter = new ShopCarListAdapter(suckleCartBean.getRes(), context, tv_money);
                            rv_purchaserecord.setAdapter(shopCarListAdapter);
                            rv_purchaserecord.setCanloadMore(false);
                            rv_purchaserecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(context, PriceDetailsActivity.class);
                                    intent.putExtra("id", shopCarListAdapter.getDatas().get(position).getGid());
                                    //intent.putExtra("CountCart", tv_countCart.getText().toString());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            shopCarListAdapter.setDatas((ArrayList<SuckleCartBean.ResBean>) suckleCartBean.getRes());
                            if (suckleCartBean.getRes().size() < 10) {
                                rv_purchaserecord.setCanloadMore(false);
                                rv_purchaserecord.loadMoreEnd();
                            }
                        }
                    } else if ("0".equals(String.valueOf(suckleCartBean.getStu()))) {
                        ll_content.setVisibility(View.GONE);
                        ll_empty.setVisibility(View.VISIBLE);
                    }
                    suckleCartBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 清除商品
     */
    private void suckleCartDel(String id) {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("id", id);
        Log.e("RegisterActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "suckle/cart_del", "suckle/cart_del", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                ShopCarActivity.this.dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    SuckleCartDelBean suckleCartDelBean = new Gson().fromJson(result, SuckleCartDelBean.class);
                    if ("1".equals(String.valueOf(suckleCartDelBean.getStu()))) {
                        EasyToast.showShort(context, "删除成功");
                        ShopCarActivity.this.dialog.show();
                        suckleCart();
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
                ShopCarActivity.this.dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean makeref = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (makeref) {
            finish();
            makeref = !makeref;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("suckle/cart");
        unregisterReceiver(receiver);
    }
}
