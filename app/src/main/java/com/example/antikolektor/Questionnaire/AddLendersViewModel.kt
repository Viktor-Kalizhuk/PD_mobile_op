package com.example.antikolektor.Questionnaire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.antikolektor.DataFile
import com.example.antikolektor.DataSerch
import com.example.antikolektor.Documents.MyDocumentRepository
import com.example.antikolektor.IdCredit
import com.example.antikolektor.MyDocumentDataStruct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class AddLendersViewModel : ViewModel() {
    private val repository = AddLendersRepository()

    private val _updateByLiveData = MutableLiveData<String?>()
    val updateByLiveData: LiveData<String?> = _updateByLiveData

    private val _addCreditByLiveData = MutableLiveData<IdCredit?>()
    val addCreditByLiveData: LiveData<IdCredit?> = _addCreditByLiveData

    private val _getCreditTitleByLiveData = MutableLiveData<DataSerch?>()
    val getCreditTitleByLiveData: LiveData<DataSerch?> = _getCreditTitleByLiveData

    private val _deleteCreditTitleByLiveData = MutableLiveData<String?>()
    val deleteCreditTitleByLiveData: LiveData<String?> = _deleteCreditTitleByLiveData

    private val _deleteFileCreditTitleByLiveData = MutableLiveData<String?>()
    val deleteFileCreditTitleByLiveData: LiveData<String?> = _deleteFileCreditTitleByLiveData

    private val _fileByLiveData = MutableLiveData<List<DataFile>?>()
    val fileByLiveData: LiveData<List<DataFile>?> = _fileByLiveData

    fun updateCredit(
        id: Int,
        title: String,
        bank_id: Int,
        amount_of_credit: String,
        monthly_paiment: String,
        documents_you_have: String,
        token: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.updateCredit(
                id,
                title,
                bank_id,
                amount_of_credit,
                monthly_paiment,
                documents_you_have,
                token
            )

            _updateByLiveData.postValue(response)
        }
    }

    fun addCredit(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.addCredit(token)

            _addCreditByLiveData.postValue(response)
        }
    }

    fun getCreditTitle(serch: String, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCreditTitle(serch, token)

            _getCreditTitleByLiveData.postValue(response)
        }
    }

    fun deleteCredit(id: Int, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.deleteCredit(id, token)

            _deleteCreditTitleByLiveData.postValue(response)
        }
    }
    fun addFile(file_type_id:Int,credit_id:Int, file:List<MultipartBody.Part>, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.addFileCredit(file_type_id,credit_id,file,token)

            _fileByLiveData.postValue(response)
        }
    }

    fun deleteFileCredit(id: Int, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.deleteFileCredit(id, token)

            _deleteFileCreditTitleByLiveData.postValue(response)
        }
    }
}