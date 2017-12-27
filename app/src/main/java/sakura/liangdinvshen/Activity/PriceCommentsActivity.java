package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.CodeBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/22
 * 功能描述：
 */
public class PriceCommentsActivity extends BaseActivity {

    private ImageView img_back;
    private RelativeLayout rl_title;
    private TextView tv_title;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private EditText et_priceComments;
    private TextView tv_newsComments_commit;
    private String star = "5";
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_price_comments;
    }

    @Override
    protected void initview() {
        img_back = (ImageView) findViewById(R.id.img_back);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        tv_title = (TextView) findViewById(R.id.tv_title);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        et_priceComments = (EditText) findViewById(R.id.et_priceComments);
        tv_newsComments_commit = (TextView) findViewById(R.id.tv_newsComments_commit);
        tv_title.setText(getIntent().getStringExtra("title"));
    }

    @Override
    protected void initListener() {
        tv_newsComments_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star = "1";
                img1.setBackgroundResource(R.mipmap.shoucang);
                img2.setBackgroundResource(R.mipmap.new_sc2);
                img3.setBackgroundResource(R.mipmap.new_sc2);
                img4.setBackgroundResource(R.mipmap.new_sc2);
                img5.setBackgroundResource(R.mipmap.new_sc2);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star = "2";
                img1.setBackgroundResource(R.mipmap.shoucang);
                img2.setBackgroundResource(R.mipmap.shoucang);
                img3.setBackgroundResource(R.mipmap.new_sc2);
                img4.setBackgroundResource(R.mipmap.new_sc2);
                img5.setBackgroundResource(R.mipmap.new_sc2);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star = "3";
                img1.setBackgroundResource(R.mipmap.shoucang);
                img2.setBackgroundResource(R.mipmap.shoucang);
                img3.setBackgroundResource(R.mipmap.shoucang);
                img4.setBackgroundResource(R.mipmap.new_sc2);
                img5.setBackgroundResource(R.mipmap.new_sc2);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star = "4";
                img1.setBackgroundResource(R.mipmap.shoucang);
                img2.setBackgroundResource(R.mipmap.shoucang);
                img3.setBackgroundResource(R.mipmap.shoucang);
                img4.setBackgroundResource(R.mipmap.shoucang);
                img5.setBackgroundResource(R.mipmap.new_sc2);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star = "5";
                img1.setBackgroundResource(R.mipmap.shoucang);
                img2.setBackgroundResource(R.mipmap.shoucang);
                img3.setBackgroundResource(R.mipmap.shoucang);
                img4.setBackgroundResource(R.mipmap.shoucang);
                img5.setBackgroundResource(R.mipmap.shoucang);
            }
        });

    }

    @Override
    protected void initData() {

    }

    private void submit() {
        // validate
        String priceComments = et_priceComments.getText().toString().trim();
        if (TextUtils.isEmpty(priceComments)) {
            Toast.makeText(this, "请写下你的评论...", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            String type = getIntent().getStringExtra("type");
            if (TextUtils.isEmpty(type)) {
                orderPingjia(getIntent().getStringExtra("id"));
            } else {
                fuwuDopingjia(getIntent().getStringExtra("id"));
            }
        } else {
            EasyToast.showShort(context, "网络未连接");
        }

    }


    /**
     * 商品评价
     */
    private void orderPingjia(String id) {
        HashMap<String, String> params = new HashMap<>(5);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("xing", star);
        params.put("content", et_priceComments.getText().toString());
        Log.e("RegisterActivity", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "order/dopingjia", "order/dopingjia", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("900".equals(String.valueOf(codeBean.getCode()))) {
                        EasyToast.showShort(context, "评价成功");
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    } else {
                        EasyToast.showShort(context, "评价失败");
                    }
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

    /**
     * 服务评价
     */
    private void fuwuDopingjia(String id) {
        HashMap<String, String> params = new HashMap<>(5);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("xing", star);
        params.put("content", et_priceComments.getText().toString());
        Log.e("RegisterActivity", "params:" + params);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "fuwu/dopingjia", "fuwu/dopingjia", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("900".equals(String.valueOf(codeBean.getCode()))) {
                        EasyToast.showShort(context, "评价成功");
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    } else {
                        EasyToast.showShort(context, "评价失败");
                    }
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
