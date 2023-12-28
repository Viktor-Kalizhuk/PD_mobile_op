//package com.example.antikolektor.RoomDatabase
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class UserViewModel(aplication:Application):AndroidViewModel(aplication) {
//
//    val readAllUser:LiveData<List<User>>
//
//    private  val repository:UserRepository
//
//    val readAllBase:LiveData<List<Base>>
//
//    private  val repositor:UserRepository
//
//    init {
//        val userDao = UserDatabase.getDatabase(aplication).userDao()
//        repository = UserRepository(userDao)
//        readAllUser = repository.readAllUser
//    }
//
//    init {
//        val userDao = UserDatabase.getDatabase(aplication).userDao()
//        repositor = UserRepository(userDao)
//        readAllBase = repositor.readAllBase
//    }
//
//
//    fun addUser(user: User){
//        viewModelScope.launch (Dispatchers.IO){
//            repository.addUser(user)
//        }
//    }
//
//    fun addBase(user: Base){
//        viewModelScope.launch (Dispatchers.IO){
//            repository.addBase(user)
//        }
//    }
//
//    fun updateBase(user:Base){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.updateBase(user)
//        }
//    }
//
//    fun updateUser(user:User){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.updateUser(user)
//        }
//    }
//    fun deleteUser(user:User){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteUser(user)
//        }
//    }
//}