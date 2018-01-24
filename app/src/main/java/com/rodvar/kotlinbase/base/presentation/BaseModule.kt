package com.rodvar.kotlinbase.base.presentation

import android.content.Context
import android.support.v4.app.FragmentManager
import com.rodvar.kotlinbase.di.scopes.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by rodvar on 30/8/17.
 *
 * Base DI module for the presentation of every feature
 */
@Module
class BaseModule {

    @Provides
    @PerActivity
    fun activityContext(activity: BaseActivity): Context {
        return activity
    }

    @Provides
    @PerActivity
    fun fragmentManager(activity: BaseActivity): FragmentManager {
        return activity.supportFragmentManager
    }
}