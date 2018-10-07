package com.example.aabhashgod.ecanteen.model;

import java.io.Serializable;

public class MenuModel implements Serializable {
    private String name;
    private int foodId,price,shortDetail;

    public MenuModel(String name, int shortDetail, int price, int foodId){
        this.name = name;
        this.shortDetail = shortDetail;
        this.price = price;
        this.foodId = foodId;
    }

    public MenuModel() {

    }

    public  void addQuantity(int quantity){
        shortDetail = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShortDetail() {
        return shortDetail;
    }

    public void setShortDetail(int shortDetail) {
        this.shortDetail = shortDetail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName(){
        return name;
    }
}
