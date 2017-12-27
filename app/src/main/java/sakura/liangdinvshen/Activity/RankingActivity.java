package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.SinglepageGlossaryBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/27
 * 功能描述：
 */
public class RankingActivity extends BaseActivity {


    private FrameLayout rl_back;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_ranking;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
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
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            singlepageGlossary();
        } else {
            EasyToast.showShort(context, "网络未连接");
            finish();
        }
    }


    /**
     * 免责声明获取
     */
    private void singlepageGlossary() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "singlepage/glossary", "singlepage/glossary", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                dialog.dismiss();
                try {
                    SinglepageGlossaryBean singlepageGlossaryBean = new Gson().fromJson(result, SinglepageGlossaryBean.class);
                    if ("1".equals(String.valueOf(singlepageGlossaryBean.getStu()))) {
                        for (int i = 0; i < singlepageGlossaryBean.getData().size(); i++) {
                            if (i == 0) {
                                tv_1.setText(singlepageGlossaryBean.getData().get(i).getContent());
                            } else if (i == 1) {
                                tv_2.setText(singlepageGlossaryBean.getData().get(i).getContent());
                            } else if (i == 2) {
                                tv_3.setText(singlepageGlossaryBean.getData().get(i).getContent());
                            } else if (i == 3) {
                                tv_4.setText(singlepageGlossaryBean.getData().get(i).getContent());
                            }
                        }
                    }
                    singlepageGlossaryBean = null;
                    result = null;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("singlepage/glossary");
    }
}
