package info.kotlin.kotako.cider.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import info.kotlin.kotako.cider.model.TabManager
import info.kotlin.kotako.cider.model.entity.Tab

class PagerAdapter(fm: FragmentManager, val list: ArrayList<Tab> = TabManager.tabList()) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = TabManager.getFragmentByTab(list[position])

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence = TabManager.tabList()[position].name

    override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE

    fun refresh() {
        list.clear()
        notifyDataSetChanged()
    }
}