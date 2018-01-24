package com.rodvar.kotlinbase.di.scopes

import javax.inject.Scope

/**
 * Created by rodvar on 29/8/17.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME) // don't need to say runtime, is the default
annotation class PerActivity