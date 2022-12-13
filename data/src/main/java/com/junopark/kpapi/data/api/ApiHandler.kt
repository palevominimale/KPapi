@file:Suppress("UNCHECKED_CAST")

package com.junopark.kpapi.data.api

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiHandler {

    private suspend fun <T> handleApi(
        execute: () -> Call<T>
    ) : ApiResult {
        return try {
            val response = execute.invoke().execute()
            val body = response.body()
            if(response.isSuccessful && body != null) {
                ApiResult.ApiSuccess(data = body)
            } else {
                ApiResult.ApiError(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            ApiResult.ApiError(code = e.code(), message = e.localizedMessage)
        } catch (e: Throwable) {
            ApiResult.ApiException(e)
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("X-API-KEY", "c9c408e6-3675-408b-9a6a-4027f534b2a8")
                .addHeader("Content-Type", "application/json")
                .build()
            it.proceed(request)
        }
        .build()

    private val retrofit: ApiRequest by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiRequest.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)
    }

}