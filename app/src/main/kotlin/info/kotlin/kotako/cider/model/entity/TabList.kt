package info.kotlin.kotako.cider.model.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
open class TabList(
        open var tabList: RealmList<Tab> = RealmList()) : Serializable, RealmObject()
