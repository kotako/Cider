package info.kotlin.kotako.cider.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import info.kotlin.kotako.cider.model.TabManager
import info.kotlin.kotako.cider.model.entity.Tab

class PagerAdapter(fm: FragmentManager, val list: ArrayList<Tab> = TabManager.tabList()) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = TabManager.getFragment(list[position])

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence = "tab" + (position + 1)
}