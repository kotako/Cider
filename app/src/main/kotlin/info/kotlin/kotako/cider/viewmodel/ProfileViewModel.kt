package info.kotlin.kotako.cider.viewmodel

import android.view.View
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.ProfileActivityContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.rx.DefaultObserver
import rx.schedulers.Schedulers

class ProfileViewModel(val profileView: ProfileActivityContract) {

    fun showProfileImage(view: View) {}
    fun onFollowButtonPressed(view: View) {}

    fun loadUser(userId: Long) {
        RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .UsersObservable()
                .showUser(userId, null, null)
                .map { user -> User(user) }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<User>(
                        next = { user -> profileView.setUser(user) },
                        error = { throwable -> profileView.makeToast(throwable.localizedMessage) }
                ))
    }
}