package info.kotlin.kotako.cider.contract

import info.kotlin.kotako.cider.model.entity.DirectMessage

interface DMFragmentContract {
    fun addDm(dm: DirectMessage)
    fun addDMCollection(newDmMap: Map<Long, ArrayList<DirectMessage>>)
    fun clearDMList()
    fun dMMapSize(): Int
    fun isEmpty(): Boolean
    fun showProgressBar()
    fun hideProgressBar()
    fun showSnackBar(msg: String)
}