package info.kotlin.kotako.cider.contract

import android.content.Context
import info.kotlin.kotako.cider.model.entity.User


interface ProfileActivityContract {
    fun finish()
    fun getContext():Context
    fun makeToast(msg: String)
    fun showImage()
    fun setUser(user: User)
}