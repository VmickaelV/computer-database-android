package com.excilys.mviegas.computer_database.android.layouts;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by excilys on 17/11/16.
 */

public class LoadingContentLayout extends FrameLayout {

    private boolean mProgress;

    public LoadingContentLayout(Context context) {
        super(context);
    }

    public LoadingContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingContentLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 2) {
            throw new IllegalArgumentException("You can't add more than one child in LoadingContentLayout.");
        }
        super.addView(child, index, params);
    }

}
