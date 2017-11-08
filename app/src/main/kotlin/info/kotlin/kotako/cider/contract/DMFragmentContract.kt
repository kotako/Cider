package info.kotlin.kotako.cider.contract

import info.kotlin.kotako.cider.model.entity.DirectMessage
import info.kotlin.kotako.cider.model.entity.User

interface DMFragmentContract {
    fun addDMCollection(newDmMap: Map<User, List<DirectMessage>>)
    fun clearDMList()
    fun dMMapSize(): Int
    fun isEmpty(): Boolean
    fun showProgressBar()
    fun hideProgressBar()
    fun showSnackBar(msg: String)
}