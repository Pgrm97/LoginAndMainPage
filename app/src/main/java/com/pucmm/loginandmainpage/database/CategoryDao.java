package com.pucmm.loginandmainpage.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CategoryDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(CategoryData categoryData);

    //Delete query
    @Delete
    void delete(CategoryData categoryData);

    //Delete all query
    @Delete
    void reset(List<CategoryData> categoryData);

    //Get all data query
    @Query("SELECT category_name FROM category_table")
   String [] getAll();


}
