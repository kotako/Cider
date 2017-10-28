package info.kotlin.kotako.cider.view.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import info.kotlin.kotako.cider.view.fragment.TimelineFragment
import info.kotlin.kotako.cider.view.fragment.UsersListFragment

class ProfilePagerAdapter(fm: FragmentManager, val userId:Long) : FragmentPagerAdapter(fm) {

    companion object {
        val PAGE_TAG = "Target"
        val MENTION = "Mention"
        val USER_ID = "UserId"
    }

    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> TimelineFragment.newInstance(Bundle().apply { putLong(USER_ID, userId) })
                2 -> UsersListFragment.newInstance(Bundle().apply { putLong(USER_ID, userId) ; putBoolean("friend", true) })
                3 -> UsersListFragment.newInstance(Bundle().apply { putLong(USER_ID, userId) ; putBoolean("follower", true) })
                else -> Fragment()
            }
}