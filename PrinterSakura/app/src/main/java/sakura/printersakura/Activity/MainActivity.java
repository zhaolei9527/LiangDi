package sakura.printersakura.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;

import sakura.printersakura.App;
import sakura.printersakura.Bean.ListsBean;
import sakura.printersakura.R;
import sakura.printersakura.base.BaseActivity;
import sakura.printersakura.httprequset.HTTP;
import sakura.printersakura.myprinter.Global;
import sakura.printersakura.utils.DataUtils;
import sakura.printersakura.utils.SPUtil;

import static sakura.printersakura.R.id.tv;
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
    private LinearLayout ll_content;

    //数据更新速率

    private int Delayed = 5000;
    private Runnable r;


    @Override
    protected int setthislayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context);
        }
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
        r = new Runnable() {
            @Override
            public void run() {
                http.lists(userid, account, qishu, context);
                mHandler.postDelayed(r, Delayed);
            }
        };
        mHandler.postDelayed(r, Delayed);
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
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ListsBean listsBean) {
        if ("200".equals(String.valueOf(listsBean.getCode()))) {
            tv_time.setText("" + listsBean.getTime());
            tv_username.setText("" + listsBean.getUsername());
            tv_number.setText("" + listsBean.getDid());
            tv_caipiao_max.setText("笔数：" + listsBean.getCount());
            tv_money.setText("￥" + listsBean.getMoney());
            tv_day.setText("*第" + qishu + "期，3天内有效");
            ll_content.removeAllViews();
            for (int i = 0; i < listsBean.getData().size(); i++) {
                View item_layout = View.inflate(context, R.layout.item_layout, null);
                TextView tv_caipiao_number = item_layout.findViewById(R.id.tv_caipiao_number);
                TextView tv_caipiao_peilv = item_layout.findViewById(R.id.tv_caipiao_peilv);
                TextView tv_caipiao_jine = item_layout.findViewById(R.id.tv_caipiao_jine);
                tv_caipiao_jine.setText("金额：" + listsBean.getData().get(i).getMoney());
                tv_caipiao_number.setText("" + listsBean.getData().get(i).getMingxi_2() + listsBean.getData().get(i).getMingxi_3());
                tv_caipiao_peilv.setText("赔率1：" + listsBean.getData().get(i).getOdds());
                ll_content.addView(item_layout);
            }

        }
    }

    void PrintTest() {
        String title = "━━━七星彩━━━\n\n";
        String str =
                "购买时间：" + tv_time.getText() + "\n\n" +
                        "会员名：" + tv_username.getText() + "\n\n" +
                        "编号：" + tv_number.getText() + "\n\n" +
                        "-------------------------------\n" +
                        "号码       赔率       金额\n" +
                        "-------------------------------\n" +
                        "15235       1-6608       5\n" +
                        "15235       1-6608       5\n" +
                        "15235       1-6608       5\n" +
                        "-------------------------------\n" +
                        tv_caipiao_max.getText() + "   总金额  " + tv_money.getText() + "\n" +
                        "-------------------------------\n";

        String bottom = tv_day.getText() + "\r\n\n\n\n";
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
        App.getQueues().cancelAll("lists");
        //反注册EventBus
        EventBus.getDefault().unregister(context);
        System.gc();
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
