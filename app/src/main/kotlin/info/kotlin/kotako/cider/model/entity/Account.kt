package info.kotlin.kotako.cider.model.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Account(@PrimaryKey open var userId: Long = 0,
                   open var userName: String = "",
                   open var accessToken: String = "") : RealmObject()