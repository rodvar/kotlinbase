package com.rodvar.kotlinbase

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.runner.AndroidJUnit4
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import com.rodvar.kotlinbase.base.utils.android.Logger
import com.rodvar.kotlinbase.di.AppTestComponent
import io.appflate.restmock.RESTMockServer
import org.junit.Before
import org.junit.runner.RunWith


/**
 * Created by rodvar on 5/9/17.
 *
 * Base Class for every Instrumented Espresso Test
 */
@RunWith(AndroidJUnit4::class)
abstract class BaseInstrumentedTest : SampleAppInstrumentedTest {

    lateinit var component: AppTestComponent

    @Before
    override fun setUp() {
        RESTMockServer.reset()
        Logger.setLogLevel(Logger.LogLevel.DEBUG)
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as SampleKotlinApplication
        component = app.component as AppTestComponent
        component.inject(this)
    }

    protected fun getResourseIdFrom(resName: String, resType: String): Int {
        val resources = InstrumentationRegistry.getTargetContext().resources
        return resources.getIdentifier(resName, resType, getActivityInstance().packageName)
    }

    /**
     * @return the app string (app resources)
     */
    protected fun getString(resId: Int): String {
        val resources = InstrumentationRegistry.getTargetContext().resources
        return resources.getString(resId)
    }

    /**
     * @return the app string (app resources)
     */
    protected fun getTestString(resId: Int): String {
        val resources = InstrumentationRegistry.getContext().resources
        return resources.getString(resId)
    }

    private fun getActivityInstance(): Activity {
        val currentActivity = arrayOf<Activity>()

        getInstrumentation().runOnMainSync(Runnable {
            val resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            val it = resumedActivity.iterator()
            currentActivity[0] = it.next()
        })

        return currentActivity[0]
    }

}