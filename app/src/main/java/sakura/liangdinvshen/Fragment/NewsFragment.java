package sakura.liangdinvshen.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.astuetz.PagerSlidingTabStrip;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sakura.liangdinvshen.Activity.LoginActivity;
import sakura.liangdinvshen.Adapter.NewsPageAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Bean.NewsIndexBean;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * Created by 赵磊 on 2017/9/19.
 */

public class NewsFragment extends Fragment implements View.OnClickListener {
    private ViewPager vp;
    private NewsPageAdapter adapter;
    public static LinearLayout ll_head;
    private List titles = new ArrayList();
    private List titleid = new ArrayList();
    private PagerSlidingTabStrip tabs;
    private TextView tv_name;
    private TextView tv_now_days;
    private TextView tv_stu_title;
    private LinearLayout banner_line1;
    private TextView tv_yun_lv;
    private ImageView img_jing;
    private SwitchButton sb_nofade;
    private ViewPager VpNews_context;
    private Dialog dialog;
    private RelativeLayout rl_isyuejing;
    private SimpleDraweeView simpleDraweeView;
    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dialog = Utils.showLoadingDialog(getActivity());
        dialog.dismiss();
        View view = inflater.inflate(R.layout.news_fragment_layout, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        uid = (String) SpUtil.get(getActivity(), "uid", "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("index/index");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //阻止activity保存fragment的状态
        //super.onSaveInstanceState(outState);
    }

    private void initView(View view) {
        this.vp = (ViewPager) view.findViewById(R.id.VpNews_context);
        this.ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
        rl_isyuejing = (RelativeLayout) view.findViewById(R.id.rl_isyuejing);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        uid = (String) SpUtil.get(getActivity(), "uid", "");
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_now_days = (TextView) view.findViewById(R.id.tv_now_days);
        tv_stu_title = (TextView) view.findViewById(R.id.tv_stu_title);
        tv_yun_lv = (TextView) view.findViewById(R.id.tv_yun_lv);
        sb_nofade = (SwitchButton) view.findViewById(R.id.sb_nofade);
        simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.SimpleDraweeView);

        if (!TextUtils.isEmpty(uid)) {
            getCache(uid);
            getIndex(uid);
        } else {
            getCache("");
            getIndex("");
        }

        String jieduan = (String) SpUtil.get(getActivity(), "jieduan", "");
        if (!TextUtils.isEmpty(jieduan)) {
            if ("1".equals(jieduan)) {
            } else if ("2".equals(jieduan)) {
            } else if ("3".equals(jieduan)) {
                rl_isyuejing.setVisibility(View.GONE);
            } else if ("4".equals(jieduan)) {
            }
        }

        /**
         * 是否经期
         * */
        sb_nofade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(uid)) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    EasyToast.showShort(getContext(), "请先登录");
                    sb_nofade.setChecked(false);
                } else {
                    if (sb_nofade.isChecked()) {
                        lifePeriodStart();
                    } else {
                        lifePeriodEnd();
                    }
                }
            }
        });

    }

    /**
     * 经期开始
     */
    @Nullable
    private void lifePeriodStart() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(getActivity(), "uid", "")));
        params.put("time", DateUtils.getDay(System.currentTimeMillis()));
        VolleyRequest.RequestPost(getActivity(), UrlUtils.BASE_URL + "life/period_start", "life/period_start", params, new VolleyInterface(getActivity()) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if (!"1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(getActivity(), "操作失败");
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getActivity().getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), getActivity().getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 经期结束
     */
    private void lifePeriodEnd() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(getActivity(), "uid", "")));
        params.put("time", DateUtils.getDay(System.currentTimeMillis()));
        VolleyRequest.RequestPost(getActivity(), UrlUtils.BASE_URL + "life/period_end", "life/period_end", params, new VolleyInterface(getActivity()) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if (!"1".equals(String.valueOf(stuBean.getStu()))) {
                        EasyToast.showShort(getActivity(), "操作失败");
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getActivity().getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), getActivity().getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getCache(final String uid) {
        String index = (String) SpUtil.get(getActivity(), "index", "");
        if (!TextUtils.isEmpty(index)) {
            try {
                NewsIndexBean newsIndexBean = new Gson().fromJson(index, NewsIndexBean.class);
                //头部展示数据处理
                if (TextUtils.isEmpty(uid)) {
                    tv_name.setText("");
                    tv_now_days.setText("");
                    tv_stu_title.setText("");
                    tv_yun_lv.setText("");
                    sb_nofade.setChecked(false);
                } else {
                    tv_name.setText(newsIndexBean.getYun().getName());
                    tv_now_days.setText("第" + String.valueOf(newsIndexBean.getYun().getNow_days()) + "天");
                    tv_stu_title.setText("(" + newsIndexBean.getYun().getStu_title() + ")");
                    tv_yun_lv.setText("怀孕几率" + String.valueOf(newsIndexBean.getYun().getYun_lv()));
                    if ("1".equals(newsIndexBean.getYun().getIs_yuejing())) {
                        sb_nofade.setChecked(true);
                    } else {
                        sb_nofade.setChecked(false);
                    }
                }
                simpleDraweeView.setImageURI(UrlUtils.URL + newsIndexBean.getTopimg());

                //新闻分类处理
                List<NewsIndexBean.CateBean> cate = newsIndexBean.getCate();
                titles.clear();
                titleid.clear();
                for (int i = 0; i < cate.size(); i++) {
                    titles.add(cate.get(i).getCate_name());
                    titleid.add(cate.get(i).getId());
                }
                if (adapter == null) {
                    adapter = new NewsPageAdapter(getChildFragmentManager(), getActivity(), titles, titleid);
                    vp.setAdapter(adapter);
                    tabs.setViewPager(vp);
                }
                vp.requestLayout();
                vp.setCurrentItem(0);
                cate = null;
                newsIndexBean = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    /**
     * 首页信息获取
     */
    private void getIndex(final String uid) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", uid);
        Log.e("NewsFragment", params.toString());
        VolleyRequest.RequestPost(getActivity(), UrlUtils.BASE_URL + "index/index", "index/index", params, new VolleyInterface(getActivity()) {
            @Override
            public void onMySuccess(String result) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                String decode = result;
                Log.e("RegisterActivity", decode);
                try {
                    NewsIndexBean newsIndexBean = new Gson().fromJson(decode, NewsIndexBean.class);
                    //头部展示数据处理

                    if (TextUtils.isEmpty(uid)) {
                        tv_name.setText("");
                        tv_now_days.setText("");
                        tv_stu_title.setText("");
                        tv_yun_lv.setText("");
                        sb_nofade.setChecked(false);
                    } else {
                        tv_name.setText(newsIndexBean.getYun().getName());
                        tv_now_days.setText("第" + String.valueOf(newsIndexBean.getYun().getNow_days()) + "天");
                        tv_stu_title.setText("(" + newsIndexBean.getYun().getStu_title() + ")");
                        tv_yun_lv.setText("怀孕几率" + String.valueOf(newsIndexBean.getYun().getYun_lv()));
                        if ("1".equals(newsIndexBean.getYun().getIs_yuejing())) {
                            sb_nofade.setChecked(true);
                        } else {
                            sb_nofade.setChecked(false);
                        }
                    }

                    simpleDraweeView.setImageURI(UrlUtils.URL + newsIndexBean.getTopimg());

                    //新闻分类处理
                    List<NewsIndexBean.CateBean> cate = newsIndexBean.getCate();
                    titles.clear();
                    titleid.clear();
                    for (int i = 0; i < cate.size(); i++) {
                        titles.add(cate.get(i).getCate_name());
                        titleid.add(cate.get(i).getId());
                    }
                    if (adapter == null) {
                        adapter = new NewsPageAdapter(getChildFragmentManager(), getActivity(), titles, titleid);
                        vp.setAdapter(adapter);
                    } else {
                        adapter = new NewsPageAdapter(getChildFragmentManager(), getActivity(), titles, titleid);
                        vp.setAdapter(adapter);
                    }
                    tabs.setViewPager(vp);
                    //缓存首页数据
                    SpUtil.putAndApply(getActivity(), "index", decode);
                    cate = null;
                    decode = null;
                    newsIndexBean = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                error.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
