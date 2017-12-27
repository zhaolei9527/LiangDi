package sakura.printersakura.Activity;

import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import sakura.printersakura.R;
import sakura.printersakura.base.BaseActivity;
import sakura.printersakura.utils.BluetoothManager;

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
                            startActivity(new Intent(context, AppStart.class));
                            finish();
                        } else {
                            //跳转到系统 Bluetooth 设置
                            startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                            Toast.makeText(context, "请先打开蓝牙", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        startActivity(new Intent(context, AppStart.class));
                        finish();
                    }
                } else {
                    Toast.makeText(context, "蓝牙不可用", Toast.LENGTH_SHORT).show();
                }

            }
        }, 2500);
    }

    @Override
    protected void initData() {

    }
}
