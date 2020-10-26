package com.websoftquality.meetfriends.Utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.websoftquality.meetfriends.R

class NonSwipeableViewpager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var swipeable: Boolean = false

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.NonSwipeableViewpager)
        try {
            swipeable = a.getBoolean(R.styleable.NonSwipeableViewpager_swipeable, true)
        } finally {
            a.recycle()
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return swipeable && super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return swipeable && super.onTouchEvent(event)
    }
}
