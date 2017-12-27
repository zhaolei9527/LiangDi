package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Bean.SucklePingjiaBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.PixelUtils;
import sakura.liangdinvshen.Utils.UrlUtils;

/**
 * Created by 赵磊 on 2017/9/29.
 */

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<SucklePingjiaBean.ResBean> datas = new ArrayList();

    public ArrayList<SucklePingjiaBean.ResBean> getDatas() {
        return datas;
    }

    public EvaluationAdapter(List<SucklePingjiaBean.ResBean> list, Context context) {
        this.datas = (ArrayList<SucklePingjiaBean.ResBean>) list;
        this.mContext = context;
    }

    public void setDatas(ArrayList<SucklePingjiaBean.ResBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_evaluation, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.sdv_pingjia.setImageURI(UrlUtils.URL + datas.get(position).getHeadimg());
        holder.tv_pingjia_name.setText(datas.get(position).getNickname());
        holder.tv_pingjia_content.setText(datas.get(position).getContent());
        String addtime = datas.get(position).getAddtime();
        holder.tv_pingjia_time.setText(String.valueOf(DateUtils.getDay(Long.parseLong(addtime) * 1000)));
        String xing = datas.get(position).getXing();
        int i = Integer.parseInt(xing);
        holder.ll_star.removeAllViews();
        for (int i1 = 0; i1 < 5; i1++) {
            ImageView imageView = new ImageView(holder.ll_star.getContext());
            imageView.setPadding(PixelUtils.dip2px(holder.ll_star.getContext(), 3), 0, 0, 0);
            if (i1 < i) {
                imageView.setBackgroundResource(R.mipmap.shoucang);
            } else {
                imageView.setBackgroundResource(R.mipmap.new_sc1);
            }
            holder.ll_star.addView(imageView);
        }
        if (TextUtils.isEmpty(datas.get(position).getHf())) {
            holder.img_pingjia_huifu.setVisibility(View.GONE);
            holder.tv_pingjia_huifu.setVisibility(View.GONE);
        } else {
            holder.img_pingjia_huifu.setVisibility(View.VISIBLE);
            holder.tv_pingjia_huifu.setVisibility(View.VISIBLE);
            holder.tv_pingjia_huifu.setText("掌柜回复：" + datas.get(position).getHf());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public SimpleDraweeView sdv_pingjia;
        public TextView tv_pingjia_name;
        public TextView tv_pingjia_time;
        public TextView tv_pingjia_content;
        public TextView tv_pingjia_huifu;
        public LinearLayout ll_star;
        public ImageView img_pingjia_huifu;


        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.sdv_pingjia = (SimpleDraweeView) rootView.findViewById(R.id.sdv_pingjia);
            this.tv_pingjia_name = (TextView) rootView.findViewById(R.id.tv_pingjia_name);
            this.tv_pingjia_time = (TextView) rootView.findViewById(R.id.tv_pingjia_time);
            this.tv_pingjia_content = (TextView) rootView.findViewById(R.id.tv_pingjia_content);
            this.tv_pingjia_huifu = (TextView) rootView.findViewById(R.id.tv_pingjia_huifu);
            this.ll_star = (LinearLayout) rootView.findViewById(R.id.ll_star);
            this.img_pingjia_huifu = (ImageView) rootView.findViewById(R.id.img_pingjia_huifu);

        }
    }

}
