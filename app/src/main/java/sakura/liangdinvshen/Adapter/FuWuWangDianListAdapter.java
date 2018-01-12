package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import sakura.liangdinvshen.Bean.WangListBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.UrlUtils;

/**
 * Created by 赵磊 on 2017/9/29.
 */

public class FuWuWangDianListAdapter extends RecyclerView.Adapter<FuWuWangDianListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<WangListBean.ListBean> datas = new ArrayList();

    public ArrayList<WangListBean.ListBean> getDatas() {
        return datas;
    }

    public FuWuWangDianListAdapter(List<WangListBean.ListBean> list, Context context) {
        this.datas = (ArrayList<WangListBean.ListBean>) list;
        this.mContext = context;
    }

    public void setDatas(List<WangListBean.ListBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fuwuwangdian, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        WangListBean.ListBean listBean = datas.get(position);
        holder.img.setImageURI(UrlUtils.URL + listBean.getImg());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(listBean.getProvince());
        stringBuilder.append(listBean.getCity());
        stringBuilder.append(listBean.getCountry());
        stringBuilder.append(listBean.getAddress());

        holder.tv_address.setText(stringBuilder.toString());

        holder.tv_tel.setText(listBean.getTel());

        holder.tv_name.setText(listBean.getTitle());

        holder.rl_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(holder.tv_tel.getText())) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + holder.tv_tel.getText()));//跳转到拨号界面，同时传递电话号码
                    holder.tv_tel.getContext().startActivity(dialIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public SimpleDraweeView img;
        public TextView tv_name;
        public TextView tv_address;
        public TextView tv_tel;

        public RelativeLayout rl_tel;


        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.img = (SimpleDraweeView) rootView.findViewById(R.id.img);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_address = (TextView) rootView.findViewById(R.id.tv_address);
            this.tv_tel = (TextView) rootView.findViewById(R.id.tv_tel);
            this.rl_tel = (RelativeLayout) rootView.findViewById(R.id.rl_tel);
        }
    }


}
