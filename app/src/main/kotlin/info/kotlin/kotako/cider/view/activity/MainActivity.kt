package info.kotlin.kotako.cider.view.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.MainActivityContract
import info.kotlin.kotako.cider.databinding.ActivityMainBinding
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
        if (resultCode == Activity.RESULT_OK) setUpView()
    }

    private fun setUpView() {
//      toolbar setup
        binding?.toolbar?.title = "tinpo"
        binding?.toolbar?.navigationIcon = getDrawable(R.mipmap.menu_white)
        binding?.toolbar?.setNavigationOnClickListener { openDrawer() }
        binding?.navigation?.setNavigationItemSelectedListener { binding?.viewModel?.navigationOnClick(it); false }

//      viewpager, tablayout setup
        binding?.pager?.adapter = PagerAdapter(supportFragmentManager)
        binding?.tabs?.setupWithViewPager(binding?.pager)

        binding?.tabs?.getTabAt(0)?.customView = layoutInflater.inflate(R.layout.tab_home, null)
        binding?.tabs?.getTabAt(1)?.customView = layoutInflater.inflate(R.layout.tab_mention, null)
        binding?.tabs?.getTabAt(2)?.customView = layoutInflater.inflate(R.layout.tab_list, null)
        binding?.tabs?.getTabAt(3)?.customView = layoutInflater.inflate(R.layout.tab_mail, null)
    }

    //  ----implements MainActivityContract----
//  viewModelから実行され、viewの変更を行う
    override fun startPostActivity() = PostActivity.start(this)

    override fun startAccountListActivity() = AccountListActivity.start(this)

    override fun startSettingsActivity() = SettingsActivity.start(this)

    override fun getContext() = this

    override fun openDrawer() = binding?.drawer?.openDrawer(GravityCompat.START)

    override fun closeDrawer() = binding?.drawer?.closeDrawer(GravityCompat.START)

    override fun showSnackBar(msg: String) {
        binding?.let {
            Snackbar.make(it.root, msg, Snackbar.LENGTH_SHORT).show()
        }
    }
}
