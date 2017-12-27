package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Bean.MynewsNewsListBean;
import sakura.liangdinvshen.R;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<MynewsNewsListBean.ResBean> datas = new ArrayList();

    public ArrayList<MynewsNewsListBean.ResBean> getDatas() {
        return datas;
    }

    public MessageListAdapter(List<MynewsNewsListBean.ResBean> list, Context context) {
        this.datas = (ArrayList<MynewsNewsListBean.ResBean>) list;
        this.mContext = context;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        MynewsNewsListBean.ResBean resBean = datas.get(position);
        if ("1".equals(String.valueOf(resBean.getStatus()))) {
            holder.img_xiaoxi.setBackgroundResource(R.mipmap.xiaoxi);
        } else {
            holder.img_xiaoxi.setBackgroundResource(R.mipmap.xiaoxi1);
        }

        holder.tv_content.setText(resBean.getContent());

        holder.tv_title.setText(resBean.getType());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView img_xiaoxi;
        public TextView tv_title;
        public TextView tv_content;
        public TextView tv_time;
        public FrameLayout fl_item;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.img_xiaoxi = (ImageView) rootView.findViewById(R.id.img_xiaoxi);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.fl_item = (FrameLayout) rootView.findViewById(R.id.fl_item);
        }

    }
}
