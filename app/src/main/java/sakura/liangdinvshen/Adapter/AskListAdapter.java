package sakura.liangdinvshen.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.ProblemGetProblemBean;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.Fragment.RecordFragment;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * Created by 赵磊 on 2017/9/29.
 */

public class AskListAdapter extends RecyclerView.Adapter<AskListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<ProblemGetProblemBean.ResBean> datas = new ArrayList();
    private Dialog dialog;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ProblemGetProblemBean.ResBean resBean = datas.get(position);
        holder.btnIsChoosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnIsChoosed2.setChecked(false);
                datas.get(position).setAnswer(1);
            }
        });

        holder.btnIsChoosed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnIsChoosed.setChecked(false);
                datas.get(position).setAnswer(2);
            }
        });

        holder.tv_title.setText(String.valueOf(position + 1) + "、" + resBean.getProblem());


        if ("1".equals(String.valueOf(resBean.getAnswer()))) {
            holder.btnIsChoosed.setChecked(true);
            holder.btnIsChoosed2.setChecked(false);
        } else if ("2".equals(String.valueOf(resBean.getAnswer()))) {
            holder.btnIsChoosed.setChecked(false);
            holder.btnIsChoosed2.setChecked(true);
        } else {
            holder.btnIsChoosed.setChecked(false);
            holder.btnIsChoosed2.setChecked(false);
        }

        if (position == (datas.size() - 1)) {
            holder.btn_submit.setVisibility(View.VISIBLE);
        } else {
            holder.btn_submit.setVisibility(View.GONE);
        }


        holder.btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isConnected(v.getContext())) {
                    dialog = Utils.showLoadingDialog(v.getContext());
                    dialog.show();
                    problemSubmitProblem(v.getContext(), datas);
                } else {
                    EasyToast.showShort(v.getContext(), "网络未连接");
                }
            }
        });


    }

    /**
     * 免责声明获取
     */
    private void problemSubmitProblem(final Context context, ArrayList<ProblemGetProblemBean.ResBean> datas) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < datas.size(); i++) {
            if (i == 0) {
                stringBuilder.append(datas.get(i).getId() + "=" + datas.get(i).getAnswer());
            } else {
                stringBuilder.append("," + datas.get(i).getId() + "=" + datas.get(i).getAnswer());
            }
        }

        params.put("id", stringBuilder.toString());

        Log.e("AskListAdapter", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "problem/submit_problem", "problem/submit_problem", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(context, "提交成功");
                        EventBus.getDefault().post(
                                new BankEvent("ask", "ask"));
                    } else {
                        EasyToast.showShort(context, "提交失败");
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                Toast.makeText(context, context.getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
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
        public TextView tv_title;
        public CheckBox btnIsChoosed;
        public CheckBox btnIsChoosed2;
        public Button btn_submit;


        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.btnIsChoosed = (CheckBox) rootView.findViewById(R.id.btnIsChoosed);
            this.btnIsChoosed2 = (CheckBox) rootView.findViewById(R.id.btnIsChoosed2);
            this.btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
        }
    }


}
