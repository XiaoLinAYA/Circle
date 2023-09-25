package com.xiaolin.circlecommon.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.xiaolin.circlecore.CUIConfig

object ScreenUtil {
    fun getPxByDp(dp :Float):Int{
        val scale =CUIConfig.getAppContext().resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun dip2px(dpValue: Float): Int {
        val scale = CUIConfig.getAppContext().resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun getScreenWidth(context: Context) : Int{
        val metric =DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }
}