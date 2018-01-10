package sakura.printersakura.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import sakura.printersakura.R;
import sakura.printersakura.base.BaseActivity;
import sakura.printersakura.httprequset.HTTP;
import sakura.printersakura.myprinter.Global;
import sakura.printersakura.myprinter.WorkService;
import sakura.printersakura.utils.DataUtils;
import sakura.printersakura.utils.SPUtil;

import static sakura.printersakura.myprinter.WorkService.workThread;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_caipiao_name;
    private TextView tv_time;
    private TextView tv_username;
    private TextView tv_number;
    private TextView tv_caipiao_max;
    private TextView tv_money;
    private TextView tv_day;
    private TextView tv_refresh;
    private TextView tv_log;
    private HTTP http;
    private String account;
    private String userid;
    private String qishu;

    @Override
    protected int setthislayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        initView();
        tv_log.setOnClickListener(this);
        tv_refresh.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        http = new HTTP();
        account = (String) SPUtil.get(context, "account", "");
        userid = (String) SPUtil.get(context, "userid", "");
        qishu = (String) SPUtil.get(context, "qishu", "");
        http.lists(userid, account, qishu, context);
    }

    private void initView() {
        tv_caipiao_name = (TextView) findViewById(R.id.tv_caipiao_name);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_caipiao_max = (TextView) findViewById(R.id.tv_caipiao_max);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_day = (TextView) findViewById(R.id.tv_day);
        tv_refresh = (TextView) findViewById(R.id.tv_refresh);
        tv_log = (TextView) findViewById(R.id.tv_log);


    }

    void PrintTest() {
        String title = "━━━七星彩━━━\n\n";
        String str =
                "购买时间：2017-11-22 09：48：56\n\n" +
                        "会员名：101616\n\n" +
                        "编号：20171122094956\n\n" +
                        "-------------------------------\n" +
                        "号码       赔率       金额\n" +
                        "-------------------------------\n" +
                        "15235       1-6608       5\n" +
                        "15235       1-6608       5\n" +
                        "15235       1-6608       5\n" +
                        "-------------------------------\n" +
                        "笔数  3   总金额  15\n" +
                        "-------------------------------\n";

        String bottom = "第二期，三天内有效" + "\r\n\n\n\n";

        // 加三行换行，避免走纸
        byte[] tmp2 = {0x1b, 0x21, 0x01};
        byte[] buf = new byte[0];
        try {
            buf = DataUtils.byteArraysToBytes(new byte[][]{
                    tmp2, str.getBytes("GBK"), tmp2});
            if (workThread.isConnected()) {
                Bundle data = new Bundle();
                Bundle dataAlign = new Bundle();
                Bundle dataTextOut = new Bundle();
                Bundle dataTextOut2 = new Bundle();
                dataTextOut.putString(Global.STRPARA1, title);
                dataTextOut.putString(Global.STRPARA2, "GBK");
                dataTextOut.putInt(Global.INTPARA1, 0);
                dataTextOut.putInt(Global.INTPARA2, 0);
                dataTextOut.putInt(Global.INTPARA3, 0);
                dataTextOut.putInt(Global.INTPARA4, 0);
                dataTextOut2.putString(Global.STRPARA1, bottom);
                dataTextOut2.putString(Global.STRPARA2, "GBK");
                dataTextOut2.putInt(Global.INTPARA1, 0);
                dataTextOut2.putInt(Global.INTPARA2, 0);
                dataTextOut2.putInt(Global.INTPARA3, 0);
                dataTextOut2.putInt(Global.INTPARA4, 0);

                // data.putByteArray(Global.BYTESPARA1, buf);
                data.putString(Global.STRPARA1, str);
                data.putString(Global.STRPARA2, "GBK");
                data.putInt(Global.INTPARA1, 0);
                data.putInt(Global.INTPARA2, 0);
                data.putInt(Global.INTPARA3, 0);
                data.putInt(Global.INTPARA4, 0);
                // data.putInt(Global.INTPARA2, buf.length);

                dataAlign.putInt(Global.INTPARA1, 1);

                workThread.handleCmd(Global.CMD_POS_SALIGN,
                        dataAlign);

                workThread.handleCmd(Global.CMD_POS_STEXTOUT,
                        dataTextOut);

                workThread.handleCmd(Global.CMD_POS_STEXTOUT, data);

                workThread.handleCmd(Global.CMD_POS_STEXTOUT,
                        dataTextOut2);
            } else {
                Toast.makeText(context, Global.toast_notconnect,
                        Toast.LENGTH_SHORT).show();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onDestroy() {
        workThread.disconnectBt();
        workThread.disconnectBle();
        workThread.disconnectNet();
        workThread.disconnectUsb();
        workThread.quit();
        workThread = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_log:
                PrintTest();
                break;
            case R.id.tv_refresh:
                http.lists(userid, account, qishu, context);
                break;
            default:
                break;
        }
    }
}
