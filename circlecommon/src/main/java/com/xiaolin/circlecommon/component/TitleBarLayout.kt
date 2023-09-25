package com.xiaolin.circlecommon.component


import android.app.Activity
import android.content.Context
import android.text.TextUtils

import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.xiaolin.circlecommon.R
import com.xiaolin.circlecommon.component.interfaces.CTitleBarLayout
import com.xiaolin.circlecommon.databinding.TitleBarLayoutBinding
import com.xiaolin.circlecommon.util.ScreenUtil
import com.xiaolin.circlecore.CUIThemeManager

class TitleBarLayout : LinearLayout,CTitleBarLayout {

    private lateinit var titleBinding: TitleBarLayoutBinding

    private lateinit var mLeftGroup: LinearLayout
    private lateinit var mRightGroup: LinearLayout
    private lateinit var mLeftTitle: TextView
    private lateinit var mCenterTitle: TextView
    private lateinit var mRightTitle: TextView
    private lateinit var mLeftIcon: ImageView
    private lateinit var mRightIcon: ImageView
    private lateinit var mTitleLayout: RelativeLayout
    private lateinit var unreadCountTextView: UnreadCountTextView


    constructor(context :Context): super(context){
        init(context,null)
    }
    constructor(context :Context,attrs: AttributeSet): super(context,attrs){
        init(context,attrs)
    }
    constructor(context :Context,attrs: AttributeSet,defStyleAttr:Int): super(context,attrs,defStyleAttr){
        init(context,attrs)
    }

    private fun init(context: Context,attrs: AttributeSet?){
        var middleTitle = ""
        var canReturn = false
        if (attrs != null){
            val array =context.obtainStyledAttributes(attrs, R.styleable.TitleBarLayout)
            middleTitle = array.getString(R.styleable.TitleBarLayout_title_bar_middle_title).toString()
            canReturn = array.getBoolean(R.styleable.TitleBarLayout_title_bar_can_return,false)
            array.recycle()
        }
        titleBinding = TitleBarLayoutBinding.inflate(LayoutInflater.from(context))
        inflate(context, R.layout.title_bar_layout,titleBinding.root)

        val params = titleBinding.pageTitleLayout.layoutParams
        params.height = ScreenUtil.getPxByDp(50F)
        titleBinding.pageTitleLayout.layoutParams = params
        setBackgroundResource(CUIThemeManager.getAttrResId(getContext(),R.attr.core_title_bar_bg))

        val iconSize =ScreenUtil.dip2px(20F)
        var iconParams = mLeftIcon.layoutParams
        iconParams.width =iconSize
        iconParams.height=iconSize
        titleBinding.pageTitleLeftIcon.layoutParams = iconParams
        iconParams = mRightIcon.layoutParams
        iconParams.width =iconSize
        iconParams.height = iconSize
        mRightIcon.layoutParams =iconParams

        if (canReturn){
            setLeftReturnListener(context)
        }
        if (!TextUtils.isEmpty(middleTitle)){
            mCenterTitle.text = middleTitle
        }

    }

    fun setLeftReturnListener(context: Context){
        mLeftGroup.setOnClickListener {
            if (context is Activity){
                val imm = getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken,0)
                context.finish()
            }
        }
    }


    override fun setOnLeftClickListener(listener: OnClickListener) {
        mLeftGroup.setOnClickListener(listener)
    }

    override fun setOnRightClickListener(listener: OnClickListener) {
        mRightGroup.setOnClickListener(listener)
    }

    override fun setTitle(title: String, position: CTitleBarLayout.Position) {
        when(position){
            CTitleBarLayout.Position.LEFT -> mLeftTitle.text = title
            CTitleBarLayout.Position.RIGHT-> mRightTitle.text = title
            CTitleBarLayout.Position.MIDDLE-> mCenterTitle.text =title
        }
    }

    override fun getLeftGroup(): LinearLayout {
        return mLeftGroup
    }

    override fun getRightGroup(): LinearLayout {
        return mRightGroup
    }

    override fun getLeftIcon(): ImageView {
        return mLeftIcon
    }

    override fun setLeftIcon(resId: Int) {
        mLeftIcon.setBackgroundResource(resId)
    }

    override fun getRightIcon(): ImageView {
        return mRightIcon
    }

    override fun setRightIcon(resId: Int) {
        mRightIcon.setBackgroundResource(resId)
    }

    override fun getLeftTitle(): TextView {
        return mLeftTitle
    }

    override fun getMiddleTitle(): TextView {
        return mCenterTitle
    }

    override fun getRightTitle(): TextView {
        return mRightTitle
    }

    fun getUnreadCountTextView(): UnreadCountTextView{
        return unreadCountTextView
    }
}