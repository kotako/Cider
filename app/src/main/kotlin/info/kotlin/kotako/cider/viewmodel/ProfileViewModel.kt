package info.kotlin.kotako.cider.viewmodel

import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.ProfileActivityContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.entity.Friendships
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.rx.DefaultObserver
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(private val profileView: ProfileActivityContract) {

    fun showProfileImage(url: String) = profileView.showImage(url)

    fun onFollowButtonPressed(friendships: Friendships) {
        if (friendships.following) friendships.id?.let { unFollow(it) }
        else friendships.id?.let { follow(it) }
    }

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

    fun loadFriendships(userId: Long) {
        RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .FriendShipObservable()
                .lookup(null, userId)
                .map { t: List<Friendships>? -> t?.get(0) }
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver(
                        next = { it?.let { profileView.setFriendships(Friendships(it.name, it.screen_name, it.id, it.id_str, it.connections)) } }
                ))
    }

    private fun follow(userId: Long) {
        RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .FriendShipObservable()
                .follow(null, userId, null)
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<com.twitter.sdk.android.core.models.User>(
                        error = { profileView.makeToast("失敗したよ") },
                        completed = { profileView.successFollow() }
                ))
    }

    private fun unFollow(userId: Long) {
        RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .FriendShipObservable()
                .unFollow(null, userId)
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<com.twitter.sdk.android.core.models.User>(
                        error = { profileView.makeToast("失敗したよ") },
                        completed = { profileView.successUnFollow() }
                ))
    }
}