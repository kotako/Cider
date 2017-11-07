package info.kotlin.kotako.cider.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.contract.UsersListFragmentContract
import info.kotlin.kotako.cider.databinding.FragmentUsersListBinding
import info.kotlin.kotako.cider.model.entity.User
import info.kotlin.kotako.cider.view.activity.ProfileActivity
import info.kotlin.kotako.cider.view.adapter.FriendsRecyclerViewAdapter
import info.kotlin.kotako.cider.view.listener.RecyclerScrollListener
import info.kotlin.kotako.cider.viewmodel.FollowersListViewModel
import info.kotlin.kotako.cider.viewmodel.FriendsListViewModel

class UsersListFragment : Fragment(), UsersListFragmentContract {

    private var usersList = ArrayList<User>()
    private lateinit var binding: FragmentUsersListBinding

    companion object {
        fun newInstance(): Fragment = UsersListFragment()
        fun newInstance(bundle: Bundle): Fragment = UsersListFragment().apply { arguments = bundle }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users_list, container, false)

        when {
            arguments == null || !arguments.containsKey("UserId") -> false
            arguments.containsKey("follower") -> binding.viewModel = FollowersListViewModel(this, arguments.getLong("UserId"))
            arguments.containsKey("friend") -> binding.viewModel = FriendsListViewModel(this, arguments.getLong("UserId"))
        }

        binding.layoutRefreshUsersList.setOnRefreshListener { binding.viewModel?.onRefresh() }
        binding.recyclerViewUsersList.apply {
            adapter = FriendsRecyclerViewAdapter(context, usersList)
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(RecyclerScrollListener({ binding.viewModel?.loadMore(usersList.last().id) }))
            invalidateItemDecorations()
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager(context).orientation))
        }
        binding.viewModel?.start()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewModel?.stop()
    }

    override fun addUser(user: User) {
        usersList.add(user)
    }

    override fun addUserList(users: List<User>) {
        usersList.addAll(users)
        activity.runOnUiThread { binding.recyclerViewUsersList.adapter?.notifyItemRangeChanged(usersList.size - users.size, users.size) }
    }

    override fun clearUsersList() {
        usersList.clear()
        activity.runOnUiThread { binding.recyclerViewUsersList.adapter?.notifyDataSetChanged() }
    }

    override fun showProgressBar() {
        activity.runOnUiThread { binding.layoutRefreshUsersList.isRefreshing = true }
    }

    override fun hideProgressBar() {
        activity.runOnUiThread { binding.layoutRefreshUsersList.isRefreshing = false }
    }

    override fun startProfileActivity(userId: Long) {
        ProfileActivity.start(context, userId)
    }

    override fun usersListSize(): Int = usersList.size
}