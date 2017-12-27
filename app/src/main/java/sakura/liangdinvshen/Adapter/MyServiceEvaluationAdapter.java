package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Activity.PriceCommentsActivity;
import sakura.liangdinvshen.Bean.FuwuDetailBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class MyServiceEvaluationAdapter extends RecyclerView.Adapter<MyServiceEvaluationAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<FuwuDetailBean.FuwuBean> datas = new ArrayList();

    public ArrayList<FuwuDetailBean.FuwuBean> getDatas() {
        return datas;
    }

    private String title;

    public MyServiceEvaluationAdapter(List<FuwuDetailBean.FuwuBean> list, Context context, String title) {
        this.datas = (ArrayList<FuwuDetailBean.FuwuBean>) list;
        this.title = title;
        this.mContext = context;
    }

    public void setDatas(List<FuwuDetailBean.FuwuBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_myservice_evaluation, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        FuwuDetailBean.FuwuBean fuwuBean = datas.get(position);
        holder.tv_wangdian.setText("服务网点：" + fuwuBean.getWang().getAddress());
        holder.tv_fuwu_name.setText("服务人：" + fuwuBean.getWang().getYuan());
        holder.tv_time.setText(DateUtils.getMillon(Long.parseLong(fuwuBean.getWang().getAdd_time()) * 1000));
        holder.tv_myservice_cishu.setText("第" + (datas.size() - position) + "次消费");
        if ("1".equals(fuwuBean.getIs_ping())) {
            holder.tv_pingjia.setText("已评价");
            holder.ll_has_pingjia.setVisibility(View.VISIBLE);
            holder.tv_pingjia_content.setText(fuwuBean.getPing().getContent());
            holder.tv_pingjia_time.setText(DateUtils.getMillon(Long.parseLong(fuwuBean.getPing().getAddtime()) * 1000));
            String xing = fuwuBean.getPing().getXing();
            holder.ll_star.removeAllViews();
            int v = (int) Double.parseDouble(xing);
            for (int i = 0; i < v; i++) {
                ImageView imageView = new ImageView(mContext);
                imageView.setBackgroundResource(R.mipmap.shoucang);
                holder.ll_star.addView(imageView);
            }
        } else {
            holder.tv_pingjia.setText("去评价");
            holder.ll_has_pingjia.setVisibility(View.GONE);
            holder.tv_pingjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, PriceCommentsActivity.class)
                            .putExtra("title", title)
                            .putExtra("id", datas.get(position).getId())
                            .putExtra("type", datas.get(position).getId())
                    );
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_myservice_cishu;
        public TextView tv_pingjia;
        public TextView tv_wangdian;
        public TextView tv_fuwu_name;
        public TextView tv_time;
        public TextView tv_mypingjia;
        public RelativeLayout rl_starandtime;
        public LinearLayout ll_star;
        public TextView tv_pingjia_time;
        public TextView tv_pingjia_content;
        public LinearLayout ll_has_pingjia;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.tv_myservice_cishu = (TextView) rootView.findViewById(R.id.tv_myservice_cishu);
            this.tv_pingjia = (TextView) rootView.findViewById(R.id.tv_pingjia);
            this.tv_wangdian = (TextView) rootView.findViewById(R.id.tv_wangdian);
            this.tv_fuwu_name = (TextView) rootView.findViewById(R.id.tv_fuwu_name);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_mypingjia = (TextView) rootView.findViewById(R.id.tv_mypingjia);
            this.tv_pingjia_time = (TextView) rootView.findViewById(R.id.tv_pingjia_time);
            this.tv_pingjia_content = (TextView) rootView.findViewById(R.id.tv_pingjia_content);
            this.rl_starandtime = (RelativeLayout) rootView.findViewById(R.id.rl_starandtime);
            this.ll_star = (LinearLayout) rootView.findViewById(R.id.ll_star);
            this.ll_has_pingjia = (LinearLayout) rootView.findViewById(R.id.ll_has_pingjia);
        }
    }

}
