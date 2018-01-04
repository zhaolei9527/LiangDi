package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Bean.WangCenterBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.UrlUtils;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class StaffManagemntListAdapter extends RecyclerView.Adapter<StaffManagemntListAdapter.ViewHolder> {
    RelativeLayout ll_empty;

    Context mContext;
    private ArrayList<WangCenterBean.ListBean> datas = new ArrayList();

    public ArrayList<WangCenterBean.ListBean> getDatas() {
        return datas;
    }

    public StaffManagemntListAdapter(List<WangCenterBean.ListBean> list, Context context, RelativeLayout ll_empty) {
        this.datas.clear();
        this.datas.addAll(list);
        this.mContext = context;
        this.ll_empty = ll_empty;

    }

    public void setDatas(List<WangCenterBean.ListBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_staffmanagemntlist, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (datas.get(position).getImg().startsWith("http://wx.qlogo.cn/")) {
            holder.img.setImageURI(datas.get(position).getImg());
        } else {
            holder.img.setImageURI(UrlUtils.URL + datas.get(position).getImg());
        }

        holder.tv_fuwuma.setText("注册时间：" + DateUtils.getMillon(Long.parseLong(datas.get(position).getAdd_time()) * 1000));
        holder.tv_money.setText("手机号：" + datas.get(position).getTel());
        holder.tv_name.setText(datas.get(position).getNi_name());
        if (!TextUtils.isEmpty(datas.get(position).getLast_login_time())) {
            holder.tv_phone.setText("最近登录：" + DateUtils.getMillon(Long.parseLong(datas.get(position).getLast_login_time()) * 1000));
        }
        holder.tv_time.setText("帐号：" + datas.get(position).getTel());
        holder.tv_num.setText("id:" + datas.get(position).getId());
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
            this.tv_fuwuma = (TextView) rootView.findViewById(R.id.tv_fuwuma);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_phone = (TextView) rootView.findViewById(R.id.tv_phone);
            this.tv_money = (TextView) rootView.findViewById(R.id.tv_money);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
        }
    }

}
