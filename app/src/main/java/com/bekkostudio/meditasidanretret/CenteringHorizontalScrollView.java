package com.bekkostudio.meditasidanretret;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bekkostudio.meditasidanretret.Global;

public class CenteringHorizontalScrollView extends HorizontalScrollView implements View.OnTouchListener {

    private Context mContext;

    private static final int SWIPE_PAGE_ON_FACTOR = 10;

    private int mActiveItem;

    private float mPrevScrollX;

    private boolean mStart;

    private int mItemWidth;

    public CenteringHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext=context;
        mItemWidth = Global.dpToPx(getContext(),50); // or whatever your item width is.
        mStart = true;
        mActiveItem = 0;
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getRawX();

        //Alternative to fix motion down not working
        boolean handled = false;
        if (mStart) {
            mPrevScrollX = x;
            mStart = false;
        }
        switch (event.getAction()) {

            //Motion down not working due to child intercept the touch down
//            case MotionEvent.ACTION_DOWN:
//                if (mStart) {
//                    Log.d("TOUCH DOWN Mstart", "onTouch: "+x);
//                    mPrevScrollX = x;
//                    mStart = false;
//                }
//                break;

            case MotionEvent.ACTION_UP:
                mStart = true;
                int minFactor = mItemWidth / SWIPE_PAGE_ON_FACTOR;
                if ((mPrevScrollX - (float) x) > minFactor) {
                    if (mActiveItem < getMaxItemCount() - 1) {
                        mActiveItem = mActiveItem + 1;
                    }
                }
                else if ((mPrevScrollX - (float) x) < -minFactor) {
                    if (mActiveItem > 0) {
                        mActiveItem = mActiveItem - 1;
                    }
                }

                scrollToActiveItem();

                handled = true;
                break;
        }

        return handled;
    }

    private int getMaxItemCount() {
        return ((LinearLayout) getChildAt(0)).getChildCount();
    }

    private LinearLayout getLinearLayout() {
        return (LinearLayout) getChildAt(0);
    }

    /**
     * Centers the current view the best it can.
     */
    public void centerCurrentItem() {
        if (getMaxItemCount() == 0) {
            return;
        }

        int currentX = getScrollX();
        View targetChild;
        int currentChild = -1;

        do {
            currentChild++;
            targetChild = getLinearLayout().getChildAt(currentChild);
        } while (currentChild < getMaxItemCount() && targetChild.getLeft() < currentX);

        if (mActiveItem != currentChild) {
            mActiveItem = currentChild;
            scrollToActiveItem();
        }
    }

    /**
     * Scrolls the list view to the currently active child.
     */
    private void scrollToActiveItem() {
        int maxItemCount = getMaxItemCount();
        if (maxItemCount == 0) {
            return;
        }

        int targetItem = Math.min(maxItemCount - 1, mActiveItem);
        targetItem = Math.max(0, targetItem);

        mActiveItem = targetItem;

        // Scroll so that the target child is centered
        View targetView = getLinearLayout().getChildAt(targetItem);

        int targetLeft = targetView.getLeft();
        int childWidth = targetView.getRight() - targetLeft;
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int targetScroll = targetLeft - ((width - childWidth) / 2);

        super.smoothScrollTo(targetScroll, 0);

    }

    /**
     * Sets the current item and centers it.
     * @param currentItem The new current item.
     */
    public void setCurrentItemAndCenter(int currentItem) {
        mActiveItem = currentItem;
        scrollToActiveItem();
    }


    public void setLeftRightGap(Context context, int contentWidth){
        //get screen Width
        Point size = new Point();
        ((Activity)context).getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;

        getLinearLayout().setPadding((screenWidth/2)-Global.dpToPx(getContext(),(contentWidth/2)),0,(screenWidth/2)-Global.dpToPx(getContext(),(contentWidth/2)),0);

    }


    public int getActiveItem(){
        return mActiveItem;
    }

}