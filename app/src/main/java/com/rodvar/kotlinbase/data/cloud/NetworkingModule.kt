package com.rodvar.kotlinbase.data.cloud

import android.content.Context
import com.rodvar.kotlinbase.BuildConfig
import com.rodvar.kotlinbase.base.data.GenericResponse
import com.rodvar.kotlinbase.base.utils.android.Logger
import com.rodvar.kotlinbase.data.json.GsonObjectParser
import dagger.Module
import dagger.Provides
import okhttp3.*
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by rodvar on 13/9/17.
 *
 * Provides everything related to network connectivity.
 */
@Module
open class NetworkingModule {

    companion object {
        const val BASE_URL = "https://www.rodvar.com"
        const val BASE_URL_KEY = "BASE_URL"
    }

    var authToken: String? = null

    @Provides
    @Singleton
    fun api(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }

    @Provides
    @Singleton
    fun retrofit(httpClient: OkHttpClient, @Named(BASE_URL_KEY) baseUrl: String, converter: Converter.Factory): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(converter)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .baseUrl(baseUrl)
                .build()
    }

    @Provides
    @Named(BASE_URL_KEY)
    @Singleton
    fun baseUrl(): String = getBaseUrl()

    /**
     * To be redefined for testing
     */
    open fun getBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun gsonConverterFactory(): Converter.Factory = GsonConverterFactory.create(GsonObjectParser().get())

    @Provides
    @Singleton
    fun httpClient(context: Context): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(object : Interceptor {
            private val xNabApiToken = "4299261a-20d8-4318-9800-a551d4a25638"

            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = this.interceptRequest(chain)
                return interceptResponse(chain.proceed(request))
            }

            @Throws(IOException::class)
            private fun interceptResponse(response: Response): Response {
                val rawJson = response.body()?.string()
                val requestHeaders = response.request().headers().toString()
                Logger.debug(BuildConfig.APPLICATION_ID, String.format("### REQUEST RESULTS: ###"))
                Logger.debug(BuildConfig.APPLICATION_ID, String.format("request HEADERS is: %s",
                        requestHeaders))
                Logger.debug(BuildConfig.APPLICATION_ID, "raw JSON request is : " + bodyToString(response.request()))
                Logger.debug(BuildConfig.APPLICATION_ID, String.format("response HEADERS is: %s",
                        response.headers().toString()))
                Logger.debug(BuildConfig.APPLICATION_ID, String.format("raw JSON response is: %s", rawJson))

                val modifiedResponse = response
                        .newBuilder()
                        .body(ResponseBody
                                .create(response.body()?.contentType(), rawJson))
                /**
                 * This is done cause the API returns valid errors with HTTP <> OK codes !!!!
                 * (That is NOT RESTful my friends!!!)
                 */
                if (response.code() != API.Companion.HTTP_OK && this.isGenericResponseBody(rawJson ?: ""))
                    modifiedResponse.code(API.Companion.HTTP_OK)
                return modifiedResponse.build()
            }

            private fun isGenericResponseBody(rawJson: String): Boolean {
                var isGeneric = false
                try {
                    val response = GsonObjectParser().get().fromJson(rawJson,
                            GenericResponse::class.java)
                    Logger.debug(javaClass.simpleName, "Recognized an error status response " +
                            "on an HTTP ERROR CODE response. " + "Changing error code to HTTP OK")
                    isGeneric = true
                } catch (e: Exception) {
                    Logger.error(javaClass.simpleName, "Failed to verify json as a Generic response", e)
                }
                return isGeneric
            }

            private fun interceptRequest(chain: Interceptor.Chain): Request {
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                requestBuilder.header("x-nab-key", this.xNabApiToken)
                requestBuilder.header("Accept", "application/json")
                requestBuilder.header("Content-Type", "application/json")
//                requestBuilder.header("User-Agent", this.userAgent())
                // TODO auth token needs to be setup after login. Move this to a context setting object?
                if (authToken != null)
                    requestBuilder.header("Authorization", authToken!!)
                return requestBuilder.build()
            }

            private fun bodyToString(request: Request): String {
                try {
                    val copy = request.newBuilder().build()
                    val buffer = Buffer()
                    var body = copy.body()
                    if (body == null) {
                        body = (copy.tag() as Request).body()
                        Logger.warning(BuildConfig.APPLICATION_ID, "Body was in the request tag!")
                    }
                    body?.writeTo(buffer)
                    return buffer.readUtf8()
                } catch (e: Exception) {
                    Logger.error(BuildConfig.APPLICATION_ID, "failed to parse body ", e)
                    return "did not work"
                }

            }
        })
        httpClientBuilder.connectTimeout(30000L, TimeUnit.MILLISECONDS)
        httpClientBuilder.readTimeout(15000L, TimeUnit.MILLISECONDS)
        httpClientBuilder.retryOnConnectionFailure(false) // THIS SHOULD BE HANDLE IN CALLBACKS
//        ClearableCookieJar cookieJar =
//                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
//        httpClientBuilder.cookieJar(new JavaNetCookieJar(cookieHandler));
        return httpClientBuilder.build()
    }


}