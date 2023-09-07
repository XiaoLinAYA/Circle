package com.xiaolin.circlecommon

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.xiaolin.circlecore.CircleUIThemeManager

class BaseLightActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(CircleUIThemeManager.getAttrResId(this,
                com.xiaolin.circlecore.R.attr.core_header_start_color))
            window.navigationBarColor = resources.getColor(R.color.navigation_bar_color,null)
            var vis = window.decorView.systemUiVisibility
            vis = vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            vis = vis or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.decorView.systemUiVisibility = vis
        }
    }

    override fun finish() {
        hideSoftInput()
        super.finish()
    }

    //隐藏键盘
    private fun hideSoftInput(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val window = window
        if (window != null){
            imm.hideSoftInputFromWindow(window.decorView.windowToken,0)
        }
    }
}