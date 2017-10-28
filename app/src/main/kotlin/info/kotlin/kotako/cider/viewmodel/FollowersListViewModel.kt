package info.kotlin.kotako.cider.viewmodel

import android.util.Log
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.UsersListFragmentContract
import info.kotlin.kotako.cider.contract.UsersListViewModelContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.rx.DefaultObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FollowersListViewModel(private val listView: UsersListFragmentContract,
                             private val userId: Long) : UsersListViewModelContract {

    private var cursor = 0L
    private var disposable = CompositeDisposable()

    override fun start() {
        setUsers()
    }

    override fun stop() {
        listView.hideProgressBar()
        cursor = 0L
        disposable.dispose()
    }

    override fun setUsers() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        listView.showProgressBar()
        disposable.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .FriendShipObservable()
                .friends(userId, null, null, 50, null, null)
                .map { users -> cursor = users.next_cursor; users }
                .map { users -> users.users.map { User(it) } }
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(DefaultObserver<List<User>>(
                        next = { listView.addUserList(it); listView.hideProgressBar() },
                        error = { listView.hideProgressBar() }
                )))
    }

    override fun onRefresh() {
        if (disposable.isDisposed) disposable = CompositeDisposable()
        listView.clearUsersList()
        cursor = 0L
        setUsers()
    }

    override fun loadMore(maxId: Long) {
        if (cursor == 0L) return
        if (disposable.isDisposed) disposable = CompositeDisposable()
        listView.showProgressBar()
        disposable.add(RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .FriendShipObservable()
                .friends(userId, null, cursor, 50, null, null)
                .map { users -> cursor = users.next_cursor; users }
                .map { users -> users.users.map { User(it) } }
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(DefaultObserver<List<User>>(
                        next = { listView.addUserList(it); listView.hideProgressBar() },
                        error = { listView.hideProgressBar(); Log.d("dev", it.localizedMessage) }
                )))
    }
}