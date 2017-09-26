package info.kotlin.kotako.cider.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.widget.RelativeLayout
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.databinding.ViewAccountCellBinding
import info.kotlin.kotako.cider.model.entity.Account

class AccountCellView constructor(context:Context): RelativeLayout(context){

    val binding = DataBindingUtil.inflate<ViewAccountCellBinding>(LayoutInflater.from(context), R.layout.view_account_cell, this, true)

    fun setAccount(account: Account) {
        binding.account = account
    }
}