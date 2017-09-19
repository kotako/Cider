package info.kotlin.kotako.cider.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.ProfileActivityContract

class ProfileActivity : AppCompatActivity(), ProfileActivityContract {

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, ProfileActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setUpView()
    }

    private fun setUpView() {
        val toolbar = findViewById(R.id.toolbar_profile) as Toolbar
        toolbar.setNavigationIcon(R.mipmap.arrow_back_white)
        toolbar.setNavigationOnClickListener { finish() }

        //      viewpager, tablayout setup
        val viewPager = findViewById(R.id.pager_profile) as ViewPager
        val tabLayout = findViewById(R.id.tabs_profile) as TabLayout
        viewPager.adapter = PagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)?.customView = layoutInflater.inflate(R.layout.tab_tweet, null)
        tabLayout.getTabAt(1)?.customView = layoutInflater.inflate(R.layout.tab_photo, null)
        tabLayout.getTabAt(2)?.customView = layoutInflater.inflate(R.layout.tab_list, null)
    }

    //  ----implements PostActivityContract----
    override fun showImage() {}
    override fun makeToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}