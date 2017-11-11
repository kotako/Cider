package info.kotlin.kotako.cider.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.TabSettingsActivityContract
import info.kotlin.kotako.cider.databinding.ActivityTabSettingsBinding
import info.kotlin.kotako.cider.model.AccountManager
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.TabManager
import info.kotlin.kotako.cider.model.entity.Tab
import info.kotlin.kotako.cider.model.entity.TabList
import info.kotlin.kotako.cider.model.entity.UserList
import info.kotlin.kotako.cider.rx.DefaultObserver
import info.kotlin.kotako.cider.view.adapter.PagerAdapter
import info.kotlin.kotako.cider.view.adapter.TabsRecyclerViewAdapter
import info.kotlin.kotako.cider.view.dialog.UserListSelectDialog
import info.kotlin.kotako.cider.viewmodel.TabSettingsViewModel
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

class TabSettingsActivity : AppCompatActivity(), TabSettingsActivityContract {

    private val tabList: ArrayList<Tab> = ArrayList()
    lateinit var binding: ActivityTabSettingsBinding
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
        binding.viewModel = TabSettingsViewModel(this)
        setUpView()
    }

    private fun setUpView() {
        supportActionBar?.title = "Tab Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.run {
            pagerTabsSettings.adapter = PagerAdapter(supportFragmentManager)
            tabRefresh()
            recyclerViewTabsSettings.apply {
                adapter = TabsRecyclerViewAdapter(this@TabSettingsActivity, tabList)
                layoutManager = LinearLayoutManager(this@TabSettingsActivity)
                addItemDecoration(DividerItemDecoration(this@TabSettingsActivity, LinearLayoutManager.VERTICAL))

//              ドラッグイベントの処理
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
                        removeTab(viewHolder.adapterPosition)
                        changed = true
                    }
                }).attachToRecyclerView(this)
            }
        }
    }

    private fun tabRefresh() {
        binding.tabsPreview.apply {
            binding.pagerTabsSettings.adapter = PagerAdapter(supportFragmentManager, tabList)
            removeAllTabs()
            setupWithViewPager(binding.pagerTabsSettings)
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

    override fun showTabSelectDialog() {
        val tabs = arrayOf(TabManager.timelineTabDefault, TabManager.mentionTabDefault, TabManager.listTabDefault(0, "List"), TabManager.dmTabDefault)
        AlertDialog.Builder(this)
                .setItems(tabs.map { it.name }.toTypedArray(),
                        { dialog, which ->
                            // userlistの場合は新たにダイアログを作成する
                            if (tabs[which].target == TabManager.USERLIST) {
                                AccountManager.currentAccount()?.userId?.let { getUserList(it) }
                                return@setItems
                            }
                            addTab(tabs[which])
                            dialog.dismiss()
                        })
                .create()
                .show()
    }

    private fun getUserList(userId: Long) {
        RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .UsersObservable()
                .showUserList(userId, null, null)
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver<List<UserList>>(
                        next = { if (it.isNotEmpty()) runOnUiThread { UserListSelectDialog.newInstance(it.toTypedArray()).show(fragmentManager, "userList") } },
                        error = { runOnUiThread { Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show() } }
                ))
    }

    override fun addTab(tab: Tab) {
        if (tabList.size > 4) {
            Toast.makeText(this, "これ以上追加できないよ", Toast.LENGTH_SHORT).show()
            return
        }
        tabList.add(tab)
        binding.recyclerViewTabsSettings.adapter.notifyItemInserted(tabList.size)
        tabRefresh()
    }

    override fun removeTab(position: Int) {
        if (tabList.size == 1) {
            Toast.makeText(this, "これ以上削除できないよ", Toast.LENGTH_SHORT).show()
            return
        }
        tabList.removeAt(position)
        binding.recyclerViewTabsSettings.adapter.notifyItemRemoved(position)
        tabRefresh()
    }
}