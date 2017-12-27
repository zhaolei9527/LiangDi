package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
 * @date 2017/12/25
 * 功能描述：
 */
public class ConsumptionActivity extends BaseActivity implements View.OnClickListener {


    private FrameLayout rl_back;
    private EditText et_code;
    private Button btn_submit;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_consumption;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void submit() {
        // validate
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "请输入服务码~", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            wangXiaofei(code);
        } else {
            EasyToast.showShort(context, "网络未连接");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_submit:
                submit();
                break;
            default:
                break;
        }
    }


    /**
     * 免责声明获取
     */
    private void wangXiaofei(String oid) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("oid", oid);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Log.e("RegisterActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "wang/xiaofei", "wang/xiaofei", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {
                        et_code.setText("");
                        EasyToast.showShort(context, "消费成功");
                    } else {
                        EasyToast.showShort(context, codeBean.getMsg());
                    }
                    codeBean = null;
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

}
