package com.rodvar.kotlinbase.feature.more

import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_accounts_menu.*

/**
 * Created by rodvar on 24/11/17.
 */
class MoreOptionsMenuFragment : BaseFragment() {
    // TODO create it's own fragment layout
    override fun getLayoutResId(): Int = R.layout.fragment_accounts_menu

    override val title: Int
        get() = R.string.more_options_menu_title

    companion object {
        fun newInstance(): MoreOptionsMenuFragment = MoreOptionsMenuFragment()
    }

    fun mainText(text: String) {
        this.main_text.text = text
    }
}