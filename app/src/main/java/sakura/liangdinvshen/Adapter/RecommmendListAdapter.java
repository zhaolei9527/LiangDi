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

import sakura.liangdinvshen.Bean.MyRecommendIndexBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.UrlUtils;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class RecommmendListAdapter extends RecyclerView.Adapter<RecommmendListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<MyRecommendIndexBean.ResBean> datas = new ArrayList();

    public ArrayList<MyRecommendIndexBean.ResBean> getDatas() {
        return datas;
    }

    public RecommmendListAdapter(List<MyRecommendIndexBean.ResBean> list, Context context) {
        this.datas = (ArrayList<MyRecommendIndexBean.ResBean>) list;
        this.mContext = context;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recmmend, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        MyRecommendIndexBean.ResBean resBean = datas.get(position);
        try {
            if (resBean.getImg().startsWith("http://")) {
                holder.img.setImageURI(resBean.getImg());
            } else {
                holder.img.setImageURI(UrlUtils.URL + resBean.getImg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.tv_time.setText(DateUtils.getMillon(Long.parseLong(resBean.getAdd_time()) * 1000));
        holder.tv_title.setText(resBean.getNi_name());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public SimpleDraweeView img;
        public TextView tv_title;
        public TextView tv_time;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.img = (SimpleDraweeView) rootView.findViewById(R.id.img);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        }

    }
}
