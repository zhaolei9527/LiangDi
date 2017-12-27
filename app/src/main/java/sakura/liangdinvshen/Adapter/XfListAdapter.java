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

import sakura.liangdinvshen.Bean.XflistBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.UrlUtils;

/**
 * Created by 赵磊 on 2017/9/29.
 */

public class XfListAdapter extends RecyclerView.Adapter<XfListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<XflistBean.ListBean> datas = new ArrayList();

    public ArrayList<XflistBean.ListBean> getDatas() {
        return datas;
    }

    public XfListAdapter(List<XflistBean.ListBean> list, Context context) {
        this.datas = (ArrayList<XflistBean.ListBean>) list;
        this.mContext = context;
    }

    public void setDatas(List<XflistBean.ListBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_xflist, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        XflistBean.ListBean listBean = datas.get(position);
        holder.img.setImageURI(UrlUtils.URL + listBean.getImg());
        holder.tv_fuwuma.setText("服务码：" + listBean.getFworder());
        holder.tv_money.setText("￥" + listBean.getPrice());
        holder.tv_name.setText(listBean.getNi_name());
        holder.tv_num.setText("/" + listBean.getNumber() + "次");
        holder.tv_phone.setText("手机号:" + listBean.getTel());
        holder.tv_time.setText("接单时间:" + DateUtils.getMillon(Long.parseLong(listBean.getAddtime()) * 1000));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public SimpleDraweeView img;
        public TextView tv_fuwuma;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_phone;
        public TextView tv_money;
        public TextView tv_num;


        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.img = (SimpleDraweeView) rootView.findViewById(R.id.img);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_fuwuma = (TextView) rootView.findViewById(R.id.tv_fuwuma);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_phone = (TextView) rootView.findViewById(R.id.tv_phone);
            this.tv_money = (TextView) rootView.findViewById(R.id.tv_money);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
        }
    }

}
