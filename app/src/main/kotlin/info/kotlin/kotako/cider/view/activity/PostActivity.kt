package info.kotlin.kotako.cider.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.PostActivityContract
import info.kotlin.kotako.cider.databinding.ActivityPostBinding
import info.kotlin.kotako.cider.model.AccountManager
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.viewmodel.PostViewModel

class PostActivity : AppCompatActivity(), PostActivityContract {

    lateinit var binding: ActivityPostBinding

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, PostActivity::class.java))
        fun start(context: Context, tweet: Tweet) = context.startActivity(Intent(context, PostActivity::class.java).apply { putExtra("replyTo", tweet) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
        binding.viewModel = intent.extras?.getSerializable("replyTo")?.let { PostViewModel(this, it as Tweet) } ?: PostViewModel(this)
        setUpView()

//      リプライの時はscreenNameを入力しておく
        intent.extras?.getSerializable("replyTo")?.let {
            var tmp = "@" + (it as Tweet).user.screen_name + " "
            if (it.inReplyToScreenName !in listOf(null, AccountManager.currentAccount()?.userName)) tmp += "@" + it.inReplyToScreenName + " "
            binding.textInputTweet.apply {
                text = Editable.Factory.getInstance().newEditable(tmp)
                setSelection(text.length)
            }
        }
    }

    private fun setUpView() {
        supportActionBar?.title = "Tweet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.layoutAuthorAccountCell?.account = AccountManager.currentAccount()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    //  ----implements PostActivityContract----
    override fun makeToast(msg: String) = runOnUiThread{ Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }

    override fun inputText(): String = binding.textInputTweet.text.toString()

    override fun showProgressbar() {
        runOnUiThread {
            binding.progressbarPost.visibility = View.VISIBLE
            binding.progressbarPost.show()
        }
    }

    override fun hideProgressbar() {
        runOnUiThread {
            binding.progressbarPost.visibility = View.INVISIBLE
            binding.progressbarPost.hide()
        }
    }
}
