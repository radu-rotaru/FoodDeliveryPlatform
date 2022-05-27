package foodPlatform;

import user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Subscription {
    private Integer subscriptionId;
    private boolean freeDelivery;
    private Integer discount;
    private Float price;

    private Subscription() { }

    public Subscription(Integer id, boolean freeDelivery, Integer discount, Float price) {
        this.subscriptionId = id;
        this.freeDelivery = freeDelivery;
        this.discount = discount;
        this.price = price;
    }

    public Subscription(ResultSet res) throws SQLException {
        this.subscriptionId = res.getInt("subscriptionsId");
        this.freeDelivery = res.getBoolean("freeDelivery");
        this.discount = res.getInt("discount");
        this.price = res.getFloat("price");
    }


    public Integer getSubscriptionId() { return subscriptionId; }

    public boolean isFreeDelivery() {
        return freeDelivery;
    }

    public Integer getDiscount() {
        return discount;
    }

    public Float getPrice() {
        return price;
    }

    public void setFreeDelivery(boolean freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;

        if(!(o instanceof User))
            return false;

        Subscription s = (Subscription) o;

        return subscriptionId == s.getSubscriptionId()
                && freeDelivery == s.isFreeDelivery()
                && discount == s.getDiscount()
                && price == s.getPrice();
    }
}
