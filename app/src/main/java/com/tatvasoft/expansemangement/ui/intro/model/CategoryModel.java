package com.tatvasoft.expansemangement.ui.intro.model;

import java.io.Serializable;

public class CategoryModel implements Serializable {

    private String category;


    public CategoryModel( String category) {

        this.category = category;


    }


    public String getCategory() {
        return category;
    }

    public void setBlogTitle(String category) {
        this.category = category;
    }

}
