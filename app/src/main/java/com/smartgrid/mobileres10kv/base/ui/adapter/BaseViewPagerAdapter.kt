package com.graime.streetvoice.screens.base.ui.adapter

import android.content.Context
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.graime.streetvoice.screens.base.ui.fragments.BaseFragment

class BaseViewPagerAdapter(var context: Context?, fragmentManager: FragmentManager?) : FragmentStatePagerAdapter(fragmentManager) {
    var fragments : List<BaseFragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence?
        = if (fragments[position].title() != -1) context?.getString(fragments[position].title()).orEmpty() else ""

    override fun getCount(): Int {
        return fragments.size
    }
}