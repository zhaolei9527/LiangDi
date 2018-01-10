package sakura.printersakura;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * sakura.printersakura
 *
 * @author 赵磊
 * @date 2018/1/10
 * 功能描述：
 */
public class App extends Application {
    /**
     * 先创建一个请求队列，因为这个队列是全局的，所以在Application中声明这个队列
     */
    public static RequestQueue queues;
    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());

    }

    public static RequestQueue getQueues() {
        return queues;
    }


}
