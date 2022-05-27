package services.read;

import foodPlatform.Subscription;
import services.Services;

public class SubscriptionReader implements Reader {
    private static SubscriptionReader instance = null;

    private SubscriptionReader() {}

    public static SubscriptionReader getInstance() {
        if(instance == null)
            instance = new SubscriptionReader();

        return instance;
    }

    public void read(String[] attrs) {
        Subscription subscription = new Subscription(1, Boolean.parseBoolean(attrs[0]), Integer.parseInt(attrs[1]), Float.parseFloat(attrs[2]));

        // Cod partea a 2-a
//        List<Subscription> subscriptionList = Services.getSubscriptions();
//        subscriptionList.add(subscription);
//        Services.setSubscriptions(subscriptionList);
        Services.subscriptionAdd(subscription);
    }
}
