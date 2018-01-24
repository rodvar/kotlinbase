package com.rodvar.kotlinbase.data

import com.rodvar.kotlinbase.BaseInstrumentedTest
import com.rodvar.kotlinbase.MockableAndroidJUnitRunner
import com.rodvar.kotlinbase.data.cloud.API
import com.rodvar.kotlinbase.data.domain.ConfirmEmailRequest
import com.rodvar.kotlinbase.data.domain.security.SecurityQuestion
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RequestsVerifier
import io.appflate.restmock.utils.RequestMatchers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by rodvar on 13/9/17.
 *
 * This test was done as a way of testing the retrofit2 - rxjasva2 - dagger2 - kotlin tech stack
 * was built properly.
 *
 * Thank's to RESTMock lib you can do a fully integrated test of the API mocking the external factors.
 *
 * Check this post to see how to improve this code:
 *
 * http://fedepaol.github.io/blog/2015/09/13/testing-rxjava-observables-subscriptions/
 */
class ApiSampleTest : BaseInstrumentedTest() {

    @Inject
    lateinit var api: API

    companion object {
        const val RODRIGO_SECURITY_QUESTION = "Best friend's month of birth"
    }

    override fun setUp() {
        super.setUp()
        component.inject(this)
    }

    /**
     * Executes an un-secured API to test everything works to reach the backend.
     */
    @Test
    fun testAPI() {
        RESTMockServer.whenPOST(RequestMatchers.pathContains("auth/questions"))
                .delay(TimeUnit.MILLISECONDS, MockableAndroidJUnitRunner.Companion.DEFAULT_MOCK_SERVER_DELAY)
                .thenReturnFile(API.HTTP_OK, "security/question.json")

        val questionRequest = ConfirmEmailRequest("loco@lindo.com.au")
        val confirmEmailV1 = api.confirmEmailV1(questionRequest)
        confirmEmailV1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn { SecurityQuestion() }
                .blockingForEach({ securityQuestion: SecurityQuestion? ->
                    Assert.assertEquals(API.SUCCESS_CODE, securityQuestion?.status?.code)
                    Assert.assertEquals(RODRIGO_SECURITY_QUESTION, securityQuestion?.question)

                })

        RequestsVerifier.verifyPOST(RequestMatchers.pathContains("auth/questions")).exactly(1)
    }
}