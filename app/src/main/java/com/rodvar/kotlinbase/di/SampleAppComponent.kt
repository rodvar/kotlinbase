package com.rodvar.kotlinbase.di

import com.rodvar.kotlinbase.SampleKotlinApplication

/**
 * Created by rodvar on 5/9/17.
 *
 * Definition of where to inject main component
 */
interface SampleAppComponent {
    fun inject(application: SampleKotlinApplication)
}