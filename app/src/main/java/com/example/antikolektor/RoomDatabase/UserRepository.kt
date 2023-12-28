package com.example.antikolektor.RoomDatabase

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val readAllUser : LiveData<List<User>> = userDao.readAllUser()

    val readAllBase : LiveData<List<Base>> = userDao.readAllBase()

    suspend fun readAllDataNoLiveData():List<User>{
        return userDao.readAllDataNoLiveData()
    }
    suspend fun readAllDataBaseNoLiveData():List<Base>{
        return userDao.readAllDataBaseNoLiveData()
    }
    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun addBase(user: Base){
        userDao.addBase(user)
    }

    suspend fun updateBase(user:Base){
        userDao.updateBase(user)
    }

    suspend fun updateUser(user:User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user:User){
        userDao.deleteUser(user)
    }
}