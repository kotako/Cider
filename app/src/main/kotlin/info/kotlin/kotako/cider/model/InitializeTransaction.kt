package info.kotlin.kotako.cider.model

import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.model.entity.Tab
import info.kotlin.kotako.cider.model.entity.TabList
import io.realm.Realm

class InitializeTransaction : Realm.Transaction {
    override fun execute(realm: Realm?) {
        realm?.run {

//          ホームタイムライン
            val tabHome = realm.createObject(Tab::class.java)
            tabHome.target = TabManager.TIMELINE
            tabHome.name = TabManager.timelineTabDefault.name
            tabHome.icon = R.mipmap.home_grey

//          メンション
            val tabMention = realm.createObject(Tab::class.java)
            tabMention.target = TabManager.MENTION
            tabMention.name = TabManager.mentionTabDefault.name
            tabMention.icon = R.mipmap.notifications_grey

//          ダイレクトメール
            val tabDm = realm.createObject(Tab::class.java)
            tabDm.target = TabManager.DIRECT_MESSAGES
            tabDm.name = TabManager.dmTabDefault.name
            tabDm.icon = R.mipmap.email_grey

            val userlist = realm.createObject(Tab::class.java)
            userlist.target = TabManager.SEARCH
            userlist.targetId = "旭祭"
            userlist.name = "旭祭"
            userlist.icon = R.mipmap.view_list_grey

//          リストを作成
            val tabList = realm.createObject(TabList::class.java)
            tabList.tabList.add(tabHome)
            tabList.tabList.add(userlist)
            tabList.tabList.add(tabDm)
        }
    }
}