package info.kotlin.kotako.cider.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageButton
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.ProfileActivityContract
import info.kotlin.kotako.cider.databinding.ActivityProfileBinding
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.view.adapter.ProfilePagerAdapter
import info.kotlin.kotako.cider.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity(), ProfileActivityContract {

    var binding: ActivityProfileBinding? = null

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, ProfileActivity::class.java))
        fun start(context: Context, userId: Long) = context.startActivity(Intent(context, ProfileActivity::class.java).putExtra("userId", userId))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ProfileViewModel(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding?.viewModel = viewModel
        intent.extras.getSerializable("userId")?.let { viewModel.loadUser(it as Long) }
        setUpView()
    }

    private fun setUpView() {
        val toolbar = findViewById(R.id.toolbar_profile) as Toolbar
        toolbar.setNavigationIcon(R.mipmap.arrow_back_white)
        toolbar.setNavigationOnClickListener { finish() }

        // viewpager, tablayout setup
        val viewPager = findViewById(R.id.pager_profile) as ViewPager
        val tabLayout = findViewById(R.id.tabs_profile) as TabLayout
        intent.extras.getSerializable("userId")?.let { viewPager.adapter = ProfilePagerAdapter(supportFragmentManager, it as Long) }
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)?.customView = layoutInflater.inflate(R.layout.tab_tweet, null)
        tabLayout.getTabAt(1)?.customView = layoutInflater.inflate(R.layout.tab_photo, null)
        tabLayout.getTabAt(2)?.customView = layoutInflater.inflate(R.layout.tab_list, null)
    }

    override fun setUser(user: User) {
        runOnUiThread {
            binding?.user = user
            Glide.with(this)
                    .load(user.profileImageUrl)
                    .apply(RequestOptions().circleCrop())
                    .into(findViewById(R.id.imageView_profile_photo) as ImageButton)
        }
    }

    override fun getContext() = this
    override fun showImage() {}

    override fun makeToast(msg: String) = runOnUiThread { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
}