package info.kotlin.kotako.cider.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.MenuItem
import android.widget.ImageView
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.databinding.ActivityTabSettingsBinding
import info.kotlin.kotako.cider.model.TabManager
import info.kotlin.kotako.cider.model.entity.Tab
import info.kotlin.kotako.cider.model.entity.TabList
import info.kotlin.kotako.cider.view.adapter.PagerAdapter
import info.kotlin.kotako.cider.view.adapter.TabsRecyclerViewAdapter
import io.realm.Realm

class TabSettingsActivity : AppCompatActivity() {

    private val tabList: ArrayList<Tab> = ArrayList()
    var binding: ActivityTabSettingsBinding? = null
    var changed = false

    companion object {
        val TAB_SET_REQUEST = 123
        fun start(activity: Activity) = activity.startActivityForResult(Intent(activity, TabSettingsActivity::class.java), TAB_SET_REQUEST)
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
            tabRefresh()
            recyclerViewTabsSettings.apply {
                adapter = TabsRecyclerViewAdapter(this@TabSettingsActivity, tabList)
                layoutManager = LinearLayoutManager(this@TabSettingsActivity)
                addItemDecoration(DividerItemDecoration(this@TabSettingsActivity, LinearLayoutManager.VERTICAL))

                ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
                    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                        val tab = tabList[viewHolder.adapterPosition]
                        tabList.remove(tabList[viewHolder.adapterPosition])
                        tabList.add(target.adapterPosition, tab)
                        adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                        tabRefresh()
                        changed = true
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        tabList.removeAt(viewHolder.adapterPosition)
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                }).attachToRecyclerView(this)
            }
        }
    }

    private fun tabRefresh() {
        binding?.tabsPreview?.apply {
            removeAllTabs()
            setupWithViewPager(binding?.pagerTabsSettings)
            for (i in 0 until tabList.size) {
                getTabAt(i)?.customView = layoutInflater.inflate(R.layout.tab_default, null).apply {
                    (findViewById(R.id.imageview_tab) as ImageView).setImageResource(tabList[i].icon)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (changed) {
            setResult(Activity.RESULT_OK)
            Realm.getDefaultInstance().use { realm ->
                realm.executeTransaction {
                    it.where(TabList::class.java).findFirst()?.let { result ->
                        result.tabList.clear()
                        result.tabList.addAll(tabList)
                    }
                }
            }
        }
        finish()
        return super.onOptionsItemSelected(item)
    }
}