package com.example.antikolektor.RoomDatabase


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBase(user: Base)

    @Query("SELECT * FROM user_table ")
    fun readAllUser():LiveData<List<User>>

    @Query("SELECT * FROM user_table ")
    fun readAllDataNoLiveData():List<User>

    @Query("SELECT * FROM base_phones ")
    fun readAllDataBaseNoLiveData():List<Base>

    @Query("SELECT * FROM base_phones ")
    fun readAllBase():LiveData<List<Base>>

    @Update
    suspend fun updateBase(user:Base)

    @Update
    suspend fun updateUser(user:User)

    @Delete
    suspend fun deleteUser(user:User)
}