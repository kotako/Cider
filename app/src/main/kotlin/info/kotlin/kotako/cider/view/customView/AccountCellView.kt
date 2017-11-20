package info.kotlin.kotako.cider.view.customView

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.widget.RelativeLayout
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.databinding.ViewAccountCellBinding
import info.kotlin.kotako.cider.model.entity.Account
import info.kotlin.kotako.cider.viewmodel.AccountListViewModel

class AccountCellView constructor(context: Context) : RelativeLayout(context) {

    val binding: ViewAccountCellBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_account_cell, this, true)

    fun setAccount(account: Account) {
        binding.account = account
    }

    fun setViewModel(viewModel: AccountListViewModel) {
        binding.viewModel = viewModel
    }
}