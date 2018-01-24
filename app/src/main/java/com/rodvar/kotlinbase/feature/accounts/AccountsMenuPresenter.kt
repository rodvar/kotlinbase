package com.rodvar.kotlinbase.feature.accounts

import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.BasePresenter

/**
 * Created by rodvar on 5/9/17.
 */
class AccountsMenuPresenter : BasePresenter() {
    override fun onViewCreated() {
        (view as AccountsMenuFragment).mainText(view.context().resources.getString(R.string.a_txt))
    }
}