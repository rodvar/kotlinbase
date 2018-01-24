package com.rodvar.kotlinbase.feature.accounts

import com.rodvar.kotlinbase.R
import com.rodvar.kotlinbase.base.presentation.BasePresenter
import com.rodvar.kotlinbase.feature.money.TransferMoneyMenuFragment

/**
 * Created by rodvar on 5/9/17.
 */
class TransferMoneyMenuPresenter : BasePresenter() {
    override fun onViewCreated() {
        (view as TransferMoneyMenuFragment).mainText(view.context().resources.getString(R.string.transfer_txt))
    }
}