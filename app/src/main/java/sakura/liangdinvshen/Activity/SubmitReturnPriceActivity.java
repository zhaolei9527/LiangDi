package sakura.liangdinvshen.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.hss01248.frescopicker.FrescoIniter;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import net.bither.util.NativeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.CodeBean;
import sakura.liangdinvshen.Bean.OrderRetreatBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;
import sakura.liangdinvshen.other.PriorityRunnable;

import static sakura.liangdinvshen.App.pausableThreadPoolExecutor;

public class SubmitReturnPriceActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout rl_back;
    private ImageView img;
    private TextView tv_return_type;
    private RelativeLayout rl_return_fuwu;
    private TextView tv_return_yuanyin;
    private RelativeLayout rl_return_yuanyin;
    private TextView tv_return_money;
    private ArrayList<String> fuwulist = new ArrayList<>();
    private ArrayList<String> yuanyinlist = new ArrayList<>();
    private String because = "";
    private String type = "";
    private String id;
    private Dialog dialog;
    private ArrayList<String> imgpath = new ArrayList<>();
    private MultiPickResultView recyclerView;
    private Button btn_submit;
    private EditText et_content;


    /**
     * 删除文件夹以及目录下的文件
     *
     * @param pPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public void deleteDirWihtFile(final File dir) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (dir == null || !dir.exists() || !dir.isDirectory()) {
                    return;
                }
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        file.delete(); // 删除所有文件
                    } else if (file.isDirectory()) {
                        deleteDirWihtFile(file); // 递规的方式删除文件夹
                    }
                }
                dir.delete();// 删除目录本身
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("order/retreat");
        App.getQueues().cancelAll("order/doretreat");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //onActivityResult里一行代码回调
        recyclerView.onActivityResult(requestCode, resultCode, data);
        for (int i = 0; i < recyclerView.getPhotos().size(); i++) {
            final Bitmap mbitmap = BitmapFactory.decodeFile(recyclerView.getPhotos().get(i));
            final int finalI = i;
            pausableThreadPoolExecutor.execute(new PriorityRunnable(finalI) {
                @Override
                public void doSth() {
                    // 首先保存图片
                    File appDir = new File(Environment.getExternalStorageDirectory().getPath() + "/ScreenCapture/");
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
                    NativeUtil.compressBitmap(mbitmap, Environment.getExternalStorageDirectory().getPath() + "/ScreenCapture/" + finalI + ".png", true);
                }
            });
        }
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_submit_return_price;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        img = (ImageView) findViewById(R.id.img);
        tv_return_type = (TextView) findViewById(R.id.tv_return_type);
        rl_return_fuwu = (RelativeLayout) findViewById(R.id.rl_return_fuwu);
        tv_return_yuanyin = (TextView) findViewById(R.id.tv_return_yuanyin);
        rl_return_yuanyin = (RelativeLayout) findViewById(R.id.rl_return_yuanyin);
        tv_return_money = (TextView) findViewById(R.id.tv_return_money);
        recyclerView = (MultiPickResultView) findViewById(R.id.recycler_view);
        recyclerView.init(this, MultiPickResultView.ACTION_SELECT, null);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_content = (EditText) findViewById(R.id.editText);
        id = getIntent().getStringExtra("id");
        fuwulist.add("换货");
        fuwulist.add("退货退款");
        yuanyinlist.add("商品与相关详情描述不符");
        yuanyinlist.add("质量问题");
        yuanyinlist.add("少件漏发");
        yuanyinlist.add("包装/商品破损");
        yuanyinlist.add("未按约定时间发货");
        yuanyinlist.add("其他问题");

        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setDeniedMessage(getString(R.string.requstPerminssions))
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        PhotoPickUtils.init(getApplicationContext(), new FrescoIniter());//第二个参数根据具体依赖库而定
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(context, R.string.Thepermissionapplicationisrejected, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void ShowPickerView_fuwu() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if ("换货".equals(fuwulist.get(options1))) {
                    type = "2";
                } else {
                    type = "1";
                }
                tv_return_type.setText(fuwulist.get(options1));
            }
        })
                .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                .setCancelColor(getResources().getColor(R.color.text))
                .setSubmitColor(getResources().getColor(R.color.text))
                .setTitleText("申请服务")
                .setTitleColor(getResources().getColor(R.color.text))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();
        pvOptions.setPicker(fuwulist);//三级选择器
        pvOptions.show();

    }

    private void ShowPickerView_yuanyin() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_return_yuanyin.setText(yuanyinlist.get(options1));
                because = yuanyinlist.get(options1);
            }
        })
                .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                .setCancelColor(getResources().getColor(R.color.text))
                .setSubmitColor(getResources().getColor(R.color.text))
                .setTitleText("退换原因")
                .setTitleColor(getResources().getColor(R.color.text))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(yuanyinlist);//三级选择器
        pvOptions.show();

    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(this);
        rl_return_fuwu.setOnClickListener(this);
        rl_return_yuanyin.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            dialog.show();
            orderRetreat();
        } else {
            EasyToast.showShort(context, "网络未连接");
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_return_fuwu:
                ShowPickerView_fuwu();
                break;
            case R.id.rl_return_yuanyin:
                ShowPickerView_yuanyin();
                break;
            case R.id.btn_submit:
                submit();
                break;
            default:
                break;
        }
    }

    /**
     * 提交退换货
     */
    private void submit() {
        final ArrayList<String> photos = recyclerView.getPhotos();
        Log.e("SubmitReturnPriceActivi", photos.toString());

        if (TextUtils.isEmpty(type)) {
            Toast.makeText(context, "请选择服务类型", Toast.LENGTH_SHORT).show();
            ShowPickerView_fuwu();
            return;
        }

        if (TextUtils.isEmpty(because)) {
            Toast.makeText(context, "请选择退换原因", Toast.LENGTH_SHORT).show();
            ShowPickerView_yuanyin();
            return;
        }

        if (TextUtils.isEmpty(et_content.getText().toString())) {
            Toast.makeText(context, "请填写退换说明", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<File> datas = new ArrayList<>();

        for (int i = 0; i < photos.size(); i++) {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/ScreenCapture/" + i + ".png");
            datas.add(file);
        }

        if (!datas.isEmpty()) {
            dialog.show();
            orderDoretreat(id, tv_return_yuanyin.getText().toString(), et_content.getText().toString(), type, datas);
        } else {
            EasyToast.showShort(context, "请选择图片");
        }

    }

    /**
     * 退换申请
     */
    private void orderDoretreat(String id, String because, String thbeizhu, String type, List<File> imgs) {
        final HashMap<String, String> params = new HashMap<>(6);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("because", because);
        params.put("thbeizhu", thbeizhu);
        params.put("type", type);
        Utils.uploadMultipart(context, UrlUtils.BASE_URL + "order/doretreat", "img", imgs, params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("SubmitReturnPriceActivi", result);
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {
                        EasyToast.showShort(context, codeBean.getMsg());
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        EasyToast.showShort(context, "提交失败，请重试");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
            }
        });
    }

    /**
     * 退换货信息
     */
    private void orderRetreat() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("key", UrlUtils.KEY);
        params.put("id", id);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "order/retreat", "order/retreat", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    OrderRetreatBean orderRetreatBean = new Gson().fromJson(result, OrderRetreatBean.class);
                    tv_return_money.setText("￥" + orderRetreatBean.getTkmoney());
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
