package sakura.liangdinvshen.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sakura.liangdinvshen.Activity.ReturnPriceDetailsActivity;
import sakura.liangdinvshen.Bean.CodeBean;
import sakura.liangdinvshen.Bean.OrderThindexBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.View.CommomDialog;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class ReturnPriceAdapter extends RecyclerView.Adapter<ReturnPriceAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<OrderThindexBean.ResBean> datas = new ArrayList();

    public ArrayList<OrderThindexBean.ResBean> getDatas() {
        return datas;
    }

    public ReturnPriceAdapter(List<OrderThindexBean.ResBean> list, Context context) {
        this.datas = (ArrayList<OrderThindexBean.ResBean>) list;
        this.mContext = context;
    }

    public void setDatas(List<OrderThindexBean.ResBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_return_price_layout, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_change_price_title.setText(datas.get(position).getTitle());
        holder.tv_change_price_classify.setText("￥" + datas.get(position).getPrice());
        holder.tv_change_price_time.setText(DateUtils.getMillon(Long.parseLong(datas.get(position).getAddtime()) * 1000));
        holder.SimpleDraweeView.setImageURI(UrlUtils.URL + datas.get(position).getGimg());
        holder.tv_number.setText("×" + datas.get(position).getNumber());
        if ("-1".equals(String.valueOf(datas.get(position).getStatus()))) {
            holder.tv_change_price_type.setText("退换状态：取消申请");
            holder.btn_delete_change_price.setVisibility(View.GONE);
        } else if ("1".equals(String.valueOf(datas.get(position).getStatus()))) {
            holder.tv_change_price_type.setText("退换状态：申请中");
            holder.btn_delete_change_price.setVisibility(View.VISIBLE);
        } else if ("2".equals(String.valueOf(datas.get(position).getStatus()))) {
            holder.tv_change_price_type.setText("退换状态：通过");
            holder.btn_delete_change_price.setVisibility(View.GONE);
        } else if ("3".equals(String.valueOf(datas.get(position).getStatus()))) {
            holder.tv_change_price_type.setText("退换状态：未通过");
            holder.btn_delete_change_price.setVisibility(View.GONE);
        }

        holder.btn_return_price_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ReturnPriceDetailsActivity.class).putExtra("id", datas.get(position).getId()));
            }
        });

        holder.btn_delete_change_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommomDialog(mContext, R.style.dialog, "确定取消订单吗？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            orderThcancel(datas.get(position).getId());
                            datas.get(position).setStatus("-1");
                            notifyDataSetChanged();
                        }
                    }
                }).setTitle("提示").show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public SimpleDraweeView SimpleDraweeView;
        public TextView tv_change_price_time;
        public TextView tv_change_price_type;
        public TextView tv_change_price_title;
        public TextView tv_change_price_classify;
        public TextView tv_number;
        public Button btn_delete_change_price;
        public Button btn_return_price_content;


        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.SimpleDraweeView = (SimpleDraweeView) rootView.findViewById(R.id.SimpleDraweeView);
            this.tv_change_price_time = (TextView) rootView.findViewById(R.id.tv_change_price_time);
            this.tv_change_price_type = (TextView) rootView.findViewById(R.id.tv_change_price_type);
            this.tv_change_price_title = (TextView) rootView.findViewById(R.id.tv_change_price_title);
            this.tv_change_price_classify = (TextView) rootView.findViewById(R.id.tv_change_price_classify);
            this.tv_number = (TextView) rootView.findViewById(R.id.tv_number);
            this.btn_delete_change_price = (Button) rootView.findViewById(R.id.btn_delete_change_price);
            this.btn_return_price_content = (Button) rootView.findViewById(R.id.btn_return_price_content);

        }
    }


    /**
     * 取消退换货
     */
    private void orderThcancel(String id) {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(mContext, "uid", "")));
        params.put("id", id);
        VolleyRequest.RequestPost(mContext, UrlUtils.BASE_URL + "order/thcancel", "order/thcancel", params, new VolleyInterface(mContext) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {
                    } else {
                        EasyToast.showShort(mContext, codeBean.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, mContext.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(mContext, mContext.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
