package info.kotlin.kotako.cider

import android.app.Application
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import info.kotlin.kotako.cider.model.InitializeTransaction
import io.realm.Realm
import io.realm.RealmConfiguration

class CiderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val config = TwitterConfig.Builder(this)
                .twitterAuthConfig(TwitterAuthConfig(getString(R.string.CONSUMER_KEY), getString(R.string.CONSUMER_SECRET)))
                .build()
        Twitter.initialize(config)
        Realm.init(this)

//      realmの初期化
        val realmConfig = RealmConfiguration.Builder().name("cider.realm")
                .initialData(InitializeTransaction())
                .build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}