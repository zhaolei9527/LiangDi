package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Bean.WithDrawListDataBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class WithdrawListAdapter extends RecyclerView.Adapter<WithdrawListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<WithDrawListDataBean.ResBean> datas = new ArrayList();

    public ArrayList<WithDrawListDataBean.ResBean> getDatas() {
        return datas;
    }

    public WithdrawListAdapter(List<WithDrawListDataBean.ResBean> list, Context context) {
        this.datas = (ArrayList<WithDrawListDataBean.ResBean>) list;
        this.mContext = context;
    }

    public void setDatas(List datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_withdraw, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_bank.setText(datas.get(position).getBank());
        holder.tv_money.setText("￥" + datas.get(position).getTixian_num());
        holder.tv_time.setText(DateUtils.getMillon(Long.parseLong(datas.get(position).getAdd_time()) * 1000));
        holder.tv_type.setText(datas.get(position).getStu());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_bank;
        public TextView tv_type;
        public TextView tv_time;
        public TextView tv_money;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.tv_bank = (TextView) rootView.findViewById(R.id.tv_bank);
            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_money = (TextView) rootView.findViewById(R.id.tv_money);
        }
    }

}
