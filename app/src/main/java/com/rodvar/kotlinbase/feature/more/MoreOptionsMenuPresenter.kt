package com.rodvar.kotlinbase.feature.accounts

import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.BasePresenter
import com.rodvar.kotlinbase.feature.more.MoreOptionsMenuFragment

/**
 * Created by rodvar on 5/9/17.
 */
class MoreOptionsMenuPresenter : BasePresenter() {
    override fun onViewCreated() {
        (view as MoreOptionsMenuFragment).mainText(view.context().resources.getString(R.string.more_options_menu_txt))
    }
}