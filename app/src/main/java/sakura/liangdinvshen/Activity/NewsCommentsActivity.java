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

import java.util.Date;
import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.CodeBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/5
 * 功能描述：咨询评论
 */
public class NewsCommentsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private RelativeLayout rl_title;
    private EditText et_newsComments;
    private TextView tv_newsComments_commit;
    private String id;
    private String uid;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_newscomments;
    }

    @Override
    protected void initview() {
        initView();
        id = getIntent().getStringExtra("id");
        uid = (String) SpUtil.get(context, "uid", "");
    }

    @Override
    protected void initListener() {
        img_back.setOnClickListener(this);
        tv_newsComments_commit.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        et_newsComments = (EditText) findViewById(R.id.et_newsComments);
        tv_newsComments_commit = (TextView) findViewById(R.id.tv_newsComments_commit);
    }

    private void submit() {
        // validate
        String newsComments = et_newsComments.getText().toString().trim();
        if (TextUtils.isEmpty(newsComments)) {
            Toast.makeText(this, "评论不能为空...", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        dialog = Utils.showLoadingDialog(context);
        dialog.show();

        newsCommentsSubmit(newsComments);
    }

    /**
     * 新闻评论提交
     */
    private void newsCommentsSubmit(final String content) {
        HashMap<String, String> params = new HashMap<>(4);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        params.put("uid", uid);
        params.put("content", content);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "news/nsping_add", "news/nsping_add", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                String decode = result;
                Log.e("NewsCommentsActivity", decode);
                try {
                    CodeBean codeBean = new Gson().fromJson(decode, CodeBean.class);
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {
                        Date date = new Date();
                        long l = date.getTime() / 1000;
                        setResult(200, new Intent()
                                .putExtra("content", content)
                                .putExtra("time", String.valueOf(l))
                                .putExtra("img", String.valueOf(SpUtil.get(context, "img", "")))
                        );
                        Toast.makeText(NewsCommentsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        SpUtil.remove(context, "news" + id);
                        finish();
                    } else {
                        Toast.makeText(NewsCommentsActivity.this, codeBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    codeBean = null;
                    decode = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_newsComments_commit:
                submit();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("news/nsping_add");
        System.gc();
    }
}
