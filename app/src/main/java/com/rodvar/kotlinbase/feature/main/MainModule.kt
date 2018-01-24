package com.rodvar.kotlinbase.feature.main

import com.rodvar.kotlinbase.base.presentation.AppPresenter
import com.rodvar.kotlinbase.base.presentation.BaseFragment
import com.rodvar.kotlinbase.base.presentation.BaseModule
import com.rodvar.kotlinbase.di.scopes.PerFeature
import dagger.Module
import dagger.Provides

/**
 * Created by rodvar on 30/8/17.
 */
@Module(includes = arrayOf(BaseModule::class))
class MainModule {

    @PerFeature
    @Provides
    fun view(): BaseFragment = MainFragment.newInstance()

    @PerFeature
    @Provides
    fun presenter(): AppPresenter = MainPresenter()
}