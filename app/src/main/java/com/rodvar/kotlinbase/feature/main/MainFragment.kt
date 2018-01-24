package com.rodvar.kotlinbase.feature.main

import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by rodvar on 5/9/17.
 */
class MainFragment : BaseFragment() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_main
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    fun defaultMenuOption() {
        this.bottom_navigation.selectedItemId = R.id.menu_item_accounts
    }

}