package com.example.antikolektor.Server

import android.text.Html
import com.example.antikolektor.*
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Query

interface ApiService {
    @GET("getToken")
    suspend fun getToken(
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<String>


    @GET("getCode")
    suspend fun getCode(
        @Query("login") login: String
    ): Response<Code>

    @GET("regenerateCode")
    suspend fun getRegenerateCode(
        @Query("login") login: String
    ): Response<String>

    @GET("getBasePhones")
    suspend fun getBasePhones(
        @Header("Authorization") header: String
    ): Response<DataByResponse>

    @GET("getUserPhones")
    suspend fun getUserBasePhones(
        @Header("Authorization") header: String
    ): Response<DataByResponse>

    @GET("getUserStages")
    suspend fun getUserStages(
        @Header("Authorization") header: String
    ): Response<DataListStages>

    @POST("addUserPhones")
    suspend fun addUserBasePhones(
        @Query("phone") phone: String,
        @Query("comment") comment: String,
        @Query("type") type: String,
        @Query("subtype") subtype: String,
        @Header("Authorization") header: String
    ): Response<String>

    @DELETE("deleteUserPhones/{id}")
    suspend fun deleteUserPhones(
        @Path("id") id: Int,
        @Header("Authorization") header: String
    ): Response<String>

    @GET("getUserRemittances")
    suspend fun getUserReittances(
        @Header("Authorization") header: String
    ): Response<DataListHist>

    @GET("getFeedback")
    suspend fun getFeedback(
        @Header("Authorization") header: String
    ): Response<String>

    @GET("getNotifications")
    suspend fun getNotifications(
        @Header("Authorization") header: String
    ): Response<DataListNotif>

    @POST("readNotification")
    suspend fun readNotification(
        @Body id: readNotification,
        @Header("Authorization") header: String
    ): Response<String>

    @POST("addFurtherPhone")
    suspend fun addFurtherPhone(
        @Query("phone") phone: String,
        @Header("Authorization") header: String
    ): Response<String>

    @POST("confirmFurtherPhone")
    suspend fun confirmFurtherPhone(
        @Query("phone") phone: String,
        @Query("code") code: String,
        @Header("Authorization") header: String
    ): Response<String>

    @POST("sendRating")
    suspend fun sendRating(
        @Query("overall_score") overall_score: Int,
        @Query("professionalism_score") professionalism_score: Int,
        @Query("courtesy_score") courtesy_score: Int,
        @Query("employee_feedback") employee_feedback: String,
        @Query("company_feedback") company_feedback: String,
        @Query("help_feedback") help_feedback: String,
        @Query("improve_feedback") improve_feedback: String,
        @Header("Authorization") header: String
    ): Response<String>

    @GET("getProfile")
    suspend fun getProfile(
        @Header("Authorization") header: String
    ): Response<DataProfile>

    @GET("getUserPanel")
    suspend fun getUserPanel(
        @Header("Authorization") header: String
    ): Response<DataListUserPanel>

    @POST("rs/suggest/address")
    suspend fun postDirectory(
        @Body query: com.example.antikolektor.Query,
        @Header("Authorization") header: String
    ): Response<DataListDirectory>

    @POST("makeRemittance")
    suspend fun makeRemittance(
        @Query("amount") amount: String,
        @Header("Authorization") header: String
    ): Response<IdRemittance>

    @GET("getClientFiles")
    suspend fun getClientFiles(
        @Header("Authorization") header: String
    ): Response<MyDocumentDataStruct>

    @POST("addCredit")
    suspend fun addCredit(
        @Header("Authorization") header: String
    ): Response<IdCredit>

    @PUT("updateCredit/{id}")
    suspend fun updateCredit(
        @Path("id") id: Int,
        @Query("title") title: String,
        @Query("bank_id") bank_id: Int,
        @Query("balance_owed") balance_owed: String,
        @Query("monthly_payment") monthly_payment: String,
        @Query("documents_you_have") documents_you_have: String,
        @Header("Authorization") header: String
    ): Response<String>

    @GET("getCreditTitle")
    suspend fun getCreditTitle(
        @Query("serch") serch: String,
        @Header("Authorization") header: String
    ): Response<DataSerch>

    @DELETE("deleteCredit/{id}")
    suspend fun deleteCredit(
        @Path("id") id: Int,
        @Header("Authorization") header: String
    ): Response<String>

    @PUT("updateProfile/{id}")
    suspend fun updateProfile(
        @Path("id") id: Int,
        @Body userData: putProfile,
        @Header("Authorization") header: String
    ): Response<String>


    @PUT("updateProfile/{id}")
    suspend fun updateProfileStepTwo(
        @Path("id") id: Int,
        @Body userData: putProfileStepTwo,
        @Header("Authorization") header: String
    ): Response<String>

    @POST("readAllNotification")
    suspend fun readAllNotification(
        @Header("Authorization") header: String
    ): Response<String>

    @DELETE("deleteFile/{id}")
    suspend fun deleteFile(
        @Path("id") id: Int,
        @Header("Authorization") header: String
    ): Response<String>


    @POST("addFile")
    @Multipart
    suspend fun addFile(
        @Part("file_type_id") file_type_id: Int,
        @Part filelist: List<MultipartBody.Part>,
        @Header("Authorization") header: String
    ): Response<List<DataFile>>

    @POST("addFile")
    @Multipart
    suspend fun addFileCredit(
        @Part("file_type_id") file_type_id: Int,
        @Part("credit_id") credit_id: Int,
        @Part filelist: List<MultipartBody.Part>,
        @Header("Authorization") header: String
    ): Response<List<DataFile>>

    @GET("getClientExchangeFiles")
    suspend fun getClientExchangeFiles(
        @Header("Authorization") header: String
    ): Response<List<SharedDataDocument>>


    @GET("getViewStage/{id}")
    suspend fun getViewStages(
        @Path("id") id: Int,
        @Header("Authorization") header: String
    ): Response<ResponseBody>

    @POST("deleteFurtherPhone")
    suspend fun deleteFurtherPhone(
        @Body phone: PhoneProfile,
        @Header("Authorization") header: String
    ): Response<String>


    @POST("changeCode")
    suspend fun changeCode(
        @Body code: ChangeCode,
        @Header("Authorization") header: String
    ): Response<String>
}