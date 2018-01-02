package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Bean.NewsDetailsBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.UrlUtils;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class NewsPLListAdapter extends RecyclerView.Adapter<NewsPLListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<NewsDetailsBean.ResBean.PlListBean> datas = new ArrayList();

    public ArrayList<NewsDetailsBean.ResBean.PlListBean> getDatas() {
        return datas;
    }

    public NewsPLListAdapter(List<NewsDetailsBean.ResBean.PlListBean> list, Context context) {
        this.datas = (ArrayList<NewsDetailsBean.ResBean.PlListBean>) list;
        this.mContext = context;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news_pl, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (!TextUtils.isEmpty(datas.get(position).getHead_img())) {
            if (datas.get(position).getHead_img().startsWith("http://wx.qlogo.cn/")) {
                holder.SimpleDraweeView.setImageURI(datas.get(position).getHead_img());
            } else {
                holder.SimpleDraweeView.setImageURI(UrlUtils.URL + datas.get(position).getHead_img());
            }
        }
        holder.tv_pl_content.setText(datas.get(position).getContent());
        holder.tv_pl_name.setText(datas.get(position).getUname());
        holder.tv_pl_time.setText(DateUtils.getDay(Long.parseLong(datas.get(position).getAdd_time()) * 1000));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public SimpleDraweeView SimpleDraweeView;
        public TextView tv_pl_name;
        public TextView tv_pl_time;
        public TextView tv_pl_content;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.SimpleDraweeView = (SimpleDraweeView) rootView.findViewById(R.id.SimpleDraweeView);
            this.tv_pl_name = (TextView) rootView.findViewById(R.id.tv_pl_name);
            this.tv_pl_time = (TextView) rootView.findViewById(R.id.tv_pl_time);
            this.tv_pl_content = (TextView) rootView.findViewById(R.id.tv_pl_content);
        }
    }

}
