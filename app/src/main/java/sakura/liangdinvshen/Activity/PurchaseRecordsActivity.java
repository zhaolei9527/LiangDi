package sakura.liangdinvshen.Activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.fangx.haorefresh.LoadMoreListener;
import sakura.liangdinvshen.Adapter.PurchaseRecordsAdapter;
import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.View.LiangDiRecycleView;
import sakura.liangdinvshen.View.SakuraLinearLayoutManager;

/**
 * Created by 赵磊 on 2017/9/22.
 */

public class PurchaseRecordsActivity extends BaseActivity {
    private FrameLayout rl_back;
    private LiangDiRecycleView rv_purchaserecord;
    private ArrayList datas;
    private SakuraLinearLayoutManager line;
    private int p = 1;
    private PurchaseRecordsAdapter purchaseRecordsAdapter;

    @Override
    protected int setthislayout() {
        return R.layout.activity_purchaserecords_layout;
    }

    @Override
    protected void initview() {
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        rv_purchaserecord = (LiangDiRecycleView) findViewById(R.id.rv_purchaserecord);
        line = new SakuraLinearLayoutManager(context);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        rv_purchaserecord.setLayoutManager(line);
        rv_purchaserecord.setItemAnimator(new DefaultItemAnimator());
        TextView textView = new TextView(context);
        textView.setText("-NOTMORE-");
        rv_purchaserecord.setFootEndView(textView);
        rv_purchaserecord.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                p = p + 1;
                getData();
            }
        });
    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        datas = new ArrayList();
        getData();
    }

    public void getData() {
        for (int i = 0; i < 10; i++) {
            datas.add("");
            Log.d("ShopFragment", "add");
        }
        if (p == 1) {
            purchaseRecordsAdapter = new PurchaseRecordsAdapter(datas, context);
            rv_purchaserecord.setAdapter(purchaseRecordsAdapter);
        } else {
            purchaseRecordsAdapter.setDatas(datas);
        }
        if (rv_purchaserecord != null) {
            rv_purchaserecord.setEnabled(true);
            rv_purchaserecord.loadMoreComplete();
        }
    }
}
