package com.huyingbao.module.common.utils

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.view.View
import com.google.android.material.appbar.AppBarLayout

object ViewUtils {
    /**
     * 解决[com.google.android.material.appbar.AppBarLayout.setElevation]无效问题。
     *
     * @param view 一般是[com.google.android.material.appbar.AppBarLayout]
     */
    fun setElevation(view: View, elevation: Float) {
        view.stateListAnimator = StateListAnimator().apply {
            addState(IntArray(0), ObjectAnimator.ofFloat(view, "elevation", elevation))
        }
    }

    /**
     * 设置View滑动联动
     *
     * @param view 一般是[com.google.android.material.appbar.AppBarLayout]中的子布局。
     */
    fun setScroll(view: View) {
        (view.layoutParams as AppBarLayout.LayoutParams).scrollFlags =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
    }
}