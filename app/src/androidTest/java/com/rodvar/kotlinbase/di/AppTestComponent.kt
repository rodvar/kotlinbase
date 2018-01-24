package com.rodvar.kotlinbase.di

import com.rodvar.kotlinbase.BaseInstrumentedTest
import com.rodvar.kotlinbase.business.BusinessModule
import com.rodvar.kotlinbase.data.ApiSampleTest
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * App TEST component, define the main modules the testing app needs to be installed via @Component
 * Provides the main inject method to get the app injected with the main dependency graph ( parent)
 *
 * Add / replace here the modules you want to provide mocked dependencies :)
 */
@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkingTestModule::class,
        BusinessModule::class,
        FeaturesModule::class
))
interface AppTestComponent : SampleAppComponent {

    /**
     * IMPORTANT - If you add a test that needs injected dependencies, IT NEEDS TO HAVE AN INJECT METHOD HERE:
     */

    fun inject(test: BaseInstrumentedTest)

    fun inject(sampleTest: ApiSampleTest)

}