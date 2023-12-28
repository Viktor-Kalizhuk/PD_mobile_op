package com.example.antikolektor.More.AntiKolektor

import android.app.Application
import androidx.lifecycle.*
import com.example.antikolektor.RoomDatabase.Base
import com.example.antikolektor.RoomDatabase.User
import com.example.antikolektor.RoomDatabase.UserDatabase
import com.example.antikolektor.RoomDatabase.UserRepository
import com.example.antikolektor.Server.DataByResponse
import com.example.antikolektor.Server.ViewModel.SharedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelBlackList(aplication: Application): AndroidViewModel(aplication)  {
    private val repository = SharedRepository()


    val readAllUser:LiveData<List<User>>

    private  var repositorDB:UserRepository

    val readAllBase:LiveData<List<Base>>


    init {
        val userDao = UserDatabase.getDatabase(aplication).userDao()
        repositorDB = UserRepository(userDao)
        readAllUser = repositorDB.readAllUser
    }

    init {
        val userDao = UserDatabase.getDatabase(aplication).userDao()
        repositorDB = UserRepository(userDao)
        readAllBase = repositorDB.readAllBase
    }


    private val _basePhonesByLiveData = MutableLiveData<DataByResponse?>()
    val basePhonesByIdLiveData: LiveData<DataByResponse?> = _basePhonesByLiveData

    private val _baseUserPhonesByLiveData = MutableLiveData<DataByResponse?>()
    val baseUserPhonesByIdLiveData: LiveData<DataByResponse?> = _baseUserPhonesByLiveData

    private val _postbaseUserPhonesByLiveData = MutableLiveData<String?>()
    val posbaseUserPhones: LiveData<String?> = _postbaseUserPhonesByLiveData

    private val _deliateByLiveData = MutableLiveData<String?>()
    val deleteUserPhone: LiveData<String?> = _deliateByLiveData

    fun basePhones(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getBasePhones(token)

            _basePhonesByLiveData.postValue(response)
        }
    }
    fun baseUserPhones(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserBasePhones(token)

            _baseUserPhonesByLiveData.postValue(response)
        }
    }
    fun postUserPhones(phone:String,comment:String,type:String,subtype:String,token:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.postUserBasePhones(phone.toString(),comment.toString(),type.toString(),subtype.toString(),token)

            _postbaseUserPhonesByLiveData.postValue(response)
        }
    }

    fun deleteUserPhones(id:Int,token:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.deleteUserPhones(id,token)

            _deliateByLiveData.postValue(response)
        }
    }


    fun addUser(user: User){
        viewModelScope.launch (Dispatchers.IO){
            repositorDB.addUser(user)
        }
    }

    fun addBase(user: Base){
        viewModelScope.launch (Dispatchers.IO){
            repositorDB.addBase(user)
        }
    }

    fun updateBase(user: Base){
        viewModelScope.launch(Dispatchers.IO) {
            repositorDB.updateBase(user)
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repositorDB.updateUser(user)
        }
    }
    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repositorDB.deleteUser(user)
        }
    }


}