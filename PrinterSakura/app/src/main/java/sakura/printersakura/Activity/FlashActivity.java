package sakura.printersakura.Activity;

import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import sakura.printersakura.R;
import sakura.printersakura.base.BaseActivity;
import sakura.printersakura.utils.BluetoothManager;
import sakura.printersakura.utils.Utils;

/**
 * sakura.printersakura.Activity
 *
 * @author 赵磊
 * @date 2017/11/22
 * 功能描述：
 */
public class FlashActivity extends BaseActivity {

    @Override
    protected void ready() {
        super.ready();
        fullScreen();
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_flash;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (BluetoothManager.isBluetoothSupported()) {
                    if (!BluetoothManager.isBluetoothEnabled()) {
                        boolean b = BluetoothManager.turnOnBluetooth();
                        if (b) {
                            Toast.makeText(context, "蓝牙打开成功", Toast.LENGTH_SHORT).show();
                            gotoLogin();
                        } else {
                            //跳转到系统 Bluetooth 设置
                            startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                            Toast.makeText(context, "请先打开蓝牙", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        gotoLogin();
                    }
                } else {
                    Toast.makeText(context, "蓝牙不可用", Toast.LENGTH_SHORT).show();
                }

            }
        }, 2500);
    }

    private void gotoAppStart() {
        startActivity(new Intent(context, AppStart.class));
        finish();
    }

    private void gotoLogin() {

        boolean connected = Utils.isConnected(context);
        if (connected) {

            AutoLogin();


        } else {
            if (context != null) {
                Toast.makeText(context, "网路未连接", Toast.LENGTH_SHORT).show();
                gotoLogin();
            }
        }








        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }


    @Override
    protected void initData() {

    }
}
