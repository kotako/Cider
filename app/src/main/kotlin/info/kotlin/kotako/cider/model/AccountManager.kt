package info.kotlin.kotako.cider.model

import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.model.entity.Account
import io.realm.Realm

class AccountManager {

    companion object {
        fun currentAccount(): Account? {
            Realm.getDefaultInstance().use {
                val currentId = TwitterCore.getInstance().sessionManager.activeSession.userId
                it.where(Account::class.java).equalTo("userId", currentId).findFirst().let { account ->
                    return Account(account.userId, account.userName, account.token, account.tokenSecret)
                } }
        }
    }
}