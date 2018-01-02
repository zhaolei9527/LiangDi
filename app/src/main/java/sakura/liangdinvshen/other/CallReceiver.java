package sakura.liangdinvshen.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.util.EMLog;

/**
 * Created by liyuzhao on 11/01/2017.
 */

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!ChatClient.getInstance().isLoggedInBefore()) {
            return;
        }
        //username
        String from = intent.getStringExtra("from");
        //call type
        String type = intent.getStringExtra("type");
        //call to
        String to = intent.getStringExtra("to");
        EMLog.d("callreceiver", "app receiver");
    }
}
