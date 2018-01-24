package com.rodvar.kotlinbase

import android.app.Activity
import android.app.Application
import com.rodvar.kotlinbase.di.AppComponent
import com.rodvar.kotlinbase.di.AppModule
import com.rodvar.kotlinbase.di.DaggerAppComponent
import com.rodvar.kotlinbase.di.SampleAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by rodvar on 30/8/17.
 *
 * Class is open for testing purposes
 */
open class SampleKotlinApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    lateinit var component: SampleAppComponent

    override fun onCreate() {
        super.onCreate()

        this.createApplicationComponent()
    }

    /**
     *
     */
    protected open fun createApplicationComponent() {
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        (component as AppComponent?)?.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}