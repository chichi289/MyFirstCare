package com.myfirstcare.app.db.dao

import androidx.room.*
import com.myfirstcare.app.db.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE email LIKE :email")
    fun getUserByEmail(email: String): User

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)
}