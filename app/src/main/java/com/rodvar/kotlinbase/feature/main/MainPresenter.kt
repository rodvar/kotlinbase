package com.rodvar.kotlinbase.feature.main

import android.view.MenuItem
import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.BaseFragment
import com.rodvar.kotlinbase.base.presentation.BasePresenter
import com.rodvar.kotlinbase.feature.accounts.AccountsMenuFragment
import com.rodvar.kotlinbase.feature.money.TransferMoneyMenuFragment
import com.rodvar.kotlinbase.feature.more.MoreOptionsMenuFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by rodvar on 5/9/17.
 */
class MainPresenter : BasePresenter() {

    override fun onViewCreated() {
        this.selectFirstMenuOption()
        (view as MainFragment).bottom_navigation.setOnNavigationItemSelectedListener { item: MenuItem -> selectMenu(item) }
    }

    private fun selectFirstMenuOption() {
        this.loadFragment(fragmentToLoad = AccountsMenuFragment.newInstance())
        (view as MainFragment).defaultMenuOption()
    }

    private fun selectMenu(item: MenuItem): Boolean {
        var fragment: BaseFragment
        when (item.itemId) {
            R.id.menu_item_accounts -> fragment = AccountsMenuFragment.newInstance()
            R.id.menu_item_transfer -> fragment = TransferMoneyMenuFragment.newInstance()
            R.id.menu_item_more -> fragment = MoreOptionsMenuFragment.newInstance()
            else -> fragment = AccountsMenuFragment.newInstance()
        }
        this.loadFragment(fragmentToLoad = fragment)
        return true
    }

}