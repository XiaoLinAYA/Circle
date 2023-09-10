package com.xiaolin.circlecommon.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.xiaolin.circlecommon.R

class UnreadCountTextView : AppCompatTextView{
    private var mNormalSize=0
    private lateinit var mPaint: Paint

    constructor(context: Context):super(context){
        init(context,null)
    }
    constructor(context: Context,attrs: AttributeSet):super(context,attrs){
        init(context,attrs)
    }
    constructor(context: Context,attrs: AttributeSet,defStyleAttr: Int):super(context,attrs,defStyleAttr){
        init(context,attrs)
    }

    private fun init(context: Context,attrs: AttributeSet?){
        mNormalSize = dp2px(18.4f)
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.UnreadCountTextView)
        val paintColor = typeArray.getColor(R.styleable.UnreadCountTextView_paint_color,resources.getColor(R.color.read_dot_bg,null))
        typeArray.recycle()

        mPaint= Paint()
        mPaint.color = paintColor
        mPaint.isAntiAlias= true
    }

    fun setPaintColor(color: Int){
        if (mPaint != null){
            mPaint.color = color
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (text.isEmpty()){
            val l = (measuredWidth - dp2px(6F))/2
            val t= l
            val r= measuredWidth - l
            val b= r
            canvas?.drawOval(RectF(l.toFloat(), t.toFloat(), r.toFloat(), b.toFloat()),mPaint)
        }else if (text.length == 1){
            canvas?.drawOval(RectF(0F, 0F, mNormalSize.toFloat(),mNormalSize.toFloat()),mPaint)
        }else if (text.length > 1){
            canvas?.drawOval(RectF(0F, 0F, measuredWidth.toFloat(), measuredWidth.toFloat()),mPaint)

        }
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = mNormalSize
        val height = mNormalSize
        if (text.length > 1){
            width = mNormalSize + dp2px(((text.length-1)*10).toFloat())
        }
        setMeasuredDimension(width,height)
    }


    private fun dp2px(dp :Float):Int{
        val displayMetrics=resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,displayMetrics) as Int
    }
}