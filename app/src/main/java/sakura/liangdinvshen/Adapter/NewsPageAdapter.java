package sakura.liangdinvshen.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import sakura.liangdinvshen.Fragment.NewsListFragment;

/**
 * Created by 赵磊 on 2017/9/20.
 */

public class NewsPageAdapter extends FragmentPagerAdapter {

    private List<String> PageTitle;
    private List<String> PageId;
    private List<Fragment> NewsContentFrameLayouts;
    private Context context;

    public NewsPageAdapter(FragmentManager fm, Context context, List pageTitle, List pageId) {
        super(fm);
        this.PageTitle = pageTitle;
        this.PageId = pageId;
        this.context = context;
        NewsContentFrameLayouts = new ArrayList<>(pageId.size());
        for (int i = 0; i < pageId.size(); i++) {
            NewsListFragment newsListFragment = new NewsListFragment();
            Bundle args = new Bundle();
            args.putString("pageId", String.valueOf(pageId.get(i)));
            newsListFragment.setArguments(args);
            NewsContentFrameLayouts.add(newsListFragment);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PageTitle.get(position);
    }

    @Override
    public int getCount() {
        return PageTitle == null ? 0 : PageTitle.size();
    }

    @Override
    public Fragment getItem(int position) {
        return NewsContentFrameLayouts.get(position);
    }

}