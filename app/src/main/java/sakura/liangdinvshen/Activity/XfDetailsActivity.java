package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.WangXfDetailBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/25
 * 功能描述：
 */
public class XfDetailsActivity extends BaseActivity {

    private FrameLayout rl_back;
    private SimpleDraweeView SimpleDraweeView;
    private TextView tv_title;
    private TextView tv_classify;
    private TextView tv_size;
    private LinearLayout ll;
    private TextView tv_fuwuma;
    private TextView tv_fuwu_user;
    private TextView tv_fuwu_name;
    private TextView tv_fuwu_tel;
    private TextView tv_fuwu_time;
    private TextView tv2;
    private SimpleDraweeView img;
    private View view;
    private TextView tv_name;
    private TextView tv_phone;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_xfdetails;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        SimpleDraweeView = (SimpleDraweeView) findViewById(R.id.SimpleDraweeView);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_classify = (TextView) findViewById(R.id.tv_classify);
        tv_size = (TextView) findViewById(R.id.tv_size);
        ll = (LinearLayout) findViewById(R.id.ll);
        tv_fuwuma = (TextView) findViewById(R.id.tv_fuwuma);
        tv_fuwu_user = (TextView) findViewById(R.id.tv_fuwu_user);
        tv_fuwu_name = (TextView) findViewById(R.id.tv_fuwu_name);
        tv_fuwu_tel = (TextView) findViewById(R.id.tv_fuwu_tel);
        tv_fuwu_time = (TextView) findViewById(R.id.tv_fuwu_time);
        tv2 = (TextView) findViewById(R.id.tv2);
        img = (SimpleDraweeView) findViewById(R.id.img);
        view = (View) findViewById(R.id.view);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        String id = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(id)) {
            EasyToast.showShort(context, "出错了");
            finish();
        } else {
            if (Utils.isConnected(context)) {
                dialog = Utils.showLoadingDialog(context);
                dialog.show();
                wangXfdetail(id);
            } else {
                EasyToast.showShort(context, "网络为连接");
                finish();
            }
        }
    }

    /**
     * 消费详情获取
     */
    private void wangXfdetail(String id) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "wang/xfdetail", "wang/xfdetail", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    WangXfDetailBean wangXfDetailBean = new Gson().fromJson(result, WangXfDetailBean.class);
                    if ("1".equals(String.valueOf(wangXfDetailBean.getCode()))) {
                        WangXfDetailBean.ListBean list = wangXfDetailBean.getList();
                        tv_classify.setText("￥" + list.getPrice());
                        tv_size.setText("/" + list.getNumber() + "次");
                        SimpleDraweeView.setImageURI(UrlUtils.URL + list.getFuimg());
                        tv_fuwu_tel.setText("接单人手机：" + list.getYgtel());
                        tv_fuwu_name.setText("接单人姓名：" + list.getYgname());
                        tv_fuwuma.setText("服务码：" + list.getFworder());
                        tv_fuwu_user.setText("接单人帐号：" + list.getYid());
                        tv_phone.setText("手机号：" + list.getTel());
                        tv_title.setText(list.getFuname());
                        tv_fuwu_time.setText("接单时间：" + DateUtils.getMillon(Long.parseLong(list.getAddtime()) * 1000));
                        img.setImageURI(UrlUtils.URL + list.getImg());
                        tv_name.setText(list.getNi_name());
                    }
                    result = null;
                    wangXfDetailBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
