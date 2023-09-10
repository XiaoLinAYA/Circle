package com.xiaolin.circlecommon.component.interfaces

import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

interface CTitleBarLayout {
    /**
     * 设置左边标题的点击事件
     *
     */
    fun setOnLeftClickListener(listener: OnClickListener)

    /**
     * 设置右边标题点击事件
     *
     * */
    fun setRightClickListener(listener: OnClickListener)

    /**
     * 设置标题
     *
     * */

    fun setTitle(title: String, position: Position)

    /**
     * 返回左边标题区域
     *
     * */
    fun getLeftGroup(): LinearLayout

    /**
     *返回右边标题区域
     *
     */
    fun getRightGroup(): LinearLayout

    /**
     * 返回左边标题的图片
     *
     * */
    fun getLeftIcon(): ImageView

    /**
     * 设置左边标题的图片
     *
     * */
    fun setLeftIcon(resId: Int)

    /**
     * 返回右边标题的图片
     *
     * */
    fun getRightIcon(): ImageView

    /**
     * 设置右边标题的图片
     *
     * */
    fun setRightIcon(resId: Int)

    /**
     * 返回左边标题的文字
     *
     * */
    fun getLeftTitle(): TextView

    /**
     * 返回中间标题的文字
     *
     * */
    fun getMiddleTitle(): TextView

    /**
     * 返回右边标题的文字
     *
     * */
    fun getRightTitle(): TextView


    enum class Position{
        /**
         * 左边标题
         *
         * */
        LEFT,

        /**
         * 中间标题
         *
         */
        MIDDLE,

        /**
         * 右边标题
         *
         * */
        RIGHT
    }
}