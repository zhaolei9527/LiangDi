package sakura.liangdinvshen.Adapter;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import sakura.liangdinvshen.Bean.GoodsDetailBean;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.UrlUtils;

/**
 * Created by 赵磊 on 2017/5/25.
 */
//轮播图
public class LoopAdapter extends LoopPagerAdapter {
    //

    private GoodsDetailBean.GoodsBean GoodsBean;

    public LoopAdapter(RollPagerView viewPager, GoodsDetailBean.GoodsBean GoodsBean) {
        super(viewPager);
        this.GoodsBean = GoodsBean;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        View inflate = View.inflate(container.getContext(), R.layout.layout_img, null);
        SimpleDraweeView SimpleDraweeView = (com.facebook.drawee.view.SimpleDraweeView) inflate.findViewById(R.id.SimpleDraweeView);
        SimpleDraweeView.setImageURI(UrlUtils.URL + GoodsBean.getImg().get(position));
        return inflate;
    }

    @Override
    public int getRealCount() {
        return GoodsBean.getImg().size();
    }
}