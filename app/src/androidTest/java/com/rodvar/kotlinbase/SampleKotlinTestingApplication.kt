package com.rodvar.kotlinbase

import com.rodvar.kotlinbase.di.AppModule
import com.rodvar.kotlinbase.di.DaggerAppTestComponent

/**
 * Created by rodvar on 5/9/17.
 * Extend application to use testing app component
 */
class SampleKotlinTestingApplication : SampleKotlinApplication() {

    override fun createApplicationComponent() {
        component = DaggerAppTestComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)
    }
}