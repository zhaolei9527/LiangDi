package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Bean.SuckleSuckleBean;
import sakura.liangdinvshen.R;

/**
 * Created by 赵磊 on 2017/9/29.
 */

public class FetaMovemontAdapter extends RecyclerView.Adapter<FetaMovemontAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<SuckleSuckleBean.ResBean> datas = new ArrayList();

    public ArrayList<SuckleSuckleBean.ResBean> getDatas() {
        return datas;
    }

    public FetaMovemontAdapter(List<SuckleSuckleBean.ResBean> list, Context context) {
        this.datas = (ArrayList<SuckleSuckleBean.ResBean>) list;
        this.mContext = context;
    }

    public void setDatas(List<SuckleSuckleBean.ResBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_breastfeedlist, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SuckleSuckleBean.ResBean resBean = datas.get(position);
        holder.tv_size.setText(resBean.getAmount()+"次");
        holder.tv_time.setText(resBean.getStart_time());
        holder.tv_type.setText(resBean.getEnd_time());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_time;
        public TextView tv_type;
        public TextView tv_size;
        public FrameLayout fl_item;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);
            this.tv_size = (TextView) rootView.findViewById(R.id.tv_size);
            this.fl_item = (FrameLayout) rootView.findViewById(R.id.fl_item);
        }
    }
}
