package sakura.liangdinvshen.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.gson.Gson;
import com.hss01248.frescopicker.FrescoIniter;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import net.bither.util.NativeUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;
import sakura.liangdinvshen.App;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.CodeBean;
import sakura.liangdinvshen.Bean.DdpicIndexBean;
import sakura.liangdinvshen.Fragment.RecordFragment;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;
import sakura.liangdinvshen.other.PriorityRunnable;

import static sakura.liangdinvshen.App.pausableThreadPoolExecutor;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/29
 * 功能描述：
 */
public class BaringActivity extends BaseActivity {
    private MultiPickResultView recyclerView;
    private Dialog dialog;

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
                    try {
                        // 首先保存图片
                        File appDir = new File(Environment.getExternalStorageDirectory().getPath() + "/ScreenCapture/");
                        if (!appDir.exists()) {
                            appDir.mkdir();
                        }
                        NativeUtil.compressBitmap(mbitmap, Environment.getExternalStorageDirectory().getPath() + "/ScreenCapture/" + finalI + ".png", true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_baring;
    }

    @Override
    protected void initview() {
        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setDeniedMessage(getString(R.string.requstPerminssions))
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        PhotoPickUtils.init(getApplicationContext(), new FrescoIniter());//第二个参数根据具体依赖库而定
                        recyclerView = (MultiPickResultView) findViewById(R.id.recycler_view);
                        recyclerView.init(BaringActivity.this, MultiPickResultView.ACTION_SELECT, null);
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(context, R.string.Thepermissionapplicationisrejected, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    protected void initListener() {
        Button btn_submit = (Button) findViewById(R.id.btn_submit);

        FrameLayout rl_back = (FrameLayout) findViewById(R.id.rl_back);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> photos = recyclerView.getPhotos();
                Log.e("RegisterActivity", photos.toString());
                ArrayList<File> datas = new ArrayList<>();

                for (int i = 0; i < photos.size(); i++) {
                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/ScreenCapture/" + i + ".png");
                    Log.e("RegisterActivity", file.getAbsolutePath().toString() + "====" + i);
                    datas.add(file);
                }

                if (!datas.isEmpty()) {
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                    orderDoretreat(datas);
                } else {
                    EasyToast.showShort(context, "请选择图片");
                }

            }
        });
    }

    @Override
    protected void initData() {
        if (Utils.isConnected(context)) {
            dialog = Utils.showLoadingDialog(context);
            if (!dialog.isShowing()) {
                dialog.show();
            }
            ddpicIndex();
        } else {
            EasyToast.showShort(context, "网络未连接");
        }

    }


    /**
     * 退换申请
     */
    private void orderDoretreat(List<File> imgs) {
        final HashMap<String, String> params = new HashMap<>(6);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        Utils.uploadMultipart(context, UrlUtils.BASE_URL + "ddpic/edit", "img", imgs, params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                dialog.dismiss();
                try {
                    CodeBean codeBean = new Gson().fromJson(result, CodeBean.class);
                    if ("1".equals(String.valueOf(codeBean.getCode()))) {
                        EasyToast.showShort(context, "保存成功");
                        EventBus.getDefault().post(
                                new BankEvent("查看", "daduzhao"));
                        finish();
                    }
                    codeBean = null;
                    result = null;
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
     * 大肚照
     */
    private void ddpicIndex() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "ddpic/index", "ddpic/index", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                Log.e("RegisterActivity", result);
                try {
                    DdpicIndexBean ddpicIndexBean = new Gson().fromJson(result, DdpicIndexBean.class);
                    if ("1".equals(String.valueOf(ddpicIndexBean.getCode()))) {
                        final ArrayList<String> paths = new ArrayList<String>();
                        for (int i = 0; i < ddpicIndexBean.getRes().getBelly_photo().size(); i++) {
                            final int finalI = i;
                            final DdpicIndexBean finalDdpicIndexBean = ddpicIndexBean;
                            ImageRequest imageRequest = new ImageRequest(
                                    UrlUtils.URL + ddpicIndexBean.getRes().getBelly_photo().get(i),
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(final Bitmap response) {
                                            pausableThreadPoolExecutor.execute(new PriorityRunnable(finalI) {
                                                @Override
                                                public void doSth() {
                                                    try {
                                                        // 首先保存图片
                                                        File appDir = new File(Environment.getExternalStorageDirectory().getPath() + "/cache/");
                                                        if (!appDir.exists()) {
                                                            appDir.mkdir();
                                                        }
                                                        NativeUtil.compressBitmap(response, Environment.getExternalStorageDirectory().getPath() + "/cache/" + "diandi" + finalI + ".jpg", 300);
                                                        paths.add(Environment.getExternalStorageDirectory().getPath() + "/cache/" + "diandi" + finalI + ".jpg");
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                            Intent mediaScanIntent = new Intent(
                                                                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                                            Uri contentUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/cache/" + "diandi" + finalI + ".jpg")); //out is your output file
                                                            mediaScanIntent.setData(contentUri);
                                                            sendBroadcast(mediaScanIntent);
                                                        } else {
                                                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory().getPath() + "/cache/" + "diandi" + finalI + ".jpg")));
                                                        }
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                recyclerView.showPics(paths);
                                                                if (finalI == finalDdpicIndexBean.getRes().getBelly_photo().size()-1) {
                                                                    dialog.dismiss();
                                                                }
                                                            }
                                                        });
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                            App.getQueues().add(imageRequest);
                        }
                    } else {
                        dialog.dismiss();
                    }
                    ddpicIndexBean = null;
                    result = null;
                } catch (
                        Exception e)

                {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getQueues().cancelAll("ddpic/index");
    }
}
