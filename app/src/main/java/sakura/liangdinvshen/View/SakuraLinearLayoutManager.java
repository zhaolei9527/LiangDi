package sakura.liangdinvshen.View;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by 赵磊 on 2017/7/27.
 */

public class SakuraLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public SakuraLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }

}