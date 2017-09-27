package info.kotlin.kotako.cider.viewmodel

import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterSession
import info.kotlin.kotako.cider.contract.AccountListActivityContract
import info.kotlin.kotako.cider.model.entity.Account
import info.kotlin.kotako.cider.model.TimelineManager
import io.realm.Realm

class AccountListViewModel(private val accountListActivity: AccountListActivityContract, private val realm: Realm) {

    val twitterManager = TimelineManager.getInstance()

    fun setAccountView() {
//      Realmから認証済みアカウントを取得して、Viewにセットしていく
        if (twitterManager.getActiveUserId() == null) return
        realm.let {
            val allAccount = it.where(Account::class.java).notEqualTo("userId", twitterManager.getActiveUserId()).findAll()
            val activeAccount = it.where(Account::class.java).equalTo("userId", twitterManager.getActiveUserId()).findAll().first()

            accountListActivity.setAccountView(activeAccount)
            allAccount.forEach { accountListActivity.setAccountView(it) }
        }
    }

    fun onTokenReceived(result: Result<TwitterSession>) {
//      Realmに追加、Viewにアカウントを示すセルを追加する（アカウント情報を取得）
//      Realmにアカウント情報を追加、既に存在していた場合もアップデート
        val account = Account(result.data.userId, result.data.userName, result.data.authToken.toString())
        realm.let { it.executeTransaction { it.copyToRealmOrUpdate(account) } }

//      画面をリフレッシュして追加
        accountListActivity.resetAccountView()
        setAccountView()
    }
}