package com.xiaolin.circle.main

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class MainNavTabRecyclerView : RecyclerView{

    private var isNeedIntercept = true

    constructor(context: Context):super(context)
    constructor(context: Context,attrs: AttributeSet):super(context,attrs)
    constructor(context: Context,attrs: AttributeSet,defStyleAttr:Int):super(context,attrs,defStyleAttr)

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return isNeedIntercept and super.onInterceptTouchEvent(e)
    }

    fun enableIntercept(){
        isNeedIntercept = true
    }

    fun disableIntercept(){
        isNeedIntercept = false
    }
}