package com.rodvar.kotlinbase.feature.accounts

import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_accounts_menu.*

/**
 * Created by rodvar on 5/9/17.
 */
class AccountsMenuFragment : BaseFragment() {
    override val title: Int
        get() = R.string.a_title

    companion object {
        fun newInstance(): AccountsMenuFragment = AccountsMenuFragment()
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_accounts_menu
    }

    fun mainText(text: String) {
        this.main_text.text = text
    }

}