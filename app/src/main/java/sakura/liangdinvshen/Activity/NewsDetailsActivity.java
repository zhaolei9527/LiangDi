package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.v7.widget.DefaultItemAnimator;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.sharesdk.tencent.qq.QQ;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.ThumbnailUtils;
import io.vov.vitamio.provider.MediaStore;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.NewsPLListAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.CodeBean;
import sakura.liangdinvshen.Bean.NewsDetailsBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.PixelUtils;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.FullyLinearLayoutManager;
import sakura.liangdinvshen.View.LiangDiRecycleView;
import sakura.liangdinvshen.View.ProgressView;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;
import sakura.liangdinvshen.onekeyshare.OnekeyShare;


/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/1
 * 功能描述：
 */
public class NewsDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView tv_news_title;
    private TextView tv_releaseTime;
    private TextView tv_view;
    private WebView forum_context;
    private TextView tv_pinglun;
    private LiangDiRecycleView pinglun_lv;
    private ScrollView sv;
    private ImageView img_shoucang;
    private ImageView img_share;
    private Dialog dialog;
    private int p = 1;
    private FullyLinearLayoutManager line;
    private NewsPLListAdapter adapter;
    private String id;
    private VideoView mVideoView;
    private MediaController mController;
    private FrameLayout mFlVideoGroup;
    private ImageView mIvStart;
    private ImageView mIvThumbnail;
    private RelativeLayout rl_title;
    private RelativeLayout rl_contentMessage;
    private LinearLayout ll_Topingjia;

    //当前是否为全屏
    private Boolean mIsFullScreen = false;
    private final int commentResult = 200;
    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private NewsDetailsBean newsDetailsBean;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            //清除缓存
            mVideoView.destroyDrawingCache();
            //停止播放
            mVideoView.stopPlayback();
            mVideoView = null;
        }
        App.getQueues().cancelAll("news/detail");
        System.gc();
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initview() {
        initView();
        dialog = Utils.showLoadingDialog(context);
        if (!dialog.isShowing()) {
            dialog.show();
        }
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

    }

    @Override
    protected void initListener() {
        img_share.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_shoucang.setOnClickListener(this);
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
                tv_pinglun.setVisibility(View.VISIBLE);
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
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            getNews(id);
        } else {
            Toast.makeText(context, getString(R.string.hasError), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 新闻内容获取
     */
    private void getNews(final String id) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        params.put("p", String.valueOf(p));
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "news/detail", "news/detail", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                String decode = result;
                Log.e("NewsDetailsActivity", decode);
                try {
                    newsDetailsBean = new Gson().fromJson(decode, NewsDetailsBean.class);
                    if ("1".equals(String.valueOf(newsDetailsBean.getCode()))) {
                        if (p == 1) {
                            //SpUtil.putAndApply(context, "news" + id, decode);
                            if ("1".equals(newsDetailsBean.getRes().getIs_shou())) {
                                img_shoucang.setBackground(getResources().getDrawable(R.mipmap.new_sc2));
                            } else {
                                img_shoucang.setBackground(getResources().getDrawable(R.mipmap.new_sc1));
                            }
                            if (!TextUtils.isEmpty(newsDetailsBean.getRes().getVideo_url())) {
                                mFlVideoGroup.setVisibility(View.VISIBLE);
                                singleThreadExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        //设置缩略图,Vitamio提供的工具类。
                                        final Bitmap videoThumbnail = ThumbnailUtils.createVideoThumbnail(
                                                context, UrlUtils.URL + newsDetailsBean.getRes().getImg()
                                                , MediaStore.Video.Thumbnails.MINI_KIND);
                                        if (videoThumbnail != null) {
                                            mIvThumbnail.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mIvThumbnail.setImageBitmap(videoThumbnail);
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                mFlVideoGroup.setVisibility(View.GONE);
                            }
                            tv_news_title.setText(newsDetailsBean.getRes().getTitle());
                            tv_releaseTime.setText("发布时间:" + DateUtils.getDay(Long.parseLong(newsDetailsBean.getRes().getAdd_time()) * 1000));
                            tv_view.setText("浏览量:" + newsDetailsBean.getRes().getView());
                            tv_pinglun.setText(newsDetailsBean.getRes().getPl_count() + "个评论");
                            Spanned spanned = Html.fromHtml(Utils.decode(newsDetailsBean.getRes().getContent()));
                            Utils.inSetWebView(spanned.toString(), forum_context, context);
                            if (!newsDetailsBean.getRes().getPl_list().isEmpty()) {
                                if (newsDetailsBean.getRes().getPl_list().size() < 20) {
                                    pinglun_lv.setCanloadMore(false);
                                    pinglun_lv.loadMoreEnd();
                                }
                                adapter = new NewsPLListAdapter(newsDetailsBean.getRes().getPl_list(), context);
                                pinglun_lv.setAdapter(adapter);
                            } else {
                                tv_pinglun.setVisibility(View.GONE);
                            }

                        } else {
                            pinglun_lv.loadMoreComplete();
                            if (!newsDetailsBean.getRes().getPl_list().isEmpty()) {
                                adapter.setDatas((ArrayList) newsDetailsBean.getRes().getPl_list());
                                if (newsDetailsBean.getRes().getPl_list().size() < 20) {
                                    pinglun_lv.setCanloadMore(false);
                                    pinglun_lv.loadMoreEnd();
                                }
                            } else {
                                p = p - 1;
                                pinglun_lv.setCanloadMore(false);
                                pinglun_lv.loadMoreEnd();
                            }
                        }
                    }
                    decode = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
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
        findViewById(R.id.tv_addPinglun).setOnClickListener(this);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_contentMessage = (RelativeLayout) findViewById(R.id.rl_contentMessage);
        ll_Topingjia = (LinearLayout) findViewById(R.id.ll_Topingjia);
        mFlVideoGroup = (FrameLayout) findViewById(R.id.fl_video_group);
        mController = new MediaController(this, true, mFlVideoGroup);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mIvStart = (ImageView) findViewById(R.id.iv_video_start);
        mIvThumbnail = (ImageView) findViewById(R.id.iv_video_thumbnail);
        //上来先隐藏controller
        mController.setVisibility(View.GONE);
        mIvStart.setOnClickListener(this);
        img_back = (ImageView) findViewById(R.id.img_back);
        tv_news_title = (TextView) findViewById(R.id.tv_news_title);
        tv_releaseTime = (TextView) findViewById(R.id.tv_releaseTime);
        tv_view = (TextView) findViewById(R.id.tv_view);
        forum_context = (WebView) findViewById(R.id.forum_context);
        tv_pinglun = (TextView) findViewById(R.id.tv_pinglun);
        pinglun_lv = (LiangDiRecycleView) findViewById(R.id.pinglun_lv);
        sv = (ScrollView) findViewById(R.id.sv);
        img_shoucang = (ImageView) findViewById(R.id.img_shoucang);
        img_share = (ImageView) findViewById(R.id.img_share);
        line = new FullyLinearLayoutManager(context);
        // line.setOrientation(LinearLayoutManager.VERTICAL);
        pinglun_lv.setLayoutManager(line);
        pinglun_lv.setItemAnimator(new DefaultItemAnimator());
        ProgressView progressView = new ProgressView(context);
        progressView.setIndicatorId(ProgressView.BallRotate);
        progressView.setIndicatorColor(getResources().getColor(R.color.colorAccent));
        pinglun_lv.setFootLoadingView(progressView);
        TextView textView = new TextView(context);
        textView.setText("-我也是有底线的-");
        pinglun_lv.setFootEndView(textView);
        pinglun_lv.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                getNews(id);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.iv_video_start:
                mIvStart.setVisibility(View.GONE);
                mIvThumbnail.setVisibility(View.GONE);
                mVideoView.setVideoPath(newsDetailsBean.getRes().getVideo_url());
                mVideoView.setMediaController(mController);
                mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mVideoView.start();
                        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
                    }
                });
                mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //停止播放
                        mVideoView.stopPlayback();
                        mIvStart.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case R.id.tv_addPinglun:
                startActivityForResult(new Intent(context, NewsCommentsActivity.class).putExtra("id", id), commentResult);
                break;
            case R.id.img_shoucang:
                if ("1".equals(newsDetailsBean.getRes().getIs_shou())) {
                    img_shoucang.setBackground(getResources().getDrawable(R.mipmap.new_sc1));
                    newsDetailsBean.getRes().setIs_shou("-1");
                    scDel();
                } else {
                    img_shoucang.setBackground(getResources().getDrawable(R.mipmap.new_sc2));
                    newsDetailsBean.getRes().setIs_shou("1");
                    scAdd();
                }
                break;
            case R.id.img_share:
                showShare();
                break;
            default:
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.addHiddenPlatform(QQ.NAME);
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(newsDetailsBean.getRes().getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(newsDetailsBean.getRes().getUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(newsDetailsBean.getRes().getTitle());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(UrlUtils.URL + getIntent().getStringExtra("shareimg"));//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(newsDetailsBean.getRes().getUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }


    /**
     * 资讯收藏
     */
    private void scAdd() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "news/sc_add", "news/sc_add", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {
                        Toast.makeText(NewsDetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    }
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
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

    /**
     * 资讯收藏
     */
    private void scDel() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "news/sc_del", "news/sc_del", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {
                        Toast.makeText(NewsDetailsActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    }
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == commentResult) {
            String content = data.getStringExtra("content");
            String time = data.getStringExtra("time");
            String img = data.getStringExtra("img");
            if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(time)) {

                try {
                    ArrayList<NewsDetailsBean.ResBean.PlListBean> datas = adapter.getDatas();
                    NewsDetailsBean.ResBean.PlListBean plListBean = new NewsDetailsBean.ResBean.PlListBean();
                    plListBean.setContent(content);
                    plListBean.setAdd_time(time);
                    plListBean.setHead_img(img);
                    plListBean.setId("");
                    plListBean.setNid("");
                    plListBean.setUname(String.valueOf(SpUtil.get(context, "username", "")));
                    datas.add(0, plListBean);
                    adapter.notifyDataSetChanged();
                    newsDetailsBean.getRes().setPl_count(String.valueOf(Integer.parseInt(newsDetailsBean.getRes().getPl_count()) + 1));
                    tv_pinglun.setText(newsDetailsBean.getRes().getPl_count() + "个评论");
                } catch (Exception e) {
                    ArrayList<NewsDetailsBean.ResBean.PlListBean> datas = new ArrayList<>();
                    NewsDetailsBean.ResBean.PlListBean plListBean = new NewsDetailsBean.ResBean.PlListBean();
                    plListBean.setContent(content);
                    plListBean.setAdd_time(time);
                    plListBean.setHead_img(img);
                    plListBean.setId("");
                    plListBean.setNid("");
                    plListBean.setUname(String.valueOf(SpUtil.get(context, "username", "")));
                    datas.add(0, plListBean);
                    newsDetailsBean.getRes().setPl_count(String.valueOf(Integer.parseInt(newsDetailsBean.getRes().getPl_count()) + 1));
                    tv_pinglun.setText(newsDetailsBean.getRes().getPl_count() + "个评论");
                    adapter = new NewsPLListAdapter(datas, context);
                    pinglun_lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        }
    }

    //记得在activity中声明
    // android:screenOrientation="portrait" 强行设置为竖屏，关闭自动旋转屏幕
    //android:configChanges="orientation|keyboardHidden|screenLayout|screenSize"注册配置变化事件
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            mIsFullScreen = true;
            //去掉系统通知栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            hideViews(true);
            //调整mFlVideoGroup布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mFlVideoGroup.setLayoutParams(params);
            //原视频大小
//            public static final int VIDEO_LAYOUT_ORIGIN = 0;
            //最优选择，由于比例问题还是会离屏幕边缘有一点间距，所以最好把父View的背景设置为黑色会好一点
//            public static final int VIDEO_LAYOUT_SCALE = 1;
            //拉伸，可能导致变形
//            public static final int VIDEO_LAYOUT_STRETCH = 2;
            //会放大可能超出屏幕
//            public static final int VIDEO_LAYOUT_ZOOM = 3;
            //效果还是竖屏大小（字面意思是填充父View）
//            public static final int VIDEO_LAYOUT_FIT_PARENT = 4;
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        } else {
            mIsFullScreen = false;
            /*清除flag,恢复显示系统状态栏*/
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            hideViews(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.MATCH_PARENT,
                    PixelUtils.dip2px(this, 220));
            params.leftMargin = PixelUtils.dip2px(this, 5);
            params.rightMargin = PixelUtils.dip2px(this, 5);
            mFlVideoGroup.setLayoutParams(params);
        }
    }

    public void hideViews(boolean hide) {
        if (hide) {
            rl_title.setVisibility(View.GONE);
            tv_news_title.setVisibility(View.GONE);
            rl_contentMessage.setVisibility(View.GONE);
            forum_context.setVisibility(View.GONE);
            tv_pinglun.setVisibility(View.GONE);
            pinglun_lv.setVisibility(View.GONE);
            ll_Topingjia.setVisibility(View.GONE);
        } else {
            rl_title.setVisibility(View.VISIBLE);
            tv_news_title.setVisibility(View.VISIBLE);
            rl_contentMessage.setVisibility(View.VISIBLE);
            forum_context.setVisibility(View.VISIBLE);
            tv_pinglun.setVisibility(View.VISIBLE);
            pinglun_lv.setVisibility(View.VISIBLE);
            ll_Topingjia.setVisibility(View.VISIBLE);
        }
    }

    //没有布局中没有设置返回键，只能响应硬件返回按钮，你可根据自己的意愿添加一个。若全屏就切换为小屏
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mIsFullScreen) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mController.setFullScreenIconState(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
