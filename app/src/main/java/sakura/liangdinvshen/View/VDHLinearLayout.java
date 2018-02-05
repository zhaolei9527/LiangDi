package sakura.liangdinvshen.View;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import sakura.liangdinvshen.R;

/**
 * sakura.liangdinvshen.View
 *
 * @author 赵磊
 * @date 2018/2/3
 * 功能描述：
 */
public class VDHLinearLayout extends FrameLayout {
    ViewDragHelper dragHelper;
    private ImageView img_zixun;

    public VDHLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);


        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == img_zixun;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (top > getHeight() - child.getMeasuredHeight()) {
                    top = getHeight() - child.getMeasuredHeight();
                } else if (top < 0) {
                    top = 0;
                }
                return top;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left > getWidth() - child.getMeasuredWidth()) {
                    left = getWidth() - child.getMeasuredWidth();
                } else if (left < 0) {
                    left = 0;
                }
                return left;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                mLastX = changedView.getX();
                mLastY = changedView.getY();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == img_zixun) {
                    float x = img_zixun.getX();
                    float y = img_zixun.getY();
                    if (x < (getMeasuredWidth() / 2f - releasedChild.getMeasuredWidth() / 2f)) { // 0-x/2
                        if (x < releasedChild.getMeasuredWidth() / 3f) {
                            x = 0;
                        } else if (y < (releasedChild.getMeasuredHeight() * 3)) { // 0-y/3
                            y = 0;
                        } else if (y > (getMeasuredHeight() - releasedChild.getMeasuredHeight() * 3)) { // 0-(y-y/3)
                            y = getMeasuredHeight() - releasedChild.getMeasuredHeight();
                        } else {
                            x = 0;
                        }
                    } else { // x/2-x
                        if (x > getMeasuredWidth() - releasedChild.getMeasuredWidth() / 3f - releasedChild.getMeasuredWidth()) {
                            x = getMeasuredWidth() - releasedChild.getMeasuredWidth();
                        } else if (y < (releasedChild.getMeasuredHeight() * 3)) { // 0-y/3
                            y = 0;
                        } else if (y > (getMeasuredHeight() - releasedChild.getMeasuredHeight() * 3)) { // 0-(y-y/3)
                            y = getMeasuredHeight() - releasedChild.getMeasuredHeight();
                        } else {
                            x = getMeasuredWidth() - releasedChild.getMeasuredWidth();
                        }
                    }
                    // 移动到x,y
                    dragHelper.smoothSlideViewTo(releasedChild, (int) x, (int) y);
                    invalidate();
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        img_zixun = (ImageView) findViewById(R.id.img_zixun);
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        restorePosition();
    }

    // 记录最后的位置
    float mLastX = -1;
    float mLastY = -1;

    public void restorePosition() {
        if (mLastX == -1 && mLastY == -1) { // 初始位置
            mLastX = getMeasuredWidth() - img_zixun.getMeasuredWidth();
            mLastY = getMeasuredHeight() * 2 / 3;
        }
        img_zixun.layout((int) mLastX, (int) mLastY,
                (int) mLastX + img_zixun.getMeasuredWidth(), (int) mLastY + img_zixun.getMeasuredHeight());
    }



}
