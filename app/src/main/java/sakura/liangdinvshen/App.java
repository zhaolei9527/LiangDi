package sakura.liangdinvshen;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.smtt.sdk.QbSdk;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import sakura.liangdinvshen.Utils.PausableThreadPoolExecutor;

/**
 * Created by 赵磊 on 2017/7/12.
 */

public class App extends Application {
    /**
     * 先创建一个请求队列，因为这个队列是全局的，所以在Application中声明这个队列
     */
    public static RequestQueue queues;
    public static PausableThreadPoolExecutor pausableThreadPoolExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());
        QbSdk.initX5Environment(this, null);
        Fresco.initialize(this);
        pausableThreadPoolExecutor = new PausableThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());

    }

    public static RequestQueue getQueues() {
        return queues;
    }
}
