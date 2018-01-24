package com.rodvar.kotlinbase.di

import com.rodvar.kotlinbase.business.BusinessModule
import com.rodvar.kotlinbase.data.cloud.NetworkingModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * App component, define the main modules the app needs to be installed via @Component
 * Provides the main inject method to get the app injected with the main dependency graph
 */
@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkingModule::class,
        BusinessModule::class,
        FeaturesModule::class
))
interface AppComponent : SampleAppComponent