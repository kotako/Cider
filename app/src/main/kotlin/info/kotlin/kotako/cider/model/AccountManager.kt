package info.kotlin.kotako.cider.model

import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.model.entity.Account
import io.realm.Realm
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder

class AccountManager {

    companion object {
        fun currentAccount(): Account? {
            Realm.getDefaultInstance().use {
                val currentId = TwitterCore.getInstance().sessionManager.activeSession.userId
                it.where(Account::class.java).equalTo("userId", currentId).findFirst().let { account ->
                    return Account(account.userId, account.userName, account.token, account.tokenSecret)
                }
            }
        }

//      !!TODO どうにかする
        fun currentConfig(): Configuration =
                ConfigurationBuilder()
                        .setOAuthConsumerKey("A9AtDIV3s44p4N3QV0Sqe0UfP")
                        .setOAuthConsumerSecret("F3R4k8EQTTYmcfaNADKwnBMP5nl5PWMKFK2E8ESFbnoe3Z5Yqq")
                        .setOAuthAccessToken(currentAccount()?.token)
                        .setOAuthAccessTokenSecret(currentAccount()?.tokenSecret)
                        .build()
    }
}