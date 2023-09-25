package com.xiaolin.circle.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.xiaolin.circle.R
import com.xiaolin.circle.databinding.ItemMainNavTabLayoutBinding
import com.xiaolin.circlecommon.component.UnreadCountTextView
import com.xiaolin.circlecommon.util.ScreenUtil
import com.xiaolin.circlecore.CUIThemeManager
import kotlin.math.abs

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

class NavTabAdapter(
    private val context: Context,
    private val navTabBeanList: MutableList<NavTabBean>,
    private val onTabEventListener: OnTabEventListener
) : RecyclerView.Adapter<NavTabAdapter.NavTabViewHolder>() {
    //lateinit var navTabBeanList : MutableList<NavTabBean>
    private lateinit var mainNavTabLayoutBinding: ItemMainNavTabLayoutBinding

    private lateinit var selectedItem : NavTabBean
    private lateinit var preSelectedItem : NavTabBean

    inner class NavTabViewHolder : RecyclerView.ViewHolder {
        var textView: TextView
        var imageView: ImageView
        var unreadCountTextView: UnreadCountTextView
        constructor(itemView: ItemMainNavTabLayoutBinding) :super(itemView.root){
            textView = itemView.tabText
            imageView = itemView.tabIcon
            unreadCountTextView = itemView.unreadView
            unreadCountTextView.addOnAttachStateChangeListener(object :View.OnAttachStateChangeListener{
                override fun onViewAttachedToWindow(v: View) {
                    disableClipOnParents(v)
                }

                override fun onViewDetachedFromWindow(v: View) {

                }

            })
        }

        private fun disableClipOnParents(v: View){
            if (v == null){
                return
            }
            if (v is ViewGroup){
                v.clipChildren = false
                v.clipToPadding = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    v.clipToOutline = false
                }
            }
            if (v.parent is View){
                disableClipOnParents(v.parent as View)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavTabViewHolder {
        mainNavTabLayoutBinding = ItemMainNavTabLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return NavTabViewHolder(mainNavTabLayoutBinding)
    }

    override fun getItemCount(): Int {
        return if (navTabBeanList != null){
            navTabBeanList.size
        }else{
            0
        }
    }

    fun setSelectedItem(navTabBean: NavTabBean){
        selectedItem =navTabBean
        preSelectedItem = selectedItem


        val prePosition =navTabBeanList.indexOf(preSelectedItem)
        if (prePosition == -1){
            return
        }

        notifyItemChanged(prePosition)
    }


    override fun onBindViewHolder(holder: NavTabViewHolder, position: Int) {
        val navTabBean = navTabBeanList[position]
        val width = context.resources.getDimensionPixelOffset(R.dimen.tab_bottom_item_width)
        val params = ViewGroup.LayoutParams(width,ViewGroup.LayoutParams.MATCH_PARENT)
        holder.itemView.layoutParams = params
        holder.textView.setText(navTabBean.text)
        if (selectedItem == navTabBean){
            holder.imageView.setBackgroundResource(CUIThemeManager.getAttrResId(context,navTabBean.selectedIcon))
            //holder.textView.setTextColor(context.resources.getColor(CUIThemeManager.getAttrResId(context,R.attr.main_tab_selected_text_color)))
        }else{
            holder.imageView.setBackgroundResource(CUIThemeManager.getAttrResId(context,navTabBean.normalIcon))
            //holder.textView.setTextColor(context.resources.getColor(CUIThemeManager.getAttrResId(context,R.attr.main_tab_normal_text_color)))
        }

        if (navTabBean.showUnread && navTabBean.unreadCount > 0){
            holder.unreadCountTextView.visibility = View.VISIBLE
            if (navTabBean.unreadCount > 99){
                holder.unreadCountTextView.text = "99+"
            }else{
                holder.unreadCountTextView.text = navTabBean.unreadCount as String
            }

            if (navTabBean.unreadClearEnable){
                prepareToClearAllUnreadMessage(holder.unreadCountTextView,navTabBean)
            }else{
                holder.unreadCountTextView.setOnTouchListener(null)
            }
        }else{
            holder.unreadCountTextView.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            onTabEventListener.onNavTabSelected(navTabBean)
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun prepareToClearAllUnreadMessage(unreadCountTextView: UnreadCountTextView, navTabBean: NavTabBean) {
        unreadCountTextView.setOnTouchListener(object : View.OnTouchListener{
            private var downX : Float = 0.0f
            private var downY : Float = 0.0f
            private var isTriggered : Boolean = false

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
               when(event?.action){
                   MotionEvent.ACTION_DOWN ->{
                       downX = unreadCountTextView.x
                       downY = unreadCountTextView.y
                   }
                   MotionEvent.ACTION_MOVE ->{
                       if (isTriggered){
                           return true
                       }
                       val viewX = v!!.x
                       val viewY = v!!.y
                       val eventX = event!!.x
                       val eventY =event!!.y
                       val translationX = eventX + viewX -downX
                       val translationY = eventY + viewY -downY
                       v.translationX = translationX
                       v.translationY = translationY

                       // If the moved x and y axis coordinates exceed a certain pixel, it will trigger one-click to clear all unread sessions
                       if (abs(translationX) > 200 || abs(translationY) > 200){
                           isTriggered = true
                           unreadCountTextView.visibility =View.GONE
                           onTabEventListener.onNavTabUnreadCleared(navTabBean)
                       }
                   }
                   MotionEvent.ACTION_UP ->{
                       v?.translationX = 0F;
                       v?.translationY = 0F
                       isTriggered = false
                   }
                   MotionEvent.ACTION_CANCEL ->{
                       v?.translationX = 0F;
                       v?.translationY = 0F
                       isTriggered = false
                   }
               }
                return true
            }

        })
    }

    inner class TabDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val columnNum = navTabBeanList.size
            val column =parent.getChildAdapterPosition(view)
            val screenWidth = ScreenUtil.getScreenWidth(context)
            val columnWidth = parent.resources.getDimensionPixelOffset(R.dimen.tab_bottom_item_width)
            if (columnNum > 1){
                val leftRightSpace = (screenWidth - columnNum * columnWidth) / (columnNum - 1)
                outRect.left =column * leftRightSpace / columnNum
                outRect.right = leftRightSpace * (columnNum - 1 - column) / columnNum
            }
        }
    }
}

interface OnTabEventListener{
    fun onNavTabSelected(navTabBean: NavTabBean)

    fun onNavTabUnreadCleared(navTabBean: NavTabBean)
}



data class NavTabBean(
    val weight: Int = 0,
    val normalIcon: Int,
    val selectedIcon: Int,
    val text: Int,
    val fragment: Fragment,
    val unreadCount: Long =0 ,
    val showUnread: Boolean = false,
    val unreadClearEnable: Boolean = false
)