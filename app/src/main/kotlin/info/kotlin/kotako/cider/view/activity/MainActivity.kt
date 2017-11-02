package info.kotlin.kotako.cider.view.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
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

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.viewModel = MainViewModel(this)
        setUpView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TabSettingsActivity.TAB_SET_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                binding?.apply {
                    (pager.adapter as PagerAdapter).refresh()
                    pager.adapter = PagerAdapter(supportFragmentManager)
                    tabs.apply { setupWithViewPager(pager) }
                }
            }
        }
        if (resultCode == Activity.RESULT_OK) setUpView()
    }

    private fun setUpView() {
        binding?.apply {

            toolbar.apply {
                title = "Cider"
                setNavigationIcon(R.mipmap.menu_white)
                setNavigationOnClickListener { openDrawer() }
            }

            navigation.run {
                setNavigationItemSelectedListener { viewModel?.navigationOnClick(it);false }
                DataBindingUtil.bind<HeaderDrawerNavigationBinding>(getHeaderView(0))
                DataBindingUtil.getBinding<HeaderDrawerNavigationBinding>(getHeaderView(0)).account = AccountManager.currentAccount()
                setCheckedItem(R.id.nav_home)
            }

            pager.adapter = PagerAdapter(supportFragmentManager)

            tabs.apply {
                setupWithViewPager(pager)
                val list = TabManager.tabList()
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
        binding?.navigation?.setCheckedItem(id)
    }

    override fun openDrawer() = binding?.drawer?.openDrawer(GravityCompat.START)

    override fun closeDrawer() = binding?.drawer?.closeDrawer(GravityCompat.START)

    override fun showSnackBar(msg: String) {
        binding?.let {
            Snackbar.make(it.root, msg, Snackbar.LENGTH_SHORT).show()
        }
    }
}
