package services;

import foodPlatform.*;
import payment.*;
import restaurant.*;
import services.databases.CourierDatabase;
import services.databases.DishesDatabase;
import services.databases.SubscriptionDatabase;
import services.databases.UserDatabase;
import user.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Services {
    private static List<Order> orders = new ArrayList<>();
    private static List<Restaurant> restaurants = new ArrayList<>();
//    private static List<User> users = new ArrayList<>();
//    private static List<Courier> couriers = new ArrayList<>();
//    private static List<Subscription> subscriptions = new ArrayList<>();
//    private static List<Dishes> dishes = new ArrayList<>();
    private static CourierDatabase courierDatabase = null;
    private static SubscriptionDatabase subscriptionDatabase = null;
    private static DishesDatabase dishesDatabase = null;
    private static UserDatabase userDatabase = null;

    private Services() {}

    // Getters
    public static List<Order> getOrders() {
        return orders;
    }

    public static List<User> getUsers() {
        return userDatabase.read();
    }

    public static List<Courier> getCouriers() {
        return courierDatabase.read();
    }

    public static List<Subscription> getSubscriptions() {
        return subscriptionDatabase.read();
    }

    public static List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public static List<Dishes> getDishes() {return dishesDatabase.read(); }

    // Setters
    public static void setOrders(List<Order> orderList) {
        orders = orderList;
    }

    public static void setUsers(UserDatabase userList) {
        userDatabase = userList;
    }

    public static void setCouriers(CourierDatabase courierList) {
        courierDatabase = courierList;
    }

    public static void setSubscriptions(SubscriptionDatabase subscriptionList) {
        subscriptionDatabase = subscriptionList;
    }

    public static void setRestaurants(List<Restaurant> restaurantList) {
        restaurants = restaurantList;
    }

    public static void setDishes(DishesDatabase dishesList) {
        dishesDatabase = dishesList;
    }

    // Actions
    public static void courierAdd(Courier c) {
        courierDatabase.create(c);
    }

    public static void dishAdd(Dishes d) {
        dishesDatabase.create(d);
    }

    public static void userAdd(User u) {
        userDatabase.create(u);
    }

    public static void subscriptionAdd(Subscription s) {
        subscriptionDatabase.create(s);
    }

    // Find a courier that does not have any other orders currently
    private static Courier findAvailableCourier() {
        List<Courier> couriers = courierDatabase.read();

        Courier courierForOrder =  couriers.stream()
                .filter(courier -> (courier.getAvailable()))
                .findAny()
                .orElse(null);

        // If we find a courier, then this courier becomes busy
        if(courierForOrder != null) {
            courierForOrder.setAvailable(false);
            courierDatabase.update(courierForOrder, courierForOrder.getCourierId());
        }

        return courierForOrder;
    }

    // Print the details of users that have ordered at least 2 times
    public static void getLoyalUsers() {
        boolean check = false;
        System.out.println("Loyal users:");

        List<User> users = userDatabase.read();
        // The sender(user) is stored in the Payment object of class
        for(User user : users) {
            long numberOfOrders = orders.stream()
                    .filter(order -> (order.getPayment().getSender().equals(user)))
                    .count();

            if(numberOfOrders >= 2) {
                check = true;
                System.out.println(String.format("  Name: %s, Address: %s, %s",
                        user.getName(), user.getCity(), user.getAddress()));
            }
        }
        if(!check)
            System.out.println("There are no loyal users.");
    }

    // Print info about the cities with the best reach
    // (most users, most orders, most restaurants)
    public static void getBestMarketPlaces() {
        System.out.println("Best cities for business:");

        List<String> cities = new ArrayList<>();
        for(Order order : orders) {
            cities.add(order.getPayment().getSender().getCity());
        }

        String mostOrders;
        if(cities.size() != 0) {
            mostOrders = getMaxFreq(cities);
            System.out.println(String.format("%s is the city with the most orders", mostOrders));
        }
        else
            System.out.println("There are no orders in the list");

        cities = new ArrayList<>();

        List<User> users = userDatabase.read();
        for(User user : users) {
            cities.add(user.getCity());
        }

        String mostUsers;
        if(cities.size() != 0) {
            mostUsers = getMaxFreq(cities);
            System.out.println(String.format("%s is the city with the most users", mostUsers));
        }
        else
            System.out.println("There are no users in the list");

        cities = new ArrayList<>();
        for(Restaurant restaurant : restaurants) {
            cities.add(restaurant.getCity());
        }

        String mostRestaurants;
        if(cities.size() != 0) {
            mostRestaurants = getMaxFreq(cities);
            System.out.println(String.format("%s is the city with the most restaurants", mostRestaurants));
        }
        else
            System.out.println("There are no restaurants in the list");
    }

    // Get restaurants from the city of the user,
    // the user is selected from list of users
    public static void getRestaurantsNearUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose user: ");
        printUsers();

        List<User> users = userDatabase.read();

        System.out.println("Enter index: ");
        int userIndex = in.nextInt();
        User user = users.get(userIndex - 1);

        String city = user.getCity();
        List<Restaurant> res = restaurants.stream()
                .filter(restaurant -> (restaurant.getCity().equals(city)))
                .toList();

        if(res.size() == 0)
            System.out.println("There are no restaurants in this city");
        else {
            System.out.println(String.format("Restaurant in %s:", city));
            for (Restaurant r : res) {
                System.out.println(String.format("  %s", r.getName()));
            }
        }
    }

    // Unsubscribe User, transform premium User to default User
    public static void unsubscribeUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("Unsubscribe premium user: ");
        printUsers();

        List<User> users = userDatabase.read();

        System.out.println("Enter index: ");
        int userIndex = in.nextInt();

        // Check if selected user is indeed a premium user
        User u = users.get(userIndex - 1);
        if(!(u instanceof PremiumUser)) {
            System.out.println("This user is not premium");
            System.out.println("No user was unsubscribed");
            return;
        }
        else {
            PremiumUser premUser = (PremiumUser) u;

            User defaultUser = new User(1, premUser.getName(), premUser.getAddress(), premUser.getCity());
            userDatabase.update(defaultUser, u.getUserId());

            System.out.println("User was unsubscribed");
            printUsers();
        }
    }

    // Add order to the list, the user, restaurant and products
    // are selected from their respective lists, the courier is searched
    // in the list of couriers, if the user is a Premium user, the
    // payment is done via card. Otherwise, the order was paid with cash.
    public static void addOrder() {
        Scanner in = new Scanner(System.in);
        System.out.println("Adding order...");

        Courier courierForOrder = findAvailableCourier();

        if(courierForOrder == null) {
            System.out.println("There are no available couriers at the moment. Please try again.");
            return;
        }

        System.out.println("Choose user: ");
        printUsers();

        List<User> users = userDatabase.read();

        System.out.println("Enter index: ");
        int userIndex = in.nextInt();
        User user = users.get(userIndex - 1);

        System.out.println("Choose restaurant: ");
        printRestaurants();

        System.out.println("Enter index: ");
        int restaurantIndex = in.nextInt();
        Restaurant restaurant = restaurants.get(restaurantIndex - 1);

        System.out.println("Choose products: ");
        System.out.println("Menu: ");
        printDishes(restaurant.getMenu().stream().toList());

        List<Dishes> dishes = new ArrayList<>();
        int dishIndex;
        while(true) {
            System.out.println("Enter index(or -1 to finalize): ");
            dishIndex = in.nextInt();
            if(dishIndex != -1)
                dishes.add(restaurant.getMenu().stream().toList().get(dishIndex - 1));
            else
                break;
        }

        float total = 0;
        for(Dishes dish : dishes) {
            total += dish.getPrice();
        }

        System.out.println("Do you want to give tips to your courier(if not - enter 0, otherwise - enter the amount)?");
        int tips = in.nextInt();

        Payment p;
        if((user instanceof PremiumUser)) {
            PremiumUser x = (PremiumUser) user;

            total = (100 - x.getSubscription().getDiscount()) * total / 100;
            if(!x.getSubscription().isFreeDelivery())
                total += restaurant.getDeliveryFee();

            p = new CardPayment(total, user, restaurant, tips, ((PremiumUser) user).getCardNumber());
        }
        else {
            p = new Payment(total, user, restaurant);
            total += restaurant.getDeliveryFee();
        }

        orders.add(new Order(p, courierForOrder, dishes));
        courierForOrder.incrementNumberOfOrders();

        System.out.println("Order was added to the list");
        System.out.println("Order info: ");
        System.out.println(String.format("Number: %s", orders.size()));
        System.out.println(String.format("User: %s, Address: %s, %s", user.getName(), user.getCity(), user.getAddress()));
        System.out.println(String.format("Restaurant: %s", restaurant.getName()));
        System.out.println("Products:");
        for(Dishes dish : dishes) {
            System.out.println(String.format("    %s", dish.getName()));
        }
        System.out.println(String.format("Price: %s", total));
        System.out.println(String.format("Courier: %s", courierForOrder.getName()));

        System.out.println();
    }

    // Remove maximum three couriers with the least number of orders from courier list
    public static void fireLaziestCouriers() {
        System.out.println("List of couriers:");
        printCouriers();

        List<Courier> couriers = courierDatabase.read();

        System.out.println("Firing the laziest couriers...");
        couriers.sort(Courier::compareTo);
//        couriers.subList(0, Math.min(3, couriers.size())).clear();

        courierDatabase.delete(couriers.get(0));
        courierDatabase.delete(couriers.get(1));
        courierDatabase.delete(couriers.get(2));

        System.out.println("Updated list of couriers:");
        printCouriers();

        System.out.println();
    }

    // Add subscription
    public static void addNewSubscription() {
        Scanner in = new Scanner(System.in);
        System.out.println("Insert subscription: ");
        System.out.println("Does the subscription have free delivery? (0 - no, otherwise - yes");
        String c = in.nextLine();

        boolean freeDelivery = true;
        if(c.equals("0"))
            freeDelivery = false;

        System.out.println("Discount(please insert number between 0 and 100): ");
        Integer discount = in.nextInt();

        System.out.println("Price: ");
        Float price = in.nextFloat();

//        subscriptions.add(new Subscription(freeDelivery, discount, price));
        subscriptionDatabase.create(new Subscription(1, freeDelivery, discount, price));

        System.out.println("Subscription was added with succes");
        printSubscriptions();

        System.out.println();
    }

    // Add user
    public static void addUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("Add user: ");

        System.out.println("Name: ");
        String name = in.nextLine();

        System.out.println("City: ");
        String city = in.nextLine();

        System.out.println("Address: ");
        String address = in.nextLine();

        System.out.println("Premium user(0 - no, otherwise - yes)?");
        String prem = in.nextLine();

        // If user is premium, add subscription and card
        if(!prem.equals("0")) {
            System.out.println("Enter credit card number: ");
            String card = in.nextLine();

            System.out.println("Choose subscription(enter index): ");
            printSubscriptions();

            System.out.println("Enter index: ");
            Integer check = in.nextInt();

            List<Subscription> subscriptions = subscriptionDatabase.read();
            Subscription sub = new Subscription(subscriptions.get(check - 1).getSubscriptionId(), subscriptions.get(check - 1).isFreeDelivery(), subscriptions.get(check - 1).getDiscount(), subscriptions.get(check - 1).getPrice());


//            users.add(new PremiumUser(name, address, city, sub, card));
            userDatabase.create(new PremiumUser(1, name, address, city, sub, card));
        }
        else {
//            users.add(new User(name, address, city));
            userDatabase.create(new User(1, name, address, city));
        }

        System.out.println("User was added!");
        printUsers();

        System.out.println();
    }

    // Add restaurant
    public static void addRestaurant() {
        Scanner in = new Scanner(System.in);
        System.out.println("Add restaurant: ");

        System.out.println("Name: ");
        String name = in.nextLine();

        System.out.println("Food type: ");
        String foodType = in.nextLine();

        System.out.println("City: ");
        String city = in.nextLine();

        System.out.println("Menu: ");
        List<Dishes> dishesList = new ArrayList<>();

        Integer opt = 1;
        while(opt != 0) {
            System.out.println("Dishes: ");
            printDishes(dishesDatabase.read());

            System.out.println("Press the index of the dish you want to add (or press -1 if you want to add a new one):");
            Integer x = in.nextInt();

            if(x == -1)
                dishesList.add(addDish());
            else
                dishesList.add(dishesDatabase.read().get(x - 1));

            System.out.println("Add another dish to the menu? (0 - no): ");
            opt = in.nextInt();
        }

        Set<Dishes> menu = new HashSet<>(dishesList);

        System.out.println("Delivery fee: ");
        Float deliveryFee = in.nextFloat();

        restaurants.add(new Restaurant(name, foodType, menu, city, deliveryFee));
    }

    //Add dish
    public static Dishes addDish() {
        Scanner in = new Scanner(System.in);

        System.out.println("Add dish to menu: ");

        System.out.println("Name: ");
        String name = in.nextLine();

        System.out.println("Quantity(grams): ");
        Integer grams = in.nextInt();

        System.out.println("Price: ");
        Float price = in.nextFloat();

        Dishes d = new Dishes(1, name, grams, price);

        dishesDatabase.create(d);

        return new Dishes(1, name, grams, price);
    }

    // Add courier
    public static void addCourier() {
        Scanner in = new Scanner(System.in);
        System.out.println("Add courier: ");

        System.out.println("Name: ");
        String name = in.nextLine();

        System.out.println("Salary: ");
        Float salary = in.nextFloat();

//        couriers.add(new Courier(name, salary, 0, true));
        courierDatabase.create(new Courier(1, name, salary, 0, true));

        System.out.println("Courier was added!");
        printCouriers();

        System.out.println();
    }

    // Remove a restaurant
    public static void removeRestaurant() {
        Scanner in = new Scanner(System.in);

        System.out.println("Choose which restaurant to remove: ");
        printRestaurants();
        System.out.println();

//        Restaurant r = restaurants.stream()
//                .filter(restaurant -> (restaurant.getCity().equals(city) && restaurant.getName().equals(name)))
//                .findFirst()
//                .orElse(null);
//
//        if(r == null) {
//            System.out.println("There is no restaurant in given city with given name");
//            return;
//        }

        System.out.println("Enter index: ");
        int restaurantIndex = in.nextInt();
        restaurants.remove(restaurantIndex - 1);

        System.out.println("Restaurant was removed successfully");
        printRestaurants();
    }

    // Get most frequent value from list
    private static String getMaxFreq(List<String> arr) {
        return arr.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
    }

    // Print methods for lists
    private static void printRestaurants() {
        System.out.println("Restaurants: ");
        for(int i = 0; i < restaurants.size(); ++i) {
            Restaurant x = restaurants.get(i);
            System.out.println(String.format("  %d - %s, City: %s, Delivery Fee: %s", i + 1, x.getName(), x.getCity(), x.getDeliveryFee()));
        }
    }

    private static void printDishes(List<Dishes> d) {
        for(int i = 0; i < d.size(); ++i) {
            Dishes x = d.get(i);
            System.out.println(String.format("  %d - %s, Price: %s", i + 1, x.getName(), x.getPrice()));
        }
    }


    private static void printUsers() {
        System.out.println("Users: ");
        boolean isPrem = false;

        List<User> users = userDatabase.read();
        for(int i = 0; i < users.size(); ++i) {
            User x = users.get(i);
            if(x instanceof PremiumUser)
                isPrem = true;
            else
                isPrem = false;

            System.out.println(String.format("  %d - %s, Address: %s, %s, Premium: %b", i + 1, x.getName(), x.getAddress(), x.getCity(), isPrem));
        }
    }

    private static void printCouriers() {
        System.out.println("Couriers: ");

        List<Courier> couriers = courierDatabase.read();
        for(int i = 0; i < couriers.size(); ++i) {
            Courier x = couriers.get(i);
            System.out.println(String.format("  %d - %s, Salary: %f, Number of orders: %d, Is Available: %b", i + 1, x.getName(), x.getSalary(), x.getNumberOfOrders(), x.getAvailable() ));
        }
    }

    private static void printSubscriptions() {
        System.out.println("Subscriptions: ");

        List<Subscription> subscriptions = subscriptionDatabase.read();
        for(int i = 0; i < subscriptions.size(); ++i) {
            Subscription x = subscriptions.get(i);
            System.out.println(String.format("  %d - Free delivery: %b, Discount: %d, Price: %f", i + 1, x.isFreeDelivery(), x.getDiscount(), x.getPrice()));
        }
    }
}
