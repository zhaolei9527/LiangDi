package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Bean.FuwuIndexBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.UrlUtils;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class MyServiceAdapter extends RecyclerView.Adapter<MyServiceAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<FuwuIndexBean.ListBean> datas = new ArrayList();

    public ArrayList<FuwuIndexBean.ListBean> getDatas() {
        return datas;
    }

    public MyServiceAdapter(List<FuwuIndexBean.ListBean> list, Context context) {
        this.datas = (ArrayList<FuwuIndexBean.ListBean>) list;
        this.mContext = context;
    }

    public void setDatas(List<FuwuIndexBean.ListBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_service, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.SimpleDraweeView.setImageURI(UrlUtils.URL + datas.get(position).getImg());




        holder.tv_service_name.setText(datas.get(position).getTitle());
        holder.tv_service_price.setText("￥" + datas.get(position).getPrice());
        holder.tv_servive_order_number.setText("服务码：" + datas.get(position).getFworder());
        holder.tv_service_time.setText(DateUtils.getMillon(Long.parseLong(datas.get(position).getAddtime()) * 1000));
        if ("0".equals(String.valueOf(datas.get(position).getYfwnum()))) {
            holder.tv_servive_type.setText("未消费");
        } else {
            if (String.valueOf(datas.get(position).getYfwnum()).equals(String.valueOf(datas.get(position).getPjnum()))) {
                holder.tv_servive_type.setText("已完成");
            } else {
                holder.tv_servive_type.setText("已消费" + datas.get(position).getYfwnum() + "/" + datas.get(position).getPjnum());
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public SimpleDraweeView SimpleDraweeView;
        public TextView tv_servive_order_number;
        public TextView tv_service_name;
        public TextView tv_servive_type;
        public TextView tv_service_price;
        public TextView tv_service_time;


        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.SimpleDraweeView = (SimpleDraweeView) rootView.findViewById(R.id.SimpleDraweeView);
            this.tv_servive_order_number = (TextView) rootView.findViewById(R.id.tv_servive_order_number);
            this.tv_service_name = (TextView) rootView.findViewById(R.id.tv_service_name);
            this.tv_servive_type = (TextView) rootView.findViewById(R.id.tv_servive_type);
            this.tv_service_price = (TextView) rootView.findViewById(R.id.tv_service_price);
            this.tv_service_time = (TextView) rootView.findViewById(R.id.tv_service_time);

        }
    }

}
