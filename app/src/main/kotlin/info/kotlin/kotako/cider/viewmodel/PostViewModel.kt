package info.kotlin.kotako.cider.viewmodel

import android.view.View
import info.kotlin.kotako.cider.contract.PostActivityContract

class PostViewModel(private val postView: PostActivityContract) {

    fun post(view: View) {
        postView.makeToast("post clicked")
        postView.finish()
    }

    fun onIconClicked(view: View) {
//      プロフィールのアイコンクリックでアカウント選択
        postView.makeToast("profile icon clicked")
    }

    fun onCameraClicked(view: View) {
//      写真をとる
        postView.makeToast("camera icon clicked")
    }

    fun onMediaClicked(view: View) {
//      写真を選択して追加
        postView.makeToast("media icon clicked")
    }

    fun onMusnoteClicked(view: View) {
//      nowPlayingをする
        postView.makeToast("musnonte icon clicked")
    }

    fun onLocationClicked(view: View) {
//      現在位置を送信
        postView.makeToast("location icon clicked")
    }
}
