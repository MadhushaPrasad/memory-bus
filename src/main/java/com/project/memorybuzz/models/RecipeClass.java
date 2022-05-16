package com.project.memorybuzz.models;

import android.graphics.Bitmap;

public class RecipeClass {
    private String recipeName,recipe,image;

    public RecipeClass(){

    }


    public RecipeClass(String recipeName, String recipe,String img) {
        this.recipeName = recipeName;
        this.recipe = recipe;
        this.image = img;

    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
