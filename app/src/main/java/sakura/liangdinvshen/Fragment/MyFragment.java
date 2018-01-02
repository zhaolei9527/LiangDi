package sakura.liangdinvshen.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;

import sakura.liangdinvshen.Activity.AddressActivitry;
import sakura.liangdinvshen.Activity.BankCardActivity;
import sakura.liangdinvshen.Activity.CollectionActivity;
import sakura.liangdinvshen.Activity.EmployeesActivity;
import sakura.liangdinvshen.Activity.FinancialActiivity;
import sakura.liangdinvshen.Activity.IntegralActiivity;
import sakura.liangdinvshen.Activity.MessageActivity;
import sakura.liangdinvshen.Activity.MyOrderActivity;
import sakura.liangdinvshen.Activity.MyPersonalDataActivity;
import sakura.liangdinvshen.Activity.MyServiceActivity;
import sakura.liangdinvshen.Activity.PhaseActivity;
import sakura.liangdinvshen.Activity.RecommendActivity;
import sakura.liangdinvshen.Activity.ReturnPriceListActivity;
import sakura.liangdinvshen.Activity.SettingActivity;
import sakura.liangdinvshen.Activity.ShopCarActivity;
import sakura.liangdinvshen.Activity.WithdrawalActivity;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Bean.UserInfoBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * Created by 赵磊 on 2017/9/19.
 */

public class MyFragment extends Fragment implements View.OnClickListener {
    private LinearLayout mLlQiandao;
    private SimpleDraweeView mSdvTouxiang;
    private TextView mTvName;
    private TextView mTvJieduan;
    private TextView mTvJifen;
    private TextView mTvYue;
    private LinearLayout mLlDingdan;
    private LinearLayout mLlShopcar;
    private LinearLayout mLlShoucang;
    private ImageView mImgDot;
    private LinearLayout mLlXiaoxi;
    private ImageView mImg1;
    private RelativeLayout mRlMyService;
    private ImageView mImg2;
    private RelativeLayout mRlJieduan;
    private ImageView mImg3;
    private RelativeLayout mRlZiliao;
    private ImageView mImg4;
    private RelativeLayout mRlAddress;
    private ImageView mImg5;
    private RelativeLayout mRlTuihuan;
    private ImageView mImg6;
    private RelativeLayout mRlTujian;
    private ImageView mImg7;
    private RelativeLayout mRlCaiwu;
    private ImageView mImg8;
    private RelativeLayout mRlJifen;
    private ImageView mImg9;
    private RelativeLayout mRlYinhangka;
    private ImageView mImg10;
    private RelativeLayout mRlTixian;
    private ImageView mImg11;
    private RelativeLayout mRlSetting;
    private ImageView mImg12;
    private RelativeLayout mRlWangdian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mLlQiandao = (LinearLayout) view.findViewById(R.id.ll_qiandao);
        mSdvTouxiang = (SimpleDraweeView) view.findViewById(R.id.sdv_touxiang);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvJieduan = (TextView) view.findViewById(R.id.tv_jieduan);
        mTvJifen = (TextView) view.findViewById(R.id.tv_jifen);
        mTvYue = (TextView) view.findViewById(R.id.tv_yue);
        mLlDingdan = (LinearLayout) view.findViewById(R.id.ll_dingdan);
        mLlShopcar = (LinearLayout) view.findViewById(R.id.ll_shopcar);
        mLlShoucang = (LinearLayout) view.findViewById(R.id.ll_shoucang);
        mImgDot = (ImageView) view.findViewById(R.id.img_dot);
        mLlXiaoxi = (LinearLayout) view.findViewById(R.id.ll_xiaoxi);
        mImg1 = (ImageView) view.findViewById(R.id.img1);
        mRlMyService = (RelativeLayout) view.findViewById(R.id.rl_myService);
        mImg2 = (ImageView) view.findViewById(R.id.img2);
        mRlJieduan = (RelativeLayout) view.findViewById(R.id.rl_jieduan);
        mImg3 = (ImageView) view.findViewById(R.id.img3);
        mRlZiliao = (RelativeLayout) view.findViewById(R.id.rl_ziliao);
        mImg4 = (ImageView) view.findViewById(R.id.img4);
        mRlAddress = (RelativeLayout) view.findViewById(R.id.rl_address);
        mImg5 = (ImageView) view.findViewById(R.id.img5);
        mRlTuihuan = (RelativeLayout) view.findViewById(R.id.rl_tuihuan);
        mImg6 = (ImageView) view.findViewById(R.id.img6);
        mRlTujian = (RelativeLayout) view.findViewById(R.id.rl_tujian);
        mImg7 = (ImageView) view.findViewById(R.id.img7);
        mRlCaiwu = (RelativeLayout) view.findViewById(R.id.rl_caiwu);
        mImg8 = (ImageView) view.findViewById(R.id.img8);
        mRlJifen = (RelativeLayout) view.findViewById(R.id.rl_jifen);
        mImg9 = (ImageView) view.findViewById(R.id.img9);
        mRlYinhangka = (RelativeLayout) view.findViewById(R.id.rl_yinhangka);
        mImg10 = (ImageView) view.findViewById(R.id.img10);
        mRlTixian = (RelativeLayout) view.findViewById(R.id.rl_tixian);
        mImg11 = (ImageView) view.findViewById(R.id.img11);
        mRlSetting = (RelativeLayout) view.findViewById(R.id.rl_setting);
        mImg12 = (ImageView) view.findViewById(R.id.img12);

        mRlWangdian = (RelativeLayout) view.findViewById(R.id.rl_wangdian);


        String img = (String) SpUtil.get(getActivity(), "img", "");


        if (img.startsWith("http://")) {
            mSdvTouxiang.setImageURI(img);
        } else {
            mSdvTouxiang.setImageURI(UrlUtils.URL + String.valueOf(SpUtil.get(getActivity(), "img", "")));
        }

        mTvName.setText(String.valueOf(SpUtil.get(getActivity(), "username", "")));
        mTvJifen.setText("积分" + String.valueOf(SpUtil.get(getActivity(), "jifen", "0")));
        mTvYue.setText("余额￥" + String.valueOf(SpUtil.get(getActivity(), "money", "")));

        mRlMyService.setOnClickListener(this);
        mLlDingdan.setOnClickListener(this);
        mRlZiliao.setOnClickListener(this);
        mRlJieduan.setOnClickListener(this);
        mRlAddress.setOnClickListener(this);
        mRlTuihuan.setOnClickListener(this);
        mLlShopcar.setOnClickListener(this);
        mLlShoucang.setOnClickListener(this);
        mLlXiaoxi.setOnClickListener(this);
        mRlTujian.setOnClickListener(this);
        mRlCaiwu.setOnClickListener(this);
        mRlJifen.setOnClickListener(this);
        mRlYinhangka.setOnClickListener(this);
        mRlTixian.setOnClickListener(this);
        mRlSetting.setOnClickListener(this);
        mRlWangdian.setOnClickListener(this);
        mRlWangdian.setVisibility(View.GONE);
        String role = (String) SpUtil.get(getActivity(), "Role", "");
        if (!"1".equals(role)) {
            mRlWangdian.setVisibility(View.VISIBLE);
        } else {
            mRlWangdian.setVisibility(View.GONE);
        }


        String jieduan = (String) SpUtil.get(getActivity(), "jieduan", "");
        if (!TextUtils.isEmpty(jieduan)) {
            if ("1".equals(jieduan)) {
                mTvJieduan.setText("人生阶段：只记经期");
            } else if ("2".equals(jieduan)) {
                mTvJieduan.setText("人生阶段：备孕期");
            } else if ("3".equals(jieduan)) {
                mTvJieduan.setText("人生阶段：我怀孕了");
            } else if ("4".equals(jieduan)) {
                mTvJieduan.setText("人生阶段：我是辣妈");
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        userInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_tixian:
                startActivity(new Intent(getContext(), WithdrawalActivity.class));
                break;
            case R.id.rl_yinhangka:
                startActivity(new Intent(getContext(), BankCardActivity.class));
                break;
            case R.id.rl_jifen:
                startActivity(new Intent(getContext(), IntegralActiivity.class));
                break;
            case R.id.rl_caiwu:
                startActivity(new Intent(getContext(), FinancialActiivity.class));
                break;
            case R.id.rl_tujian:
                startActivity(new Intent(getContext(), RecommendActivity.class));
                break;
            case R.id.rl_myService:
                startActivity(new Intent(getContext(), MyServiceActivity.class));
                break;
            case R.id.ll_dingdan:
                startActivity(new Intent(getContext(), MyOrderActivity.class));
                break;
            case R.id.rl_ziliao:
                startActivity(new Intent(getContext(), MyPersonalDataActivity.class));
                break;
            case R.id.rl_jieduan:
                startActivity(new Intent(getContext(), PhaseActivity.class));
                break;
            case R.id.rl_address:
                startActivity(new Intent(getContext(), AddressActivitry.class));
                break;
            case R.id.rl_tuihuan:
                startActivity(new Intent(getContext(), ReturnPriceListActivity.class));
                break;
            case R.id.ll_shopcar:
                startActivity(new Intent(getContext(), ShopCarActivity.class));
                break;
            case R.id.ll_shoucang:
                startActivity(new Intent(getContext(), CollectionActivity.class));
                break;
            case R.id.ll_xiaoxi:
                startActivity(new Intent(getContext(), MessageActivity.class));
                break;
            case R.id.rl_setting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.rl_wangdian:
                startActivity(new Intent(getContext(), EmployeesActivity.class));
                break;
            default:
                break;

        }
    }

    /**
     * 用户信息获取
     */
    private void userInfo() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(getActivity(), "uid", "")));
        VolleyRequest.RequestPost(getActivity(), UrlUtils.BASE_URL + "user/info", "user/info", params, new VolleyInterface(getActivity()) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    UserInfoBean userInfoBean = new Gson().fromJson(result, UserInfoBean.class);
                    if ("1".equals(String.valueOf(userInfoBean.getCode()))) {
                        SpUtil.putAndApply(getActivity(), "img", userInfoBean.getList().getImg());
                        SpUtil.putAndApply(getActivity(), "account", userInfoBean.getList().getTel());
                        SpUtil.putAndApply(getActivity(), "username", userInfoBean.getList().getNi_name());
                        SpUtil.putAndApply(getActivity(), "jifen", userInfoBean.getList().getJifen());
                        SpUtil.putAndApply(getActivity(), "money", userInfoBean.getList().getMoney());
                        SpUtil.putAndApply(getActivity(), "Role", userInfoBean.getList().getRole());
                        SpUtil.putAndApply(getActivity(), "jieduan", userInfoBean.getList().getStu());

                        String img = (String) SpUtil.get(getActivity(), "img", "");

                        if (img.startsWith("http://")) {
                            mSdvTouxiang.setImageURI(img);
                        } else {
                            mSdvTouxiang.setImageURI(UrlUtils.URL + String.valueOf(SpUtil.get(getActivity(), "img", "")));
                        }


                        mTvName.setText(String.valueOf(SpUtil.get(getActivity(), "username", "")));
                        mTvJifen.setText("积分" + String.valueOf(SpUtil.get(getActivity(), "jifen", "0")));
                        mTvYue.setText("余额￥" + String.valueOf(SpUtil.get(getActivity(), "money", "")));

                        String jieduan = (String) SpUtil.get(getActivity(), "jieduan", "");
                        if (!TextUtils.isEmpty(jieduan)) {
                            if ("1".equals(jieduan)) {
                                mTvJieduan.setText("人生阶段：只记经期");
                            } else if ("2".equals(jieduan)) {
                                mTvJieduan.setText("人生阶段：备孕期");
                            } else if ("3".equals(jieduan)) {
                                mTvJieduan.setText("人生阶段：我怀孕了");
                            } else if ("4".equals(jieduan)) {
                                mTvJieduan.setText("人生阶段：我是辣妈");
                            }
                        }
                        String role = (String) SpUtil.get(getActivity(), "Role", "");
                        if (!"1".equals(role)) {
                            mRlWangdian.setVisibility(View.VISIBLE);
                        } else {
                            mRlWangdian.setVisibility(View.GONE);
                        }

                    }
                    userInfoBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("user/info");
    }
}
