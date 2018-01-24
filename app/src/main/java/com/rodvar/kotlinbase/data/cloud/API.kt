package com.rodvar.kotlinbase.data.cloud

import com.rodvar.kotlinbase.data.domain.ConfirmEmailRequest
import com.rodvar.kotlinbase.data.domain.security.SecurityQuestion
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by rodvar on 13/9/17.
 *
 * Definition of our APIs
 */
interface API {

    companion object {
        val LOB: String = "loco"
        val HTTP_OK: Int = 200
        val SUCCESS_CODE : String = HTTP_OK.toString()
    }

    /**
     * @param questionRequest
     * @return
     */
    @POST("/init/auth/questions?v=1")
    fun confirmEmailV1(@Body questionRequest: ConfirmEmailRequest): Observable<SecurityQuestion>
}
