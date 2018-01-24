package com.rodvar.kotlinbase.di

import com.rodvar.kotlinbase.data.cloud.NetworkingModule
import dagger.Module
import io.appflate.restmock.RESTMockServer

/**
 * Created by rodvar on 13/9/17.
 *
 * Provides everything related to network connectivity.
 */
@Module
class NetworkingTestModule : NetworkingModule() {

    override fun getBaseUrl(): String = RESTMockServer.getUrl()

}