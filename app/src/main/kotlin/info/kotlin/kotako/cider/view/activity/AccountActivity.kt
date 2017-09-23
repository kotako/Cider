package info.kotlin.kotako.cider.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.AccountListActivityContract
import info.kotlin.kotako.cider.viewmodel.AccountListViewModel

class AccountActivity : AppCompatActivity(), AccountListActivityContract {

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, AccountActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_list)
        setUpView()
    }

    fun setUpView() {
        supportActionBar?.title = "Account"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel = AccountListViewModel(this)
        val loginButton = findViewById(R.id.button_login_with_twitter) as TwitterLoginButton
        loginButton.callback = ( object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                viewModel.onTokenReceived(result)
                Snackbar.make(findViewById(R.id.layout_account_list), "Successfully", Snackbar.LENGTH_SHORT)
                        .show()
            }

            override fun failure(exception: TwitterException?) {
                Snackbar.make(findViewById(R.id.layout_account_list), "Try again...", Snackbar.LENGTH_SHORT)
                        .show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        (findViewById(R.id.button_login_with_twitter) as TwitterLoginButton).onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }


    override fun setCurrentAccountView() {
//      動的に追加する
    }

    override fun setOthersAccountView() {
//      動的に追加する
    }
}