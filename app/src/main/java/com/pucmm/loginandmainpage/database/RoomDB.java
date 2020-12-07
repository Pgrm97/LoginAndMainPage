package com.pucmm.loginandmainpage.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Add database entities
@Database(entities = {UserData.class,CategoryData.class,ProductData.class}, version = 3, exportSchema = false)

public abstract class RoomDB extends RoomDatabase {
    //Create database instance
    private static RoomDB database;
    //Define database
    private static String DATABASE_NAME = "ProductManager2";

    public synchronized static RoomDB getInstance(Context context){
        //Check condition
        if (database == null){
            // When database is null
            // Initialize database
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        //Return database
        return database;
    }

    //Create Dao
    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();
    public abstract ProductDao productDao();
}
