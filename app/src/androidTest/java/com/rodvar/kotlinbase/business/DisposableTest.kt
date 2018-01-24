package com.rodvar.kotlinbase.business

import com.rodvar.kotlinbase.SampleAppInstrumentedTest
import io.reactivex.disposables.Disposable

/**
 * Created by rodvar on 23/11/17.
 *
 * Handle disposables
 */
interface DisposableTest : SampleAppInstrumentedTest {

    override fun tearDown() {
        val disposable = getDisposable()
        if (!disposable?.isDisposed!!)
            disposable.dispose()
    }

    /**
     * Redefine en your test
     */
    fun getDisposable(): Disposable? = null

}