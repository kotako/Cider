package info.kotlin.kotako.cider.view.adapter

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import info.kotlin.kotako.cider.model.TabManager
import info.kotlin.kotako.cider.model.entity.Tab

class PagerAdapter(fm: FragmentManager, val list: ArrayList<Tab> = TabManager.getTabList()) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = TabManager.getFragmentByTab(list[position])

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence = list[position].name

    override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE

    override fun saveState(): Parcelable {
        val bundle = super.saveState() as Bundle
        bundle.putParcelableArray("states", null)
        return bundle
    }

    fun refresh() {
        list.clear()
        notifyDataSetChanged()
    }
}