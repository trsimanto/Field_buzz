package com.towhid.fieldbuzz.network.api

import com.towhid.fieldbuzz.network.model.file.FileRes
import com.towhid.fieldbuzz.network.model.form_info.request.FormInfoReq
import com.towhid.fieldbuzz.network.model.form_info.response.FormInfoRes
import com.towhid.fieldbuzz.network.model.sign_in.request.SignInReq
import com.towhid.fieldbuzz.network.model.sign_in.response.SignInRes
import io.reactivex.Flowable
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @POST("login/")
    fun SignInRes_(
            @Body signInReq: SignInReq
    ): Flowable<SignInRes>

    @POST("v1/recruiting-entities/")
    fun FormInfoRes_(
            @Body formInfoReq: FormInfoReq,
            @Header("Authorization") token: String
    ): Call<FormInfoRes>
    @Multipart
    @PUT("file-object/{fileTokenId}/")
    fun uploadfile(
            @Path("fileTokenId") fileTokenId: Int,
            @Header("Authorization") token: String,
            @Part recfile: MultipartBody.Part
    ): Call<FileRes>
}
