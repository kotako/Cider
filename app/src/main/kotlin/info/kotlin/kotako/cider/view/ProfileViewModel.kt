package info.kotlin.kotako.cider.view

import android.view.View
import info.kotlin.kotako.cider.contract.ProfileActivityContract

class ProfileViewModel(val profileView: ProfileActivityContract){

    fun showProfileImage(view: View){}
    fun onFollowButtonPressed(view:View){}
}