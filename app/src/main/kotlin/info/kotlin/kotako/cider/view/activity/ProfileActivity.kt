package info.kotlin.kotako.cider.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageButton
import android.widget.ImageView
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
        intent.extras.getSerializable("userId")?.let { viewModel.loadUser(it as Long) }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding?.viewModel = viewModel
        setUpView()
    }

    private fun setUpView() {
        binding?.apply {
            toolbarProfile?.apply {
                setNavigationIcon(R.mipmap.arrow_back_white)
                setNavigationOnClickListener { finish() }
            }

            // viewpager, tablayout setup
            intent.extras.getSerializable("userId")?.let { pagerProfile?.adapter = ProfilePagerAdapter(supportFragmentManager, it as Long) }

            tabsProfile?.apply {
                setupWithViewPager(pagerProfile)
                getTabAt(0)?.customView = layoutInflater.inflate(R.layout.tab_tweet, null).apply { (findViewById(R.id.imageview_tab_tweet) as ImageView).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary)) }
                getTabAt(1)?.customView = layoutInflater.inflate(R.layout.tab_photo, null).apply { (findViewById(R.id.imageview_tab_photo) as ImageView).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary)) }
                getTabAt(2)?.customView = layoutInflater.inflate(R.layout.tab_list, null).apply { (findViewById(R.id.imageview_tab_list) as ImageView).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary)) }
            }
        }
    }

    override fun setUser(user: User) = runOnUiThread { binding?.user = user }

    override fun getContext() = this
    override fun showImage() {}

    override fun makeToast(msg: String) = runOnUiThread { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
}