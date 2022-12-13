@file:Suppress("UNCHECKED_CAST")

package com.junopark.kpapi.data.api

import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHandler {

    private suspend fun <T: Any> handleApi(
        execute: () -> Response<T>
    ) : ApiResult {
        return try {
            val response = execute()
            val body = response.body()
            if(response.isSuccessful && body != null) {
                ApiResult.ApiSuccess(data = body as List<Any>)
            } else {
                ApiResult.ApiError(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            ApiResult.ApiError(code = e.code(), message = e.localizedMessage)
        } catch (e: Throwable) {
            ApiResult.ApiException(e)
        }
    }

    private val retrofit: ApiRequest by lazy {
        Retrofit.Builder()
            .baseUrl(ApiRequest.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)
    }

    suspend fun execute(request: () -> Call<*>) : ApiResult {
        return when(val res = handleApi { request.invoke().execute() } ) {
            is ApiResult.ApiSuccess -> ApiResult.ApiSuccess(data = res.data)
            is ApiResult.ApiError -> ApiResult.ApiError(code = res.code, message = res.message)
            is ApiResult.ApiException -> ApiResult.ApiException(e = res.e)
        }
    }


}