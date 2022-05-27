package restaurant;

import java.util.*;

public class Restaurant {
    private String name;
    private String foodType;
    private Set<Dishes> menu;
    private String city;
    private Float deliveryFee;

    private Restaurant() { }

    public Restaurant(String name, String foodType, Set<Dishes> menu, String city, Float deliveryFee) {
        this.name = name;
        this.foodType = foodType;
        this.menu = menu;
        this.city = city;
        this.deliveryFee = deliveryFee;

    }

    public String getName() {
        return name;
    }

    public String getFoodType() {
        return foodType;
    }

    public Set<Dishes> getMenu() {
        return menu;
    }

    public String getCity() {
        return city;
    }

    public Float getDeliveryFee() { return deliveryFee; }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public void setMenu(Set<Dishes> menu) {
        this.menu = menu;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDeliveryFee(Float deliveryFee) { this.deliveryFee = deliveryFee; }
}
