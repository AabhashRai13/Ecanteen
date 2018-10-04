package com.example.aabhashgod.ecanteen.model;

public class MenuModel {
    private String name, shortDetail, price;
    private int foodId;

    public MenuModel(String name, String shortDetail, String price, int foodId){
        this.name = name;
        this.shortDetail = shortDetail;
        this.price = price;
        this.foodId = foodId;
    }
    public  void addQuantity(String quantity){
        shortDetail = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDetail() {
        return shortDetail;
    }

    public void setShortDetail(String shortDetail) {
        this.shortDetail = shortDetail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
