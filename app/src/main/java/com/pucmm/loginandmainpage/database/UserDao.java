package com.pucmm.loginandmainpage.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(UserData userData);

    //Delete query
    @Delete
    void delete(UserData userData);

    //Delete all query
    @Delete
    void reset(List<UserData> userData);

    //Get all data query
    @Query("SELECT * FROM user_table")
    List<UserData> getAll();

    //Get password from userName for Login.
    @Query("SELECT password FROM user_table WHERE user_name = :userName")
    List<String> getPasswordForLogin(String userName);

    //Get password from email for Forgot Password.
    @Query("SELECT password FROM user_table WHERE email = :email")
    List<String> getPasswordFromEmail(String email);
}
