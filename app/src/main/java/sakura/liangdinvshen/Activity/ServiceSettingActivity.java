package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.CodeBean;
import sakura.liangdinvshen.Bean.JsonBean;
import sakura.liangdinvshen.Bean.WangWdDetailBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.GetJsonDataUtil;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/26
 * 功能描述：
 */
public class ServiceSettingActivity extends BaseActivity {


    private FrameLayout rl_back;
    private TextView tv_add;
    private EditText et_name;
    private EditText et_adddress;
    private TextView tv_phone;
    private TextView tv_lianxiname;
    private TextView tv_address;
    private Thread thread;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    break;
                default:
                    break;

            }
        }
    };
    private boolean isLoaded = false;
    private String province;
    private String city;
    private String country;
    private Dialog dialog;

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    private void ShowPickerView() {// 弹出选择器
        if (!options1Items.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    province = options1Items.get(options1).getPickerViewText();
                    city = options2Items.get(options1).get(options2);
                    country = options3Items.get(options1).get(options2).get(options3);
                    String tx = options1Items.get(options1).getPickerViewText() +
                            options2Items.get(options1).get(options2) +
                            options3Items.get(options1).get(options2).get(options3);
                    tv_address.setText(tx);
                }
            })
                    .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                    .setCancelColor(getResources().getColor(R.color.text))
                    .setSubmitColor(getResources().getColor(R.color.text))
                    .setTitleText("选择城市")
                    .setSelectOptions(15)//默认选中项
                    .setTitleColor(getResources().getColor(R.color.text))
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
            pvOptions.show();
        }

    }


    @Override
    protected int setthislayout() {
        return R.layout.activity_servicesetting;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        tv_add = (TextView) findViewById(R.id.tv_add);
        et_name = (EditText) findViewById(R.id.et_name);
        et_adddress = (EditText) findViewById(R.id.et_adddress);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_lianxiname = (TextView) findViewById(R.id.tv_lianxiname);
        tv_address = (TextView) findViewById(R.id.tv_address);
    }

    @Override
    protected void initListener() {

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPickerView();
            }
        });
    }

    @Override
    protected void initData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            wangWdDetail();
        } else {
            EasyToast.showShort(context, "网络未连接");
            finish();
        }
    }

    private void submit() {
        // validate
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入网点名称", Toast.LENGTH_SHORT).show();
            return;
        }

        String adddress = et_adddress.getText().toString().trim();
        if (TextUtils.isEmpty(adddress)) {
            Toast.makeText(this, "请输入网点地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Utils.isConnected(context)) {
            dialog.show();
            wangWddetail();
        } else {
            EasyToast.showShort(context, "网络未连接");
            finish();
        }
    }

    /**
     * 服务基本设置获取
     */
    private void wangWdDetail() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "wang/wd_detail", "wang/wd_detail", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    WangWdDetailBean wangWdDetailBean = new Gson().fromJson(result, WangWdDetailBean.class);
                    if ("1".equals(String.valueOf(wangWdDetailBean.getCode()))) {
                        et_name.setText(wangWdDetailBean.getRes().getTitle());
                        et_adddress.setText(wangWdDetailBean.getRes().getAddress());
                        tv_phone.setText(wangWdDetailBean.getRes().getLx_tel());
                        tv_lianxiname.setText(wangWdDetailBean.getRes().getLx_name());
                        tv_address.setText(wangWdDetailBean.getRes().getProvince() + wangWdDetailBean.getRes().getCity() + wangWdDetailBean.getRes().getCountry());
                        province = wangWdDetailBean.getRes().getProvince();
                        city = wangWdDetailBean.getRes().getCity();
                        country = wangWdDetailBean.getRes().getCountry();
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
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 服务基本设置
     */
    private void wangWddetail() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("name", et_name.getText().toString());
        params.put("address", et_adddress.getText().toString());
        params.put("lx_tel", tv_phone.getText().toString());
        params.put("lx_name", tv_lianxiname.getText().toString());
        params.put("province", province);
        params.put("city", city);
        params.put("country", country);
        Log.e("RegisterActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "wang/wdedit", "wang/wdedit", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {
                        EasyToast.showShort(context, "网店设置成功");
                        finish();
                    } else {
                        EasyToast.showShort(context, codeBean.getMsg());
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
                dialog.dismiss();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
