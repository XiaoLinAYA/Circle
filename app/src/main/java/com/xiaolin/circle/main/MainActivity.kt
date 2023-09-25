package com.xiaolin.circle.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaolin.circle.R
import com.xiaolin.circle.databinding.ActivityMainBinding
import com.xiaolin.circlecommon.BaseLightActivity
import com.xiaolin.cuiconversation.classicui.page.BaseFragment
import com.xiaolin.cuiconversation.classicui.page.Blank1Fragment
import java.lang.ref.WeakReference
import java.util.Collections

class MainActivity : BaseLightActivity() {
    private val mTAG = MainActivity::class.simpleName

    private lateinit var instance : WeakReference<MainActivity>

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var navTabList: MutableList<NavTabBean>
    private lateinit var conversationBean: NavTabBean
    private lateinit var communityBean: NavTabBean
    private lateinit var contactsBean: NavTabBean
    private lateinit var profileBean: NavTabBean

    private lateinit var navTabAdapter: NavTabAdapter

    private lateinit var fragmentAdapter: FragmentAdapter
    private lateinit var fragments: MutableList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = WeakReference(this)
        initView()
    }

    private fun initView(){
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        //initMenuAction()
        initNavTabs();
    }

//    private fun initMenuAction() {
//        val titleBarIconSize = resources.getDimensionPixelSize(R.dimen.title_bar_icon_size)
//        mainBinding.mainTitleBar.getLeftIcon().maxHeight = titleBarIconSize
//        mainBinding.mainTitleBar.getLeftIcon().maxWidth = titleBarIconSize
//        mainBinding.mainTitleBar.getRightIcon().maxHeight = titleBarIconSize
//        mainBinding.mainTitleBar.getRightIcon().maxWidth = titleBarIconSize
//        mainBinding.mainTitleBar.setOnRightClickListener{
//
//        }
//    }

    private fun initNavTabs() {
        navTabList = mutableListOf()

        //1第一个Tab
        conversationBean = NavTabBean(
            normalIcon = R.attr.main_tab_conversation_normal_bg,
            selectedIcon = R.attr.main_tab_conversation_selected_bg,
            text = R.string.tab_conversation_tab_text,
            fragment = BaseFragment(),
            showUnread = true,
            unreadClearEnable = true,
            weight = 100
        )
        navTabList.add(conversationBean)

        //2第二个Tab
        contactsBean = NavTabBean(
            normalIcon = R.attr.main_tab_contact_normal_bg,
            selectedIcon = R.attr.main_tab_contact_selected_bg,
            text = R.string.tab_contact_tab_text,
            fragment = Blank1Fragment(),
            showUnread = true,
            weight = 90
        )
        navTabList.add(contactsBean)

        //3第三个Tab
        communityBean = NavTabBean(
            normalIcon = R.attr.main_tab_community_normal_bg,
            selectedIcon = R.attr.main_tab_community_selected_bg,
            text = R.string.tab_community_tab_text,
            fragment = BaseFragment(),
            showUnread = true,
            weight = 80
        )
        navTabList.add(communityBean)

        profileBean = NavTabBean(
            normalIcon = R.attr.main_tab_profile_normal_bg,
            selectedIcon = R.attr.main_tab_profile_selected_bg,
            text = R.string.tab_profile_tab_text,
            fragment = Blank1Fragment()
        )
        navTabList.add(profileBean)

        Collections.sort(navTabList,object:Comparator<NavTabBean>{
            override fun compare(o1: NavTabBean?, o2: NavTabBean?): Int {
                return o2!!.weight - o1!!.weight
            }
        })


        navTabAdapter = NavTabAdapter(this,navTabList,object:OnTabEventListener{
            override fun onNavTabSelected(navTabBean: NavTabBean) {
                // Log.i("TAG",resources.getString(navTabBean.text))
                setSelected(navTabBean)
            }

            override fun onNavTabUnreadCleared(navTabBean: NavTabBean) {

            }

        })

        mainBinding.navTabList.disableIntercept()
        mainBinding.navTabList.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        mainBinding.navTabList.adapter = navTabAdapter
        mainBinding.navTabList.addItemDecoration(navTabAdapter.TabDecoration())
        fragments = arrayListOf()
        for (navTab in navTabList){
            fragments.add(navTab.fragment)
        }
        Log.i("TAG","${fragments.size}")
        fragmentAdapter = FragmentAdapter(this)
        fragmentAdapter.setFragmentList(fragments)
        mainBinding.viewPager.isUserInputEnabled= false
        mainBinding.viewPager.offscreenPageLimit = 5
        mainBinding.viewPager.adapter = fragmentAdapter




        navTabAdapter.setSelectedItem(communityBean)






    }



    fun setSelected(navTabBean: NavTabBean){
        if (navTabBean == null){
            return
        }
        val position = navTabList.indexOf(navTabBean)
        if (position == -1){
            return
        }

        navTabAdapter.notifyItemChanged(position)
        mainBinding.viewPager.setCurrentItem(position,false)

        //resetMenuState();

        if (navTabBean == conversationBean){

        }else if (navTabBean == communityBean){

        }else if (navTabBean == conversationBean){

        }else if (navTabBean == profileBean){

        }

        navTabAdapter.setSelectedItem(navTabBean)

    }


}