package foodPlatform;

import payment.Payment;
import restaurant.Dishes;

import java.util.List;

public class Order {
    private Payment payment;
    private Courier courier;
    private List<Dishes> dishes;

    private Order() { }

    public Order(Payment payment, Courier courier, List<Dishes> dishes) {
        this.payment = payment;
        this.courier = courier;
        this.dishes = dishes;
    }

    public Payment getPayment() {
        return payment;
    }

    public Courier getCourier() {
        return courier;
    }

    public List<Dishes> getDishes() {
        return dishes;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public void setDishes(List<Dishes> dishes) {
        this.dishes = dishes;
    }
}
