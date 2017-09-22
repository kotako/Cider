package info.kotlin.kotako.cider.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import info.kotlin.kotako.cider.view.fragment.TimelineFragment

class PagerAdapter(val fm:FragmentManager): FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return TimelineFragment.newInstance()
    }

    override fun getCount(): Int = 4

    override fun getPageTitle(position: Int): CharSequence {
        return "tab"+(position+1)
    }
}