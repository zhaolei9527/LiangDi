package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;

import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.SinglepageAboutBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;


/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/1
 * 功能描述：
 */
public class WhitUsDetailsActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout rl_back;
    private TextView tv_news_title;
    private WebView forum_context;
    private Dialog dialog;
    private TextView tv_title;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("singlepage/about");
        System.gc();
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_withus_details;
    }

    @Override
    protected void initview() {
        initView();
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
        IX5WebViewExtension ix5 = forum_context.getX5WebViewExtension();
        if (null != ix5) {
            ix5.setScrollBarFadingEnabled(false);
        }
        // 开启 localStorage
        forum_context.getSettings().setDomStorageEnabled(true);
        // 设置支持javascript
        forum_context.getSettings().setJavaScriptEnabled(true);
        // 启动缓存
        forum_context.getSettings().setAppCacheEnabled(true);
        // 设置缓存模式
        forum_context.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)) {
            return;
        }

        String type = getIntent().getStringExtra("type");
        if (TextUtils.isEmpty(type)) {
            Toast.makeText(context, "出错了", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if ("withus".equals(type)) {
                tv_title.setText("关于我们");
                singlepageAbout();
            } else {
                tv_title.setText("帮助与反馈");
                singlepageHelp();
            }
        }

    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(this);
        // 启动缓存
        forum_context.getSettings().setAppCacheEnabled(true);
        forum_context.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        forum_context.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                // forumContext.loadUrl("javascript:(" + readJS() + ")()");
                dialog.dismiss();
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                webView.measure(w, h);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                Toast.makeText(context, getString(R.string.hasError), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void initData() {
    }

    /**
     * 关于我们内容获取
     */
    private void singlepageAbout() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "singlepage/about", "singlepage/about", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("MessageDetailsActivity", result);
                try {
                    SinglepageAboutBean singlepageAboutBean = new Gson().fromJson(result, SinglepageAboutBean.class);
                    if ("1".equals(String.valueOf(singlepageAboutBean.getStu()))) {
                        tv_news_title.setText(singlepageAboutBean.getRes().getTitle());
                        Spanned spanned = Html.fromHtml(Utils.decode(singlepageAboutBean.getRes().getContent()));
                        Utils.inSetWebView(spanned.toString(), forum_context, context);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                } finally {
                    result = null;
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 关于我们内容获取
     */
    private void singlepageHelp() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "singlepage/help", "singlepage/help", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("MessageDetailsActivity", result);
                try {
                    SinglepageAboutBean singlepageAboutBean = new Gson().fromJson(result, SinglepageAboutBean.class);
                    if ("1".equals(String.valueOf(singlepageAboutBean.getStu()))) {
                        tv_news_title.setText(singlepageAboutBean.getRes().getTitle());
                        Spanned spanned = Html.fromHtml(Utils.decode(singlepageAboutBean.getRes().getContent()));
                        Utils.inSetWebView(spanned.toString(), forum_context, context);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                } finally {
                    result = null;
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_news_title = (TextView) findViewById(R.id.tv_news_title);
        forum_context = (WebView) findViewById(R.id.forum_context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            default:
                break;
        }
    }

}
