package com.xiaolin.circlecore

import android.content.Context
import android.util.TypedValue

object CUIThemeManager {

    fun getAttrResId(context: Context?, attrId: Int) : Int{
        if (context == null || attrId == 0) {
            return 0
        }
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attrId,typedValue,true)
        return typedValue.resourceId
    }

}