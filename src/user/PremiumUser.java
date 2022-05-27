package user;

import foodPlatform.Subscription;

public class PremiumUser extends User {
    private Subscription subscription;
    private String cardNumber;

    private PremiumUser() { }

    public PremiumUser(Integer id, String name, String address, String city, Subscription subscription, String cardNumber) {
        super(id, name, address, city);
        this.subscription = subscription;
        this.cardNumber = cardNumber;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;

        if(!(o instanceof User))
            return false;

        PremiumUser p = (PremiumUser) o;

        return super.equals((User) p)
                && subscription.equals(p.subscription)
                && cardNumber.equals(p.cardNumber);
    }
}
