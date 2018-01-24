package com.rodvar.kotlinbase

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.test.runner.AndroidJUnitRunner
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger


/**
 * Created by rodvar on 5/9/17.
 *
 * Called via Espresso, defined on build.config file.
 * More info: https://developer.android.com/training/testing/espresso/setup.html
 */
class MockableAndroidJUnitRunner : AndroidJUnitRunner() {

    companion object {
        const val DEFAULT_MOCK_SERVER_DELAY = 200L
    }

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(
            cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(
                cl, SampleKotlinTestingApplication::class.java.name, context)
    }

    /**
     * Starting mock server. More configs on:
     *
     * https://github.com/andrzejchm/RESTMock wiki
     */
    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        RESTMockServerStarter.startSync(AndroidAssetsFileParser(context), AndroidLogger())
    }
}