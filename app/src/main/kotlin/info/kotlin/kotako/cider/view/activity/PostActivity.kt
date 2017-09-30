package info.kotlin.kotako.cider.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.PostActivityContract
import info.kotlin.kotako.cider.databinding.ActivityPostBinding
import info.kotlin.kotako.cider.model.AccountManager
import info.kotlin.kotako.cider.viewmodel.PostViewModel

class PostActivity : AppCompatActivity(), PostActivityContract {

    var binding:ActivityPostBinding? = null

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, PostActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        binding?.viewModel = PostViewModel(this)
        setUpView()
    }

    private fun setUpView() {
        supportActionBar?.title = "Tweet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.layoutAuthorAccountCell?.account = AccountManager.currentAccount()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    //  ----implements PostActivityContract----
    override fun makeToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    override fun inputText(): String = binding?.textInputTweet?.text.toString()
}
