package com.xiaolin.circlecommon.util

import com.xiaolin.circlecore.CUIConfig

object ScreenUtil {
    fun getPxByDp(dp :Float):Int{
        val scale =CUIConfig.getAppContext().resources.displayMetrics.density
        return (dp * scale + 0.5f) as Int
    }

    fun dip2px(dpValue: Float): Int {
        val scale = CUIConfig.getAppContext().resources.displayMetrics.density
        return (dpValue * scale + 0.5f) as Int
    }
}