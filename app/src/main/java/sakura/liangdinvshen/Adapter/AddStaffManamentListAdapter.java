package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sakura.liangdinvshen.Bean.CodeBean;
import sakura.liangdinvshen.Bean.WangQbListBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * Created by 赵磊 on 2017/9/29.
 */

public class AddStaffManamentListAdapter extends RecyclerView.Adapter<AddStaffManamentListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<WangQbListBean.ListBean> datas = new ArrayList();

    public ArrayList<WangQbListBean.ListBean> getDatas() {
        return datas;
    }

    public AddStaffManamentListAdapter(List<WangQbListBean.ListBean> list, Context context) {
        this.datas = (ArrayList<WangQbListBean.ListBean>) list;
        this.mContext = context;
    }

    public void setDatas(List<WangQbListBean.ListBean> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_addstaffmanagement, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final WangQbListBean.ListBean listBean = datas.get(position);
        holder.img.setImageURI(UrlUtils.URL + listBean.getImg());
        holder.tv_name.setText(listBean.getNi_name());
        holder.tv_time.setText("注册时间：" + DateUtils.getMillon(Long.parseLong(listBean.getAdd_time()) * 1000));
        holder.btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wangYgadd(listBean.getId());
                datas.remove(position);
                notifyDataSetChanged();
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
        public TextView tv_time;
        public Button btn_add_address;


        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.img = (SimpleDraweeView) rootView.findViewById(R.id.img);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.btn_add_address = (Button) rootView.findViewById(R.id.btn_add_address);
        }
    }


    /**
     * 员工添加
     */
    private void wangYgadd(String id) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(mContext, "uid", "")));
        params.put("uid2", id);
        VolleyRequest.RequestPost(mContext, UrlUtils.BASE_URL + "wang/ygadd", "wang/ygadd", params, new VolleyInterface(mContext) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {
                        Toast.makeText(mContext, "添加完成", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, codeBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    codeBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, mContext.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(mContext, mContext.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
