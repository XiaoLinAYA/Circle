package com.xiaolin.circle.main

import android.os.Bundle
import com.xiaolin.circle.databinding.ActivityMainBinding
import com.xiaolin.circlecommon.BaseLightActivity
import java.lang.ref.WeakReference

class MainActivity : BaseLightActivity() {
    private val mTAG = MainActivity::class.simpleName

    private lateinit var instance : WeakReference<MainActivity>

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = WeakReference(this)
        initView()
    }

    private fun initView(){
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }


}