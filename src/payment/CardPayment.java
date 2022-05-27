package payment;

import user.User;
import restaurant.Restaurant;

public class CardPayment extends Payment {
    private int tips;
    private String cardNumber;

    private CardPayment() {
        super();
    }

    public CardPayment(Float sum, User sender, Restaurant receiver, int tips, String cardNumber) {
        super(sum, sender, receiver);
        this.tips = tips;
        this.cardNumber = cardNumber;
    }

    public Integer getTips() {
        return tips;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setTips(Integer tips) {
        this.tips = tips;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
