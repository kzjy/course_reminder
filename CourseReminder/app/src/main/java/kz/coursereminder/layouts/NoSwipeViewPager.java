package kz.coursereminder.layouts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoSwipeViewPager extends ViewPager {
    private boolean enabled;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.enabled && onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.enabled && onInterceptTouchEvent(event);
    }

    public NoSwipeViewPager(@NonNull Context context) {
        super(context);
    }

    public NoSwipeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
