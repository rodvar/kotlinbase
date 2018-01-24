package com.rodvar.kotlinbase.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Main Application Module. Provides Android System dependencies.
 */
@Module
class AppModule(var application: Application) {

    @Singleton
    @Provides
    fun context() = application.applicationContext

    @Provides
    @Singleton
    fun locationManager(): LocationManager {
        return application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

}