package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Bean.CollectionGoodsBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.UrlUtils;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class CollectionShopListAdapter extends RecyclerView.Adapter<CollectionShopListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<CollectionGoodsBean.ResBean> datas = new ArrayList();

    public ArrayList<CollectionGoodsBean.ResBean> getDatas() {
        return datas;
    }

    public CollectionShopListAdapter(List<CollectionGoodsBean.ResBean> list, Context context) {
        this.datas.clear();
        this.datas.addAll(list);
        this.mContext = context;
    }

    public void setDatas(ArrayList<CollectionGoodsBean.ResBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shop_price, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Uri uri = Uri.parse(UrlUtils.URL + datas.get(position).getImg());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.SimpleDraweeView.getController())
                .build();
        holder.SimpleDraweeView.setController(controller);
        holder.tv_look.setText("销量：" + datas.get(position).getXiaoliang());
        holder.tv_title.setText(datas.get(position).getTitle());
        holder.tv_classify.setText("￥" + datas.get(position).getPrice());
        holder.tv_type.setText(datas.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public SimpleDraweeView SimpleDraweeView;
        public TextView tv_title;
        public TextView tv_classify;
        public TextView tv_look;
        public TextView tv_type;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.SimpleDraweeView = (SimpleDraweeView) rootView.findViewById(R.id.SimpleDraweeView);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_classify = (TextView) rootView.findViewById(R.id.tv_classify);
            this.tv_look = (TextView) rootView.findViewById(R.id.tv_look);
            this.tv_type = (TextView) rootView.findViewById(R.id.tv_type);

        }
    }

}
