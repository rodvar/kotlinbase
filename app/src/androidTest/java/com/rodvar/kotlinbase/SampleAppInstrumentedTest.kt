package com.rodvar.kotlinbase

import org.junit.After
import org.junit.Before

/**
 * Created by rodvar on 23/11/17.
 *
 * Interface for every instrumented test
 */
interface SampleAppInstrumentedTest {

    @Before
    fun setUp()

    @After
    fun tearDown() {

    }
}