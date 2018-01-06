package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.Fragment.RecordFragment;
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
 * @date 2017/12/27
 * 功能描述：
 */
public class YJxiangqingActivity extends BaseActivity {

    private FrameLayout rl_back;
    private ImageView img_tongjing;
    private TextView tv_tongjing;
    private RelativeLayout rl_tonging;
    private ImageView img_rl_liuliang;
    private TextView tv_liuliang;
    private RelativeLayout rl_liuliang;
    private ImageView img_xuese;
    private RelativeLayout rl_xuese;
    private ImageView img_jingxie;
    private RelativeLayout rl_jingxie;

    ArrayList<String> tongjingList = new ArrayList<>();
    ArrayList<String> liuliangList = new ArrayList<>();
    ArrayList<String> xueseList = new ArrayList<>();
    ArrayList<String> jingxieList = new ArrayList<>();
    private TextView tv_jingxie;
    private TextView tv_xuese;
    private TextView tv_submit;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.activity_tjxiangqing;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        img_tongjing = (ImageView) findViewById(R.id.img_tongjing);
        tv_tongjing = (TextView) findViewById(R.id.tv_tongjing);
        tv_jingxie = (TextView) findViewById(R.id.tv_jingxie);
        tv_xuese = (TextView) findViewById(R.id.tv_xuese);
        rl_tonging = (RelativeLayout) findViewById(R.id.rl_tonging);
        img_rl_liuliang = (ImageView) findViewById(R.id.img_rl_liuliang);
        tv_liuliang = (TextView) findViewById(R.id.tv_liuliang);
        rl_liuliang = (RelativeLayout) findViewById(R.id.rl_liuliang);
        img_xuese = (ImageView) findViewById(R.id.img_xuese);
        rl_xuese = (RelativeLayout) findViewById(R.id.rl_xuese);
        img_jingxie = (ImageView) findViewById(R.id.img_jingxie);
        rl_jingxie = (RelativeLayout) findViewById(R.id.rl_jingxie);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        rl_jingxie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("经血", "4", jingxieList);
            }
        });

        rl_liuliang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("流量", "2", liuliangList);
            }
        });

        rl_tonging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("痛经", "1", tongjingList);
            }
        });

        rl_xuese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("血色", "3", xueseList);
            }
        });


        tv_submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(tv_jingxie.getText())) {
                    EasyToast.showShort(context, "请选择经血");
                    return;
                }
                if (TextUtils.isEmpty(tv_tongjing.getText())) {
                    EasyToast.showShort(context, "请选择痛经");
                    return;
                }
                if (TextUtils.isEmpty(tv_xuese.getText())) {
                    EasyToast.showShort(context, "请选择血色");
                    return;
                }
                if (TextUtils.isEmpty(tv_liuliang.getText())) {
                    EasyToast.showShort(context, "请选择流量");
                    return;
                }

                if (Utils.isConnected(context)) {
                    dialog = Utils.showLoadingDialog(context);
                    dialog.show();
                    EventBus.getDefault().post(
                            new BankEvent(tv_jingxie.getText().toString() + "," + tv_liuliang.getText().toString() + "," + tv_xuese.getText().toString() + "," + tv_tongjing.getText().toString()));
                    lifeDoPeriodDetail(tv_tongjing.getText().toString(), tv_liuliang.getText().toString(), tv_xuese.getText().toString(), tv_jingxie.getText().toString());
                } else {
                    EasyToast.showShort(context, "网络未连接");
                }


            }
        });

    }

    @Override
    protected void initData() {
        tongjingList.add("轻度");
        tongjingList.add("中度");
        tongjingList.add("重度");
        liuliangList.add("很少");
        liuliangList.add("较少");
        liuliangList.add("中等");
        liuliangList.add("较多");
        liuliangList.add("很多");
        xueseList.add("很浅");
        xueseList.add("较浅");
        xueseList.add("中等");
        xueseList.add("较深");
        xueseList.add("很深");
        jingxieList.add("正常");
        jingxieList.add("血块");
        jingxieList.add("异味");
        jingxieList.add("渣状");
    }

    private void ShowPickerView(String TITLE, final String type, ArrayList<String> list) {// 弹出选择器
        if (!tongjingList.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if ("1".equals(type)) {
                        tv_tongjing.setText(tongjingList.get(options1));
                    } else if ("2".equals(type)) {
                        tv_liuliang.setText(liuliangList.get(options1));
                    } else if ("3".equals(type)) {
                        tv_xuese.setText(xueseList.get(options1));
                    } else if ("4".equals(type)) {
                        tv_jingxie.setText(jingxieList.get(options1));
                    }
                }
            })
                    .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                    .setCancelColor(getResources().getColor(R.color.text))
                    .setSubmitColor(getResources().getColor(R.color.text))
                    .setTitleText(TITLE)
                    .setSelectOptions(0)
                    .setTitleColor(getResources().getColor(R.color.text))
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(list);//三级选择器
            pvOptions.show();
        }
    }

    /**
     * 经期详情
     */
    private void lifeDoPeriodDetail(String tongjing, String liuliang, String xuese, String jingxue) {
        HashMap<String, String> params = new HashMap<>(7);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        params.put("tongjing", tongjing);
        params.put("liuliang", liuliang);
        params.put("xuese", xuese);
        params.put("jingxue", jingxue);
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "life/do_period_detail", "life/do_period_detail", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                dialog.dismiss();
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {

                    } else {
                        EasyToast.showShort(context, "提交失败");
                    }
                    stuBean = null;
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
