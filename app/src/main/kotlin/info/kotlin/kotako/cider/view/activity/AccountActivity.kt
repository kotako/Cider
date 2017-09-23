package info.kotlin.kotako.cider.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.AccountListActivityContract
import info.kotlin.kotako.cider.model.Account
import info.kotlin.kotako.cider.view.AccountCellView
import info.kotlin.kotako.cider.viewmodel.AccountListViewModel
import io.realm.Realm

class AccountActivity : AppCompatActivity(), AccountListActivityContract {

    val realm = Realm.getDefaultInstance()

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

        // Account一覧をセット
        val viewModel = AccountListViewModel(this, realm)
        viewModel.setAccountView()

        // Login Buttonのアクションをセット
        val loginButton = findViewById(R.id.button_login_with_twitter) as TwitterLoginButton
        loginButton.callback = (object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
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

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        (findViewById(R.id.button_login_with_twitter) as TwitterLoginButton).onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    //  アカウント一覧に取得したアカウントをセットする, currentにセットされている場合はothersにセット
    override fun setAccountView(account: Account) {
        val view = AccountCellView(this)
        view.setAccount(account)
        if ((findViewById(R.id.layout_container_account_current) as RelativeLayout).childCount == 0) {
            (findViewById(R.id.layout_container_account_current) as RelativeLayout).addView(view)
            return
        }
        (findViewById(R.id.layout_container_account_others) as LinearLayout).addView(view)
    }

    override fun resetAccountView() {
        (findViewById(R.id.layout_container_account_current) as RelativeLayout).removeAllViews()
        (findViewById(R.id.layout_container_account_others) as LinearLayout).removeAllViews()
    }
}