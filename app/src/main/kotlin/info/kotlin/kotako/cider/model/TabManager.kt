package info.kotlin.kotako.cider.model

import android.os.Bundle
import android.support.v4.app.Fragment
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.model.entity.Tab
import info.kotlin.kotako.cider.model.entity.TabList
import info.kotlin.kotako.cider.view.fragment.TimelineFragment
import io.realm.Realm

class TabManager {
    companion object {
        val TIMELINE = "timeline"
        val DIRECT_MESSAGES = "direct_messages"
        val TAG_COLLECTIONS = "tag_collections"
        val TARGET = "Target"
        val MENTION = "Mention"

        val timelineTabDefault = Tab(TIMELINE, "Home",null, R.mipmap.home_grey)
        val mentionTabDefault = Tab(TIMELINE, "Mention", MENTION, R.mipmap.notifications_grey)
        val dmTabDefault = Tab(DIRECT_MESSAGES, "Direct Messages",null, R.mipmap.email_grey)
        val favoriteTabDefault: Tab = Tab(TIMELINE, "Favorite",null, R.mipmap.favorite_grey)
        fun listTabDefault(listId: Long, listName:String): Tab = Tab(TIMELINE, listName, listId.toString(), R.mipmap.view_list_grey)

        fun getFragmentByTab(tab: Tab): Fragment = when (tab.id) {
            TIMELINE ->
                if (tab.target == null) TimelineFragment.newInstance() else TimelineFragment.newInstance(Bundle().apply { putString(TARGET, tab.target) })
            DIRECT_MESSAGES -> Fragment()
            TAG_COLLECTIONS -> Fragment()
            else -> Fragment()
        }

        fun tabList(): ArrayList<Tab> {
            val result = ArrayList<Tab>()
            Realm.getDefaultInstance().use { realm ->
                realm.where(TabList::class.java).findFirst()?.tabList?.forEach {
                    result.add(Tab(it.id, it.name, it.target, it.icon))
                }
            }
            return result
        }

        fun updateTabList(tabList:ArrayList<Tab>): ArrayList<Tab> {
            Realm.getDefaultInstance().use { realm ->
                realm.where(TabList::class.java).findFirst()?.let {
                    it.tabList.clear()
                    it.tabList.addAll(tabList)
                }
            }
            return Companion.tabList()
        }
    }
}