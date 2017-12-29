package sakura.liangdinvshen.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sakura.liangdinvshen.Adapter.NewsPageAdapter;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Bean.LifeUserSlqBean;
import sakura.liangdinvshen.Bean.NewsIndexBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.DateUtils;
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
    public void onResume() {
        super.onResume();
        getCache();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //阻止activity保存fragment的状态
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initView(View view) {
        this.vp = (ViewPager) view.findViewById(R.id.VpNews_context);
        this.ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        String uid = (String) SpUtil.get(getActivity(), "uid", "");
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_now_days = (TextView) view.findViewById(R.id.tv_now_days);
        tv_stu_title = (TextView) view.findViewById(R.id.tv_stu_title);
        tv_yun_lv = (TextView) view.findViewById(R.id.tv_yun_lv);
        sb_nofade = (SwitchButton) view.findViewById(R.id.sb_nofade);
        sb_nofade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity(), "isChecked:" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        if (!TextUtils.isEmpty(uid)) {
            getIndex(uid);
        }

        String jieduan = (String) SpUtil.get(getActivity(), "jieduan", "");
        if (!TextUtils.isEmpty(jieduan)) {
            if ("1".equals(jieduan)) {
                sb_nofade.setChecked(true);
            }
        }

    }

    private void getCache() {
        String index = (String) SpUtil.get(getActivity(), "index", "");
        if (!TextUtils.isEmpty(index)) {
            try {
                NewsIndexBean newsIndexBean = new Gson().fromJson(index, NewsIndexBean.class);
                //头部展示数据处理
                tv_name.setText(newsIndexBean.getYun().getName());
                tv_now_days.setText(String.valueOf(newsIndexBean.getYun().getNow_days()));
                tv_stu_title.setText("(" + newsIndexBean.getYun().getStu_title() + ")");
                tv_yun_lv.setText("怀孕几率" + String.valueOf(newsIndexBean.getYun().getYun_lv()));
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
                Toast.makeText(getActivity(), getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        } else {

        }

    }

    /**
     * 首页信息获取
     */
    private void getIndex(String uid) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", uid);
        VolleyRequest.RequestPost(getActivity(), UrlUtils.BASE_URL + "index/index", "index/index", params, new VolleyInterface(getActivity()) {
            @Override
            public void onMySuccess(String result) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                String decode = result;
                Log.e("NewsFragment", decode);
                try {
                    NewsIndexBean newsIndexBean = new Gson().fromJson(decode, NewsIndexBean.class);
                    //头部展示数据处理
                    tv_name.setText(newsIndexBean.getYun().getName());
                    tv_now_days.setText(String.valueOf(newsIndexBean.getYun().getNow_days()));
                    tv_stu_title.setText("(" + newsIndexBean.getYun().getStu_title() + ")");
                    tv_yun_lv.setText("怀孕几率" + String.valueOf(newsIndexBean.getYun().getYun_lv()));
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
    public void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("index/index");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sb_nofade:

                break;
        }
    }
}
