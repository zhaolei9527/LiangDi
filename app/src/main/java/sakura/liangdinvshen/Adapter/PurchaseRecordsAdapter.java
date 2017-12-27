package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.R;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class PurchaseRecordsAdapter extends RecyclerView.Adapter<PurchaseRecordsAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<String> datas = new ArrayList();

    public ArrayList<String> getDatas() {
        return datas;
    }

    public PurchaseRecordsAdapter(List<String> list, Context context) {
        this.datas = (ArrayList<String>) list;
        this.mContext = context;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_purchaserecords, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        Log.d("ShopListAdapter", "datas.size():" + datas.size());
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public SimpleDraweeView SimpleDraweeView;
        public TextView tv_name;
        public TextView tv_buy;
        public TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.SimpleDraweeView = (SimpleDraweeView) rootView.findViewById(R.id.SimpleDraweeView);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_buy = (TextView) rootView.findViewById(R.id.tv_buy);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        }
    }

}
