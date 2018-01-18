package sakura.liangdinvshen.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;
import sakura.liangdinvshen.Activity.FlashActivity;
import sakura.liangdinvshen.Activity.NewsDetailsActivity;
import sakura.liangdinvshen.Bean.PushBean;
import sakura.liangdinvshen.Utils.SpUtil;

import static android.content.ContentValues.TAG;

/**
 * sakura.liangdinvshen.Receiver
 *
 * @author 赵磊
 * @date 2018/1/17
 * 功能描述：
 */
public class PushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "收到了通知");
            // 在这里可以做些统计，或者做些其他工作
            int badgeCount = 1;
            ShortcutBadger.applyCount(context, badgeCount); //for 1.1.4+
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.e(TAG, "用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            ShortcutBadger.removeCount(context); //for 1.1.4+
            String uid = (String) SpUtil.get(context, "uid", "");
            //未登录
            if (TextUtils.isEmpty(uid)) {
                Intent i = new Intent(context, FlashActivity.class);  //自定义打开的界面
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                return;
            }

            Log.e(TAG, "收到了参数消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_EXTRA));
            PushBean pushBean = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA), PushBean.class);

            //已登录
            Intent i = new Intent(context, NewsDetailsActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("id", pushBean.getPush_id());
            i.putExtra("shareimg", pushBean.getImg());
            if (!TextUtils.isEmpty(pushBean.getPush_id())) {
                context.startActivity(i);
            }
        } else {
            Log.e(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}
