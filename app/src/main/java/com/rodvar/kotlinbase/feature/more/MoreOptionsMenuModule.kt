package com.rodvar.kotlinbase.feature.accounts

import com.rodvar.kotlinbase.base.presentation.AppPresenter
import com.rodvar.kotlinbase.base.presentation.BaseFragment
import com.rodvar.kotlinbase.base.presentation.BaseModule
import com.rodvar.kotlinbase.di.scopes.PerFeature
import com.rodvar.kotlinbase.feature.more.MoreOptionsMenuFragment
import dagger.Module
import dagger.Provides

/**
 * Created by rodvar on 30/8/17.
 */
@Module(includes = arrayOf(BaseModule::class))
class MoreOptionsMenuModule {

    @PerFeature
    @Provides
    fun view(): BaseFragment = MoreOptionsMenuFragment.newInstance()

    @PerFeature
    @Provides
    fun presenter(): AppPresenter = MoreOptionsMenuPresenter()
}