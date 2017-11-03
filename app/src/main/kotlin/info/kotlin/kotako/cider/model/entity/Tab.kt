package info.kotlin.kotako.cider.model.entity

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
open class Tab(
        open var name: String = "",
        open var target:String? = null,
        open var targetId:String? = null,
        open var icon: Int = 0) : Serializable, RealmObject()