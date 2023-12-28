package com.example.antikolektor.Server

import android.text.Html
import com.example.antikolektor.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

data class ApiClient(
    private val apiService: ApiService
) {
    suspend fun getToken(login: String, password: String): Response<String> {
        return apiService.getToken(login, password)
    }

    suspend fun getCode(login: String): Response<Code> {
        return apiService.getCode(login)
    }

    suspend fun getRegenerateCode(login: String): Response<String> {
        return apiService.getRegenerateCode(login)
    }

    suspend fun getBasePhones(token: String): Response<DataByResponse> {
        return apiService.getBasePhones(token)
    }

    suspend fun getUserBasePhones(token: String): Response<DataByResponse> {
        return apiService.getUserBasePhones(token)
    }


    suspend fun postUserBasePhones(
        phone: String,
        comment: String,
        type: String,
        subtype: String,
        token: String
    ): Response<String> {
        return apiService.addUserBasePhones(
            phone.toString(),
            comment.toString(),
            type.toString(),
            subtype.toString(),
            token
        )
    }

    suspend fun deleteUserPhones(id: Int, token: String): Response<String> {
        return apiService.deleteUserPhones(id, token)
    }

    suspend fun getUserReittances(token: String): Response<DataListHist> {
        return apiService.getUserReittances(token)
    }

    suspend fun getFeedback(token: String): Response<String> {
        return apiService.getFeedback(token)
    }

    suspend fun getNotifications(token: String): Response<DataListNotif> {
        return apiService.getNotifications(token)
    }

    suspend fun readNotification(id: String, token: String): Response<String> {
        return apiService.readNotification(readNotification(id), token)
    }

    suspend fun addFurtherPhone(phone: String, token: String): Response<String> {
        return apiService.addFurtherPhone(phone, token)
    }

    suspend fun confirmFurtherPhone(phone: String, code: String, token: String): Response<String> {
        return apiService.confirmFurtherPhone(phone, code, token)
    }

    suspend fun getUserStages(token: String): Response<DataListStages> {
        return apiService.getUserStages(token)
    }

    suspend fun sendRating(
        overall_score: Int,
        professionalism_score: Int,
        courtesy_score: Int,
        employee_feedback: String,
        company_feedback: String,
        help_feedback: String,
        improve_feedback: String,
        header: String
    ): Response<String> {
        return apiService.sendRating(
            overall_score,
            professionalism_score,
            courtesy_score,
            employee_feedback,
            company_feedback,
            help_feedback,
            improve_feedback,
            header
        )
    }

    suspend fun getProfile(header: String): Response<DataProfile> {
        return apiService.getProfile(header)
    }

    suspend fun getUserPanel(header: String): Response<DataListUserPanel> {
        return apiService.getUserPanel(header)
    }

    suspend fun postDirectory(query: String, header: String): Response<DataListDirectory> {
        return apiService.postDirectory(Query(query), header)
    }

    suspend fun makeRemittance(amount: String, header: String): Response<IdRemittance> {
        return apiService.makeRemittance(amount, header)
    }

    suspend fun getClientFiles(header: String): Response<MyDocumentDataStruct> {
        return apiService.getClientFiles(header)
    }

    suspend fun updateProfile(id: Int, userData: putProfile, token: String): Response<String> {
        return apiService.updateProfile(id, userData, token)
    }

    suspend fun updateProfileStepTwo(
        id: Int,
        userData: putProfileStepTwo,
        token: String
    ): Response<String> {
        return apiService.updateProfileStepTwo(id, userData, token)
    }

    suspend fun getCreditTitle(serch: String, header: String): Response<DataSerch> {
        return apiService.getCreditTitle(serch, header)
    }

    suspend fun deleteCredit(id: Int, header: String): Response<String> {
        return apiService.deleteCredit(id, header)
    }

    suspend fun updateCredit(
        id: Int,
        title: String,
        bank_id: Int,
        amount_of_credit: String,
        monthly_paiment: String,
        documents_you_have: String,
        token: String
    ): Response<String> {
        return apiService.updateCredit(
            id,
            title,
            bank_id,
            amount_of_credit,
            monthly_paiment,
            documents_you_have,
            token
        )
    }

    suspend fun addCredit(token: String): Response<IdCredit> {
        return apiService.addCredit(token)
    }

    suspend fun readAllNotification(token: String): Response<String> {
        return apiService.readAllNotification(token)
    }

    suspend fun deleteFile(id: Int, token: String): Response<String> {
        return apiService.deleteFile(id, token)
    }

    suspend fun addFile(
        file_type_id: Int,
        file: List<MultipartBody.Part>,
        token: String
    ): Response<List<DataFile>> {
        return apiService.addFile(file_type_id, file, token)
    }

    suspend fun addFileCredit(
        file_type_id: Int,
        credit_id: Int,
        file: List<MultipartBody.Part>,
        token: String
    ): Response<List<DataFile>> {
        return apiService.addFileCredit(file_type_id, credit_id, file, token)
    }

    suspend fun getClientExchangeFiles(token: String): Response<List<SharedDataDocument>> {
        return apiService.getClientExchangeFiles(token)
    }

    suspend fun getViewStages(id: Int, header: String): Response<ResponseBody> {
        return apiService.getViewStages(id, header)
    }

    suspend fun deleteFurtherPhone(phone: String, header: String): Response<String> {
        return apiService.deleteFurtherPhone(PhoneProfile(phone), header)
    }

    suspend fun changeCode(code: String, header: String): Response<String> {
        return apiService.changeCode(ChangeCode(code), header)
    }

}
