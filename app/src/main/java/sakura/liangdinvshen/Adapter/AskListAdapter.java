package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Bean.ProblemGetProblemBean;
import sakura.liangdinvshen.R;

/**
 * Created by 赵磊 on 2017/9/29.
 */

public class AskListAdapter extends RecyclerView.Adapter<AskListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<ProblemGetProblemBean.ResBean> datas = new ArrayList();

    public ArrayList<ProblemGetProblemBean.ResBean> getDatas() {
        return datas;
    }

    public AskListAdapter(List<ProblemGetProblemBean.ResBean> list, Context context) {
        this.datas = (ArrayList<ProblemGetProblemBean.ResBean>) list;
        this.mContext = context;
    }

    public void setDatas(List<ProblemGetProblemBean.ResBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ask, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ProblemGetProblemBean.ResBean resBean = datas.get(position);
        holder.btnIsChoosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnIsChoosed2.setChecked(false);

            }
        });

        holder.btnIsChoosed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnIsChoosed.setChecked(false);
            }
        });

        holder.tv_title.setText(String.valueOf(position + 1) + "、" + resBean.getProblem_title());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_title;
        public CheckBox btnIsChoosed;
        public CheckBox btnIsChoosed2;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.btnIsChoosed = (CheckBox) rootView.findViewById(R.id.btnIsChoosed);
            this.btnIsChoosed2 = (CheckBox) rootView.findViewById(R.id.btnIsChoosed2);
        }
    }


}
