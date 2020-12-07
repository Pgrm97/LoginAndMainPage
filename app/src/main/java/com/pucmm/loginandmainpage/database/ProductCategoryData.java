package com.pucmm.loginandmainpage.database;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ProductCategoryData {
    @Embedded
    public CategoryData categoryData;
    @Relation(
            parentColumn = "ID",
            entityColumn = "category_id"
    )
    public ProductData productData ;

}
