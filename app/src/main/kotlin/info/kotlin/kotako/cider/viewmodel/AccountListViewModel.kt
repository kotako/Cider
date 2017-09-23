package info.kotlin.kotako.cider.viewmodel

import android.util.Log
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterSession
import info.kotlin.kotako.cider.contract.AccountListActivityContract

class AccountListViewModel(val accountListActivity: AccountListActivityContract){

    fun onTokenReceived(result:Result<TwitterSession>?){
//      Realmに追加、Viewにアカウントを示すセルを追加する（アカウント情報を取得）
        if(result == null) return
        Log.d("hoge", result.data.userName)
        Log.d("hoge", result.data.userId.toString())
    }
}