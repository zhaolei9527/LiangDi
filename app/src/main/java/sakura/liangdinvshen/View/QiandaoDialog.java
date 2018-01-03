package sakura.liangdinvshen.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import sakura.liangdinvshen.R;

/**
 * Created by 赵磊 on 2017/9/25.
 */

public class QiandaoDialog extends Dialog {

    private Context mContext;
    private String content;
    private TextView tv_jifen;

    public QiandaoDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public QiandaoDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qiandao);
        initView();
    }

    private void initView() {
        tv_jifen = (TextView) findViewById(R.id.tv_jifen);
        if (content != null) {
            tv_jifen.setText(content);
        }
    }

}