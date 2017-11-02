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
            tabHome.id = TabManager.TIMELINE
            tabHome.name = TabManager.timelineTabDefault.name
            tabHome.icon = R.mipmap.home_grey

//          メンション
            val tabMention = realm.createObject(Tab::class.java)
            tabMention.id = TabManager.TIMELINE
            tabMention.name = TabManager.mentionTabDefault.name
            tabMention.target = TabManager.MENTION
            tabMention.icon = R.mipmap.notifications_grey

//          リスト
            //val tabUserlist = realm.createObject(Tab::class.java)
            //tabUserlist.id = TabManager.TIMELINE
            //tabUserlist.icon = R.mipmap.view_list_grey

//          ダイレクトメール
            val tabDm = realm.createObject(Tab::class.java)
            tabDm.id = TabManager.DIRECT_MESSAGES
            tabDm.name = TabManager.dmTabDefault.name
            tabDm.icon = R.mipmap.email_grey

//          リストを作成
            val tabList = realm.createObject(TabList::class.java)
            tabList.tabList.add(tabHome)
            tabList.tabList.add(tabMention)
            tabList.tabList.add(tabDm)
        }
    }
}