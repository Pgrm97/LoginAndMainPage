package com.pucmm.loginandmainpage.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "product_table")
public class ProductData implements Serializable {
    //Create id column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "product_code")
    private String productCode;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "product_img")
    private byte[] productImg;

    @ColumnInfo(name = "price")
    private String price;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getProductImg() {
        return productImg;
    }

    public void setProductImg(byte[] productImg) {
        this.productImg = productImg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
