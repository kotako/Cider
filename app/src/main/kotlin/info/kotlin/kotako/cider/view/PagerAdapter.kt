package info.kotlin.kotako.cider.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import info.kotlin.kotako.cider.view.fragment.TimelineFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        val PAGE_TAG = "Target"
        val MENTION = "Mention"
    }

    // Realmとかに保存しといてカスタムできるようにしたい
    override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> TimelineFragment.newInstance()
                1 -> TimelineFragment.newInstance(Bundle().apply { putString(PAGE_TAG, MENTION) })
                else -> Fragment()
            }

    override fun getCount(): Int = 4

    override fun getPageTitle(position: Int): CharSequence {
        return "tab" + (position + 1)
    }
}