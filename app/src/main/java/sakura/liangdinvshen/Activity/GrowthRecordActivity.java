package sakura.liangdinvshen.Activity;

import android.graphics.Color;
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
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/29
 * 功能描述：
 */
public class GrowthRecordActivity extends BaseActivity {

    private FrameLayout rl_back;
    private ImageView img_shengao;
    private TextView tv_shengao;
    private RelativeLayout rl_start_time;
    private ImageView img_rl_liuliang;
    private TextView tv_tizhong;
    private RelativeLayout rl_tizhong;
    private ImageView img_xuese;
    private TextView tv_touwei;
    private RelativeLayout rl_touwei;

    ArrayList<String> shengaoList = new ArrayList<>();
    ArrayList<String> tizhongList = new ArrayList<>();
    ArrayList<String> touweilist = new ArrayList<>();
    ArrayList<String> shengaominList = new ArrayList<>();
    ArrayList<String> tizhongminList = new ArrayList<>();
    ArrayList<String> touweiminlist = new ArrayList<>();

    private int maxshengao = 151;
    private int maxtouwei = 61;
    private int maxtizhong = 41;
    private int mintizhong = 2;
    private int minshengao = 40;
    private int mintouwei = 3;
    private int min = 11;

    @Override
    protected int setthislayout() {
        return R.layout.activity_growthrecord;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        img_shengao = (ImageView) findViewById(R.id.img_shengao);
        tv_shengao = (TextView) findViewById(R.id.tv_shengao);
        rl_start_time = (RelativeLayout) findViewById(R.id.rl_start_time);
        img_rl_liuliang = (ImageView) findViewById(R.id.img_rl_liuliang);
        tv_tizhong = (TextView) findViewById(R.id.tv_tizhong);
        rl_tizhong = (RelativeLayout) findViewById(R.id.rl_tizhong);
        img_xuese = (ImageView) findViewById(R.id.img_xuese);
        tv_touwei = (TextView) findViewById(R.id.tv_touwei);
        rl_touwei = (RelativeLayout) findViewById(R.id.rl_touwei);
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rl_tizhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("体重", "2", tizhongList, tizhongminList);
            }
        });

        rl_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("身高", "1", shengaoList, shengaominList);
            }
        });

        rl_touwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView("头围", "3", touweilist, touweiminlist);
            }
        });

    }

    private void ShowPickerView(String TITLE, final String type, final ArrayList<String> list, final ArrayList<String> list2) {// 弹出选择器
        if (!list.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if ("1".equals(type)) {
                        tv_shengao.setText(list.get(options1) + list2.get(options2));
                    } else if ("2".equals(type)) {
                        tv_tizhong.setText(list.get(options1) + list2.get(options2));
                    } else if ("3".equals(type)) {
                        tv_touwei.setText(list.get(options1) + list2.get(options2));
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
            pvOptions.setNPicker(list, list2, null);//三级选择器
            pvOptions.show();
        }
    }

    @Override
    protected void initData() {

        for (int i = minshengao; i < maxshengao; i++) {
            shengaoList.add(String.valueOf(i));
        }

        for (int i = mintizhong; i < maxtizhong; i++) {
            tizhongList.add(String.valueOf(i));
        }

        for (int i = mintouwei; i < maxtouwei; i++) {
            touweilist.add(String.valueOf(i));
        }

        for (int i = 0; i < min; i++) {
            shengaominList.add("." + String.valueOf(i) + "cm");
            touweiminlist.add("." + String.valueOf(i) + "cm");
            tizhongminList.add("." + String.valueOf(i) + "kg");
        }

    }

    /**
     * 生长记录
     */
    private void suckleDoGrow(String height, String weight, String head) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("height", height);
        params.put("weight", weight);
        params.put("head", head);
        params.put("time", RecordFragment.currentDate.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "suckle/do_grow", "suckle/do_grow", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                    } else {
                        EasyToast.showShort(context, "提交失败");
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
    protected void onPause() {
        super.onPause();
        suckleDoGrow(tv_shengao.getText().toString(), tv_tizhong.getText().toString(), tv_touwei.getText().toString());
        EventBus.getDefault().post(
                new BankEvent(tv_shengao.getText().toString() + tv_tizhong.getText().toString() + tv_touwei.getText().toString(), "chengzhang"));
    }
}
