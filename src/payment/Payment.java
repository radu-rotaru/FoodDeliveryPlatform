package payment;

import user.User;
import restaurant.Restaurant;

public class Payment {
    private Float sum;
    private User sender;
    private Restaurant receiver;

    protected Payment() { }

    public Payment(Float sum, User sender, Restaurant receiver) {
        this.sum = sum;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Float getSum() {
        return sum;
    }

    public User getSender() {
        return sender;
    }

    public Restaurant getReceiver() {
        return receiver;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(Restaurant receiver) {
        this.receiver = receiver;
    }
}
