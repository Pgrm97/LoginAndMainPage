package com.pucmm.loginandmainpage.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "category_id")
    private int IDCategory;

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    @Ignore
    private int Cantidad;
    @Ignore
    public boolean selected;


    @ColumnInfo(name = "product_image")
    private String productImage;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIDCategory() {
        return IDCategory;
    }

    public void setIDCategory(int IDCategory) {
        this.IDCategory = IDCategory;
    }
    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

}
