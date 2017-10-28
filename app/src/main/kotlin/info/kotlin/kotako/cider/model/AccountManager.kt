package info.kotlin.kotako.cider.model

import com.twitter.sdk.android.core.*
import info.kotlin.kotako.cider.model.entity.Account
import io.realm.Realm
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder

class AccountManager {

    companion object {
        fun currentAccount(): Account? {
            Realm.getDefaultInstance().use {
                val currentId = TwitterCore.getInstance().sessionManager.activeSession?.userId ?: return null
                it.where(Account::class.java).equalTo("userId", currentId).findFirst()?.let { account ->
                    return Account(account.userId, account.userName, account.token, account.tokenSecret)
                }
                return null
            }
        }

        fun currentConfig(): Configuration =
                ConfigurationBuilder()
                        .setOAuthConsumerKey(TwitterCore.getInstance().authConfig.consumerKey)
                        .setOAuthConsumerSecret(TwitterCore.getInstance().authConfig.consumerSecret)
                        .setOAuthAccessToken(currentAccount()?.token)
                        .setOAuthAccessTokenSecret(currentAccount()?.tokenSecret)
                        .build()

        fun changeCurrentAccount(account: Account) {
            TwitterCore.getInstance().sessionManager.clearActiveSession()
            TwitterCore.getInstance().sessionManager.activeSession =
                    TwitterSession(TwitterAuthToken(account.token, account.tokenSecret), account.userId, account.userName)
        }
    }
}