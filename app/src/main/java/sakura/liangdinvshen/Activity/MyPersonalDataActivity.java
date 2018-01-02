package sakura.liangdinvshen.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hss01248.frescopicker.FrescoIniter;

import net.bither.util.NativeUtil;

import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.JsonBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.GetJsonDataUtil;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.View.ChangeNameDialog;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;
import sakura.liangdinvshen.other.PriorityRunnable;

import static sakura.liangdinvshen.App.pausableThreadPoolExecutor;
import static sakura.liangdinvshen.R.style.dialog;

public class MyPersonalDataActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout rl_back;
    private ImageView img_goto;
    private SimpleDraweeView SimpleDraweeView;
    private RelativeLayout rl_change_touxiang;
    private TextView tv_name;
    private RelativeLayout rl_change_name;
    private RelativeLayout rl_change_shengri;
    private RelativeLayout r_change_shengao;
    private RelativeLayout rl_change_hunyin;
    private TextView textView;
    private RelativeLayout rl_change_address;
    private TimePickerView pvTime;
    private TextView tv_shengri;
    private TextView tv_shengao;
    private TextView tv_hunyin;
    private TextView tv_city;

    @Override
    protected int setthislayout() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        img_goto = (ImageView) findViewById(R.id.img_goto);
        SimpleDraweeView = (SimpleDraweeView) findViewById(R.id.SimpleDraweeView);
        rl_change_touxiang = (RelativeLayout) findViewById(R.id.rl_change_touxiang);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_shengri = (TextView) findViewById(R.id.tv_shengri);
        tv_shengao = (TextView) findViewById(R.id.tv_shengao);
        tv_hunyin = (TextView) findViewById(R.id.tv_hunyin);
        tv_city = (TextView) findViewById(R.id.tv_city);

        rl_change_name = (RelativeLayout) findViewById(R.id.rl_change_name);
        rl_change_shengri = (RelativeLayout) findViewById(R.id.rl_change_shengri);
        r_change_shengao = (RelativeLayout) findViewById(R.id.r_change_shengao);
        rl_change_hunyin = (RelativeLayout) findViewById(R.id.rl_change_hunyin);
        textView = (TextView) findViewById(R.id.textView);
        rl_change_address = (RelativeLayout) findViewById(R.id.rl_change_address);

        String img = (String) SpUtil.get(context, "img", "");

        if (img.startsWith("http://")) {
            SimpleDraweeView.setImageURI(img);
        } else {
            SimpleDraweeView.setImageURI(UrlUtils.URL + SpUtil.get(context, "img", ""));
        }

        tv_name.setText(String.valueOf(SpUtil.get(context, "username", "")));
        tv_shengri.setText(String.valueOf(SpUtil.get(context, "shengri", "")));
        tv_shengao.setText(String.valueOf(SpUtil.get(context, "shengao", "")));
        tv_hunyin.setText(String.valueOf(SpUtil.get(context, "hunyin", "")));
        tv_city.setText(String.valueOf(SpUtil.get(context, "chengshi", "")));
        PhotoPickUtils.init(getApplicationContext(), new FrescoIniter());//第二个参数根据具体依赖库而定

    }

    private Dialog dialogResult;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {


            @Override
            public void onPickSuccess(final ArrayList<String> photos, int requestCode) {
                dialogResult = Utils.showLoadingDialog(context);
                dialogResult.show();
                SimpleDraweeView.setImageURI("file://" + photos.get(0));
                final ArrayList<File> photo = new ArrayList<>();
                pausableThreadPoolExecutor.execute(new PriorityRunnable(1) {
                    @Override
                    public void doSth() {
                        final Bitmap mbitmap = BitmapFactory.decodeFile(photos.get(0));
                        // 首先保存图片
                        File appDir = new File(Environment.getExternalStorageDirectory().getPath() + "/ScreenCapture/");
                        if (!appDir.exists()) {
                            appDir.mkdir();
                        }
                        NativeUtil.compressBitmap(mbitmap, Environment.getExternalStorageDirectory().getPath() + "/ScreenCapture/img.png", 300);
                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/ScreenCapture/img.png");
                        photo.add(file);

                        aboutEditimg(photo);
                    }
                });
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos, int requestCode) {

            }

            @Override
            public void onPickFail(String error, int requestCode) {

            }

            @Override
            public void onPickCancle(int requestCode) {
            }
        });

    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(this);
        rl_change_touxiang.setOnClickListener(this);
        rl_change_shengri.setOnClickListener(this);
        rl_change_address.setOnClickListener(this);
        r_change_shengao.setOnClickListener(this);
        rl_change_hunyin.setOnClickListener(this);
        rl_change_name.setOnClickListener(this);
        initTimePicker();
    }

    int heightmax = 100;

    @Override
    protected void initData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        for (int i = 0; i < heightmax; i++) {
            heightitems.add(100 + i + "cm");
        }
        hunyinitems.add("未婚");
        hunyinitems.add("已婚");
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_change_touxiang:
                PhotoPickUtils.startPick().setPhotoCount(1).setShowCamera(true).start((Activity) context, 505);
                break;
            case R.id.rl_change_shengri:
                pvTime.show();
                break;
            case R.id.rl_change_address:
                ShowPickerView();
                break;
            case R.id.r_change_shengao:
                ShowPickerView_height();
                break;
            case R.id.rl_change_hunyin:
                ShowPickerView_hunyin();
                break;
            case R.id.rl_change_name:
                new ChangeNameDialog(context, dialog, "", new ChangeNameDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            EditText contentTxt = (EditText) dialog.findViewById(R.id.content);
                            if (TextUtils.isEmpty(contentTxt.getText().toString())) {
                                EasyToast.showShort(context, contentTxt.getHint().toString());
                                return;
                            }
                            if (contentTxt.getText().toString().length() > 12) {
                                EasyToast.showShort(context, "用户名过长");
                                return;
                            }
                            tv_name.setText(contentTxt.getText().toString());
                            SpUtil.putAndApply(context, "username", tv_name.getText());
                            aboutEditname();
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).setTitle("修改昵称").show();
                break;
            default:
                break;
        }

    }

    private ArrayList<String> heightitems = new ArrayList<>();
    private ArrayList<String> hunyinitems = new ArrayList<>();

    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1972, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 12, 31);
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tv_shengri.setText(getTime(date));
                if (tv_shengri.getText().length() > 1) {
                    aboutEditsr();
                    SpUtil.putAndApply(context, "shengri", tv_shengri.getText());
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                .setCancelColor(getResources().getColor(R.color.text))
                .setSubmitColor(getResources().getColor(R.color.text))
                .setTitleText("选择生日")
                .setTitleColor(getResources().getColor(R.color.text))
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();

    }

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    pausableThreadPoolExecutor.execute(new PriorityRunnable(1) {
                        @Override
                        public void doSth() {
                            initJsonData();
                        }
                    });
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
                    String tx = options1Items.get(options1).getPickerViewText() +
                            options2Items.get(options1).get(options2) +
                            options3Items.get(options1).get(options2).get(options3);
                    tv_city.setText(tx);
                    if (tv_city.getText().length() > 0) {
                        aboutEditct();
                        SpUtil.putAndApply(context, "chengshi", tv_city.getText());
                    }
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

    private void ShowPickerView_height() {// 弹出选择器
        if (!options1Items.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    tv_shengao.setText(heightitems.get(options1));
                    if (tv_shengao.getText().length() > 0) {
                        aboutEditsg();
                        SpUtil.putAndApply(context, "shengao", tv_shengao.getText());
                    }
                }
            })
                    .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                    .setCancelColor(getResources().getColor(R.color.text))
                    .setSubmitColor(getResources().getColor(R.color.text))
                    .setTitleText("选择身高")
                    .setSelectOptions(60)//默认选中项
                    .setTitleColor(getResources().getColor(R.color.text))
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
            pvOptions.setPicker(heightitems);//三级选择器
            pvOptions.show();
        }

    }

    private void ShowPickerView_hunyin() {// 弹出选择器
        if (!options1Items.isEmpty()) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    tv_hunyin.setText(hunyinitems.get(options1));
                    if (tv_hunyin.getText().length() > 1) {
                        SpUtil.putAndApply(context, "hunyin", tv_hunyin.getText());
                        aboutEdithy();
                    }
                }
            })
                    .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                    .setCancelColor(getResources().getColor(R.color.text))
                    .setSubmitColor(getResources().getColor(R.color.text))
                    .setTitleText("婚姻状态")
                    .setTitleColor(getResources().getColor(R.color.text))
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(hunyinitems);//三级选择器
            pvOptions.show();
        }

    }


    /**
     * 修改用户名
     */
    private void aboutEditname() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("name", tv_name.getText().toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "about/editname", "about/editname", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {

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
     * 修改城市
     */
    private void aboutEditct() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("city", tv_city.getText().toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "about/editct", "about/editct", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {

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
     * 修改身高
     */
    private void aboutEditsg() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("shengao", tv_shengao.getText().toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "about/editsg", "about/editsg", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {

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
     * 修改婚姻
     */
    private void aboutEdithy() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("hunyin", tv_hunyin.getText().toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "about/edithy", "about/edithy", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {

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
     * 修改生日
     */
    private void aboutEditsr() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("shengri", tv_shengri.getText().toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "about/editsr", "about/editsr", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {

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
     * 更换头像
     */
    private void aboutEditimg(List<File> imgs) {
        final HashMap<String, String> params = new HashMap<>(2);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        Utils.uploadMultipart(context, UrlUtils.BASE_URL + "about/editimg", "img", imgs, params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialogResult.dismiss();
                Log.e("SubmitReturnPriceActivi", result);
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                EasyToast.showShort(context,"头像修改失败");
                dialogResult.dismiss();
                error.printStackTrace();
            }
        });
    }


}
