package com.towhid.fieldbuzz.network.error

import com.towhid.fieldbuzz.network.api.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

object ErrorUtils2 {
    fun parseError(response: Response<*>): ApiError {
        val converter: Converter<ResponseBody, ApiError> = RetrofitClient.getInstance().getRetrofit()
                .responseBodyConverter(ApiError::class.java, arrayOfNulls<Annotation>(0))
        val error: ApiError
        error = try {
            converter.convert(response.errorBody()!!)!!
        } catch (e: IOException) {
            return ApiError()
        }
        return error
    }
}