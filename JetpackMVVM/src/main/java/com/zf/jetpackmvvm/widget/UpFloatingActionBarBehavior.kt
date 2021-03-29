package com.zf.jetpackmvvm.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UpFloatingActionBarBehavior
/**
 * 必须实现这个构造函数
 * @param context
 * @param attrs
 */(context: Context?, attrs: AttributeSet?) : FloatingActionButton.Behavior(context, attrs) {


    /**
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @param type
     * @return 返回true才会接受后续滑动事件
     */
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int, type: Int
    ): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(
                    coordinatorLayout,
                    child,
                    directTargetChild,
                    target,
                    nestedScrollAxes,
                    type
                )
    }

    /**
     * 处理滑动事件
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param type
     * @param consumed
     */
    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        super.onNestedScroll(
            coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
            dyUnconsumed, type, consumed
        )
        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            child.visibility = View.INVISIBLE
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            child.show()
        }
    }
}