package info.kotlin.kotako.cider.viewmodel

import android.view.View
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.PostActivityContract
import info.kotlin.kotako.cider.model.APIClient
import info.kotlin.kotako.cider.rx.DefaultObserver
import rx.schedulers.Schedulers

class PostViewModel(private val postView: PostActivityContract) {

    fun post() {
        postView.showProgressbar()
        APIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .PostObservable()
                .post(postView.inputText(), null, null, null)
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver(
                        next = { },
                        error = {
                            postView.hideProgressbar()
                            postView.makeToast("失敗した")
                        },
                        completed = {
                            postView.hideProgressbar()
                            postView.finish()
                        }))
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
