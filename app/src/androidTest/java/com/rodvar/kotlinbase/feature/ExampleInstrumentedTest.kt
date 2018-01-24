package com.rodvar.kotlinbase.feature

import android.content.Context
import android.support.test.InstrumentationRegistry
import com.rodvar.kotlinbase.BaseInstrumentedTest
import org.junit.Assert.assertEquals
import org.junit.Test
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest : BaseInstrumentedTest() {

    @Inject
    lateinit var appContext: Context

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.rodvar.kotlinbase", appContext.packageName)
    }
}
