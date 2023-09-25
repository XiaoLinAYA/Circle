package com.xiaolin.circle.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter:FragmentStateAdapter {

    private lateinit var fragmentList: MutableList<Fragment>

    constructor(fragmentActivity: FragmentActivity):super(fragmentActivity)
    constructor(fragment: Fragment): super(fragment)
    constructor(fragmentManager: FragmentManager,lifecycle: Lifecycle): super(fragmentManager,lifecycle)

    override fun getItemCount(): Int {
        return if (fragmentList == null){
            0
        }else{
            fragmentList.size
        }
    }

    override fun createFragment(position: Int): Fragment {
        return if (fragmentList == null || fragmentList.size <= position){
            Fragment()
        }else{
            fragmentList[position]
        }
    }

    override fun getItemId(position: Int): Long {
        return fragmentList[position].hashCode().toLong()
    }

    fun setFragmentList(fragmentList: MutableList<Fragment>){
        this.fragmentList=fragmentList
    }

}