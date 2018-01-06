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
    private String addfen;

    private TextView tv_jifen;
    private TextView tv_addjifen;

    public QiandaoDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public QiandaoDialog(Context context, int themeResId, String content, String addfen) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.addfen = addfen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qiandao);
        initView();
    }

    private void initView() {
        tv_jifen = (TextView) findViewById(R.id.tv_jifen);
        tv_addjifen = (TextView) findViewById(R.id.tv_addjifen);

        if (content != null) {
            tv_jifen.setText(content);
        }
        if (addfen!=null){
            tv_addjifen.setText(addfen);
        }
    }

}