package com.pucmm.loginandmainpage.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProductDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(ProductData productData);

    @Update
    void update(ProductData productData);

    //Delete query
    @Delete
    void delete(ProductData productData);

    //Delete all query
    @Delete
    void reset(List<ProductData> productData);

    //Get all data query
    @Query("SELECT * FROM product_table")
    List<ProductData> getAll();

    @Transaction
    @Query("SELECT * FROM product_table")
    public List<ProductCategoryData> getProductsCategories();

    @Query("SELECT * FROM product_table WHERE ID = :id")
    public ProductData get(int id);

    @Query("SELECT * FROM product_table WHERE category_id = :id")
    public List<ProductData> getByCategory(int id);


}
