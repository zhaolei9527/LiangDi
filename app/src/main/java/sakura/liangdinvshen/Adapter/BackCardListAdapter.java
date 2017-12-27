package sakura.liangdinvshen.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import sakura.liangdinvshen.Activity.AddBankCardActivity;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.BankIndexBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.View.CommomDialog;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class BackCardListAdapter extends RecyclerView.Adapter<BackCardListAdapter.ViewHolder> {

    Context mContext;
    RelativeLayout ll_empty;

    private BankIndexBean.ResBean ResBean = new BankIndexBean.ResBean();

    public BankIndexBean.ResBean getDatas() {
        return ResBean;
    }

    public BackCardListAdapter(BankIndexBean.ResBean ResBean, Context context, RelativeLayout ll_empty) {
        this.ResBean = ResBean;
        this.mContext = context;
        this.ll_empty = ll_empty;
    }

    public void setDatas(ArrayList datas) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_backcard, parent, false);
        ViewHolder vp = new ViewHolder(view);
        return vp;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String no = ResBean.getNo();
        String substring = no.substring(no.length() - 4, no.length());
        holder.tv_card_number.setText("**** **** **** ***" + substring);
        holder.tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AddBankCardActivity.class)
                        .putExtra("type", "save")
                        .putExtra("id", ResBean.getId())
                        .putExtra("name", ResBean.getName())
                        .putExtra("bank", ResBean.getBank())
                        .putExtra("banknumber", ResBean.getNo())
                );
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommomDialog(mContext, R.style.dialog, "您确定删除此银行卡吗？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            EventBus.getDefault().post(
                                    new BankEvent("nodata"));
                            bankDelete_bank(String.valueOf(ResBean.getId()));
                            ll_empty.setVisibility(View.VISIBLE);
                        }
                    }
                }).setTitle("提示").show();

            }
        });

        holder.tv_title.setText(ResBean.getBank());

        holder.tv_name.setText(ResBean.getName());

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    //自定义的ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_title;
        public TextView tv_card_number;
        public TextView tv_name;
        public TextView tv_delete;
        public TextView tv_change;
        public FrameLayout fl_item;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_card_number = (TextView) rootView.findViewById(R.id.tv_card_number);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_delete = (TextView) rootView.findViewById(R.id.tv_delete);
            this.tv_change = (TextView) rootView.findViewById(R.id.tv_change);
            this.fl_item = (FrameLayout) rootView.findViewById(R.id.fl_item);
        }

    }


    /**
     * 银行卡删除
     */
    private void bankDelete_bank(String id) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        VolleyRequest.RequestPost(mContext, UrlUtils.BASE_URL + "bank/delete_bank", "bank/delete_bank", params, new VolleyInterface(mContext) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {


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
