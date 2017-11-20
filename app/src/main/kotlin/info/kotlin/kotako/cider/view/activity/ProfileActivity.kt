package info.kotlin.kotako.cider.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.ProfileActivityContract
import info.kotlin.kotako.cider.databinding.ActivityProfileBinding
import info.kotlin.kotako.cider.model.entity.Friendships
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.view.dialog.ExpandedImageDialog
import info.kotlin.kotako.cider.view.adapter.ProfilePagerAdapter
import info.kotlin.kotako.cider.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity(), ProfileActivityContract {

    lateinit var binding: ActivityProfileBinding

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, ProfileActivity::class.java))
        fun start(context: Context, userId: Long?) = context.startActivity(Intent(context, ProfileActivity::class.java).putExtra("userId", userId))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ProfileViewModel(this)
        intent.extras.getSerializable("userId")?.let { viewModel.loadUser(it as Long);viewModel.loadFriendships(it) }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewModel = viewModel
        setUpView()
    }

    private fun setUpView() {
//      toolbarのセットアップ
        binding.toolbarProfile.setNavigationIcon(R.mipmap.arrow_back_white)
        binding.toolbarProfile.setNavigationOnClickListener { finish() }

        // ユーザIDを取得してセットする
        intent.extras.getSerializable("userId")?.let { binding.pagerProfile.adapter = ProfilePagerAdapter(supportFragmentManager, it as Long) }

//      タブのセット
        binding.tabsProfile.apply {
            setupWithViewPager(binding.pagerProfile)
            val tabList = listOf(R.mipmap.ic_twitter_grey, R.mipmap.insert_photo_grey, R.mipmap.friend_grey, R.mipmap.follower_grey)
            for (i in 0 until tabCount) {
                getTabAt(i)?.customView = layoutInflater.inflate(R.layout.tab_default, null).apply {
                    (findViewById(R.id.imageview_tab) as ImageView).setImageResource(tabList[i])
                    (findViewById(R.id.imageview_tab) as ImageView).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary))
                }
            }
        }
    }

    override fun setUser(user: User) = runOnUiThread { binding.user = user }

    override fun setFriendships(friendships: Friendships) = runOnUiThread { binding.friendShips = friendships }

    override fun getContext() = this
    override fun showImage(url: String) {
        ExpandedImageDialog
                .newInstance(Bundle().apply { putString("url", url) })
                .show(fragmentManager, "expandedImage")
    }

    override fun successFollow() {
        binding.friendShips?.none = false
        binding.friendShips?.blocking = false
        binding.friendShips?.following = true
        makeToast("フォローに成功したよ")
    }

    override fun successUnFollow() {
        binding.friendShips?.following = false
        makeToast("フォロー解除に成功したよ")
    }

    override fun makeToast(msg: String) = runOnUiThread { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
}