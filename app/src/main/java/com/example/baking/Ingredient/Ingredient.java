package com.example.baking.Ingredient;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient implements Serializable {
    @SerializedName("quantity")
    private double quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String toString(){
        return ingredient + " " + quantity + " " + measure;
    }
}
