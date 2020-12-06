package com.pucmm.loginandmainpage.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "category_table")
public class CategoryData implements Serializable {
    //Create id column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "category_name")
    private String categoryName;

    @ColumnInfo(name = "category_img")
    private Byte categoryImg;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Byte getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(Byte categoryImg) {
        this.categoryImg = categoryImg;
    }

}
