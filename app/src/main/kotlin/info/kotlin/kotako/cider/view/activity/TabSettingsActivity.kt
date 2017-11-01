package info.kotlin.kotako.cider.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.ImageView
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.databinding.ActivityTabSettingsBinding
import info.kotlin.kotako.cider.model.TabManager
import info.kotlin.kotako.cider.model.entity.Tab
import info.kotlin.kotako.cider.view.adapter.PagerAdapter
import info.kotlin.kotako.cider.view.adapter.TabsRecyclerViewAdapter

class TabSettingsActivity : AppCompatActivity() {

    private val tabList: ArrayList<Tab> = ArrayList()
    var binding: ActivityTabSettingsBinding? = null

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, TabSettingsActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabList.addAll(TabManager.tabList())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_settings)
        setUpView()
    }

    private fun setUpView() {
        supportActionBar?.title = "Tab Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.run {
            pagerTabsSettings.adapter = PagerAdapter(supportFragmentManager)
            tabsPreview.apply {
                setupWithViewPager(pagerTabsSettings)
                for (i in 0 until tabList.size) {
                    getTabAt(i)?.customView = layoutInflater.inflate(R.layout.tab_default, null).apply {
                        (findViewById(R.id.imageview_tab) as ImageView).setImageResource(tabList[i].icon)
                    }
                }
            }
            recyclerViewTabsSettings.apply {
                adapter = TabsRecyclerViewAdapter(this@TabSettingsActivity, tabList)
                layoutManager = LinearLayoutManager(this@TabSettingsActivity)
                addItemDecoration(DividerItemDecoration(this@TabSettingsActivity, LinearLayoutManager.VERTICAL))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}