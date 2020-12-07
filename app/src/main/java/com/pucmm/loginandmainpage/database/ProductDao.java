package com.pucmm.loginandmainpage.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProductDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(ProductData productData);

    //Delete query
    @Delete
    void delete(ProductData productData);

    //Delete all query
    @Delete
    void reset(List<ProductData> productData);

    //Get all data query
    @Query("SELECT product_code FROM product_table")
    String [] getAll();


}
