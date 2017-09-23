package info.kotlin.kotako.cider.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.MainActivityContract
import info.kotlin.kotako.cider.databinding.ActivityMainBinding
import info.kotlin.kotako.cider.view.NavigationDrawerListener
import info.kotlin.kotako.cider.view.PagerAdapter
import info.kotlin.kotako.cider.viewmodel.MainViewModel

class MainActivity: AppCompatActivity() , MainActivityContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = MainViewModel(this)
        setUpView()
    }

    private fun setUpView() {
//      toolbar setup
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        val navigation = findViewById(R.id.drawer) as DrawerLayout
        toolbar.title = getString(R.string.app_name)
        toolbar.setNavigationIcon(R.mipmap.menu_white)
        toolbar.setNavigationOnClickListener { navigation.openDrawer(GravityCompat.START) }
        (findViewById(R.id.navigation) as NavigationView).setNavigationItemSelectedListener(NavigationDrawerListener(this))

//      viewpager, tablayout setup
        val viewPager = findViewById(R.id.pager) as ViewPager
        val tabLayout = findViewById(R.id.tabs) as TabLayout
        viewPager.adapter = PagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)?.customView = layoutInflater.inflate(R.layout.tab_home, null)
        tabLayout.getTabAt(1)?.customView = layoutInflater.inflate(R.layout.tab_mention, null)
        tabLayout.getTabAt(2)?.customView = layoutInflater.inflate(R.layout.tab_list, null)
        tabLayout.getTabAt(3)?.customView = layoutInflater.inflate(R.layout.tab_mail, null)
    }

    //  ----implements MainActivityContract----
//  viewModelから実行され、viewの変更を行う
    override fun startPostActivity() {
        AccountActivity.start(this)
        //PostActivity.start(this)
    }

    override fun getContext() = this
}
