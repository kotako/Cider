package info.kotlin.kotako.cider.view.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.MainActivityContract
import info.kotlin.kotako.cider.databinding.ActivityMainBinding
import info.kotlin.kotako.cider.databinding.HeaderDrawerNavigationBinding
import info.kotlin.kotako.cider.model.AccountManager
import info.kotlin.kotako.cider.model.TabManager
import info.kotlin.kotako.cider.view.adapter.PagerAdapter
import info.kotlin.kotako.cider.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MainActivityContract {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = MainViewModel(this)
        setUpView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TabSettingsActivity.TAB_SET_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                binding.apply {
                    (pager.adapter as PagerAdapter).refresh()
                    pager.adapter = PagerAdapter(supportFragmentManager)
                    tabs.apply { setupWithViewPager(pager) }
                }
            }
        }
        if (resultCode == Activity.RESULT_OK) setUpView()
    }

    private fun setUpView() {
        binding.apply {

            toolbar.apply {
                title = "Home"
                setNavigationIcon(R.mipmap.menu_white)
                setNavigationOnClickListener { openDrawer() }
            }

            navigation.run {
                setNavigationItemSelectedListener { viewModel?.navigationOnClick(it);false }
                DataBindingUtil.bind<HeaderDrawerNavigationBinding>(getHeaderView(0))
                DataBindingUtil.getBinding<HeaderDrawerNavigationBinding>(getHeaderView(0)).apply {
                    account = AccountManager.currentAccount()
                    imageviewNavigationHeaderAccount.setOnClickListener { ProfileActivity.start(this@MainActivity, account?.userId) }
                }
                setCheckedItem(R.id.nav_home)
            }

            pager.adapter = PagerAdapter(supportFragmentManager)
            pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageSelected(position: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager.SCROLL_STATE_SETTLING) toolbar.title = pager.adapter.getPageTitle(pager.currentItem)
                }
            })

            tabs.apply {
                setupWithViewPager(pager)
                val list = TabManager.getTabList()
                for (i in 0 until list.size) {
                    getTabAt(i)?.customView = layoutInflater.inflate(R.layout.tab_default, null)
                            .apply {
                                (findViewById(R.id.imageview_tab) as ImageView).setImageResource(list[i].icon)
                                (findViewById(R.id.imageview_tab) as ImageView).setColorFilter(Color.WHITE)
                            }
                }
            }
        }
    }

    //  ----implements MainActivityContract----
//  viewModelから実行され、viewの変更を行う
    override fun startPostActivity() = PostActivity.start(this)

    override fun startAccountListActivity() = AccountListActivity.start(this)

    override fun startSettingsActivity() = SettingsActivity.start(this)

    override fun startTabsSettingsActivity() = TabSettingsActivity.start(this)

    override fun getContext() = this

    override fun checkNavigationItem(id: Int) {
        binding.navigation.setCheckedItem(id)
    }

    override fun openDrawer() = binding.drawer.openDrawer(GravityCompat.START)

    override fun closeDrawer() = binding.drawer.closeDrawer(GravityCompat.START)

    override fun showSnackBar(msg: String) {
            Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun setTitle(title: String) {
        binding.toolbar.title = title
    }
}
