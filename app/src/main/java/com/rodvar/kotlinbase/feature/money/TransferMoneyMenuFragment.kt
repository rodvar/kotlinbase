package com.rodvar.kotlinbase.feature.money

import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_accounts_menu.*

/**
 * Created by rodvar on 24/11/17.
 */
class TransferMoneyMenuFragment : BaseFragment() {
    // TODO create it's own fragment layout
    override fun getLayoutResId(): Int = R.layout.fragment_accounts_menu

    override val title: Int
        get() = R.string.some_action_menu_title

    companion object {
        fun newInstance(): TransferMoneyMenuFragment = TransferMoneyMenuFragment()
    }

    fun mainText(text: String) {
        this.main_text.text = text
    }
}