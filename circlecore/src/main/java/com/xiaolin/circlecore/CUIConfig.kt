package com.xiaolin.circlecore

import android.content.Context

object CUIConfig {
    private lateinit var appContext : Context

    fun init(context:Context){
        appContext = context
    }
    fun getAppContext() : Context{
        return appContext;
    }
}