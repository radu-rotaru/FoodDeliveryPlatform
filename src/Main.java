import foodPlatform.*;
import restaurant.*;
import services.*;
import services.databases.CourierDatabase;
import services.databases.DishesDatabase;
import services.databases.SubscriptionDatabase;
import services.databases.UserDatabase;
import user.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.*;

public class Main {

    public static AuditService auditService = AuditService.getInstance();
//    public static ReadFromFileService readFromFileService = ReadFromFileService.getInstance();
//    public static WriteToFileService writeToFileService = WriteToFileService.getInstance();

    public static Connection databaseConection() {

        try {
            String url = "jdbc:mysql://localhost:3306/proiectpao";
            String username = "root";
            String password = "";

            return DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static void main(String[] args) {
        // I've added the users to the database first time when I've first ran the program
        UserDatabase users = new UserDatabase(Main.databaseConection());
        users.create(new User(1, "John", "Street 19, No. 14", "Barcelona"));
        users.create(new User(2, "Calum", "Street 11, No. 13", "London"));
        users.create(new User(3, "Bob", "Street 9, No. 11", "Barcelona"));
        users.create(new User(4, "Fred", "Street 2, No. 1", "Liverpool"));
        users.create(new User(5, "Paul", "Street 12, No. 4", "Barcelona"));
        users.create(new User(6, "Sofia", "Street 1, No. 4", "Madrid"));
        users.create(new User(7, "Fred", "Street 9, No. 1", "Barcelona"));
        users.create(new User(8, "Taylor", "Street 19, No. 5", "Prague"));

        // I've create the menu for the restaurants like this just for simplicity
        // // I've added the dishes to the database first time when I've first ran the program
        List<Dishes> dishes = new ArrayList<>();
        DishesDatabase d = new DishesDatabase(Main.databaseConection());
        Dishes dish;

        dish = new Dishes(1, "Fries", 200, 5f);
        dishes.add(dish);
        d.create(dish);

        dish = new Dishes(2, "Beef", 200, 15f);
        dishes.add(dish);
        d.create(dish);

        dish = new Dishes(3, "Pork", 200, 19f);
        dishes.add(dish);
        d.create(dish);

        dish = new Dishes(4, "Burger", 200, 12f);
        dishes.add(dish);
        d.create(dish);

        dish = new Dishes(5, "Omlette", 200, 7f);
        dishes.add(dish);
        d.create(dish);

        // Second restaurant menu
        List<Dishes> dishes_1 = new ArrayList<>();
        dish = new Dishes(6, "Spaghetti", 200, 22f);
        dishes_1.add(dish);
        d.create(dish);

        dish = new Dishes(7, "Pasta", 200, 25f);
        dishes_1.add(dish);
        d.create(dish);

        dish = new Dishes(8, "Rice", 200, 7f);
        dishes_1.add(dish);
        d.create(dish);

        dish = new Dishes(9, "Chicken Soup", 200, 14f);
        dishes_1.add(dish);
        d.create(dish);

        // I've created the restaurants like this, I haven't created a database for them
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("McDonalds", "fast-food", new HashSet<>(dishes), "Barcelona", 5.0f));
        restaurants.add(new Restaurant("McDonalds", "fast-food", new HashSet<>(dishes), "London", 4.0f));
        restaurants.add(new Restaurant("Fratelli", "italian", new HashSet<>(dishes_1), "Prague", 10.0f));
        restaurants.add(new Restaurant("Fratelli", "italian", new HashSet<>(dishes_1), "Madrid", 8.0f));
        restaurants.add(new Restaurant("Fratelli", "italian", new HashSet<>(dishes_1), "London", 5.5f));

        // I've added the couriers to the database first time when I've first ran the program
        CourierDatabase couriers = new CourierDatabase(Main.databaseConection());
        couriers.create(new Courier(1, "Adrian", 2500f, 7, false));
        couriers.create(new Courier(2, "George", 2700f, 3, false));
        couriers.create(new Courier(3, "Christian", 2100f, 2, true));
        couriers.create(new Courier(4, "Drake", 2800f, 1, true));
        couriers.create(new Courier(5, "Dwight", 3000f, 1, true));
        couriers.create(new Courier(6, "Louis", 1900f, 3, true));

        // I've added the subscriptions to the database first time when I've first ran the program
        SubscriptionDatabase subscriptions = new SubscriptionDatabase(Main.databaseConection());
        subscriptions.create(new Subscription(1, true, 20, 19.99f));
        subscriptions.create(new Subscription(2, true, 10, 14.99f));
        subscriptions.create(new Subscription(3, true, 40, 29.99f));
        subscriptions.create(new Subscription(4, false, 5, 2.99f));
        subscriptions.create(new Subscription(5, true, 0, 6.99f));


        Services.setUsers(users);
        Services.setCouriers(couriers);
        Services.setRestaurants(restaurants);
        Services.setSubscriptions(subscriptions);
        Services.setDishes(d);

//        readFromFileService.read(CourierReader.getInstance(), "couriers.csv");
//        readFromFileService.read(SubscriptionReader.getInstance(), "subscriptions.csv");
//        readFromFileService.read(DishReader.getInstance(), "dishes.csv");
//        readFromFileService.read(UserReader.getInstance(), "users.csv");

        // Interactive menu to test functionality of Services actions
        Scanner in = new Scanner(System.in);
        String[] options = new String[]{"Add order", "Add User", "Add Courier", "Add Subscription", "Print loyal users",
                                             "Get cities with most interaction", "Fire Lazy Couriers", "Get local restaurants",
                                             "User unsubscribed", "Remove restaurant", "Add restaurant", "Leave"};
        while(true) {
            System.out.println("Menu. Choose option.");
            for(int i = 0; i < options.length; i++) {
                System.out.println(String.format("    %d - %s", i + 1, options[i]));
            }
//            System.out.println("    1 - Add order");
//            System.out.println("    2 - Add User");
//            System.out.println("    3 - Add Courier");
//            System.out.println("    4 - Add Subscription");
//            System.out.println("    5 - Print loyal users");
//            System.out.println("    6 - Get cities with most interaction");
//            System.out.println("    7 - Fire Lazy Couriers");
//            System.out.println("    8 - Get local restaurants");
//            System.out.println("    9 - User unsubscribed");
//            System.out.println("   10 - Remove restaurant");
//            System.out.println("   11 - Add restaurant");
//            System.out.println("   12 - Leave");
            System.out.println();

            System.out.println("Enter index: ");
            int option = in.nextInt();
            auditService.write(options[option - 1]);
            switch (option) {
                case 1:
                    Services.addOrder();
                    break;
                case 2:
                    Services.addUser();
                    break;
                case 3:
                    Services.addCourier();
                    break;
                case 4:
                    Services.addNewSubscription();
                    break;
                case 5:
                    Services.getLoyalUsers();
                    break;
                case 6:
                    Services.getBestMarketPlaces();
                    break;
                case 7:
                    Services.fireLaziestCouriers();
                    break;
                case 8:
                    Services.getRestaurantsNearUser();
                    break;
                case 9:
                    Services.unsubscribeUser();
                    break;
                case 10:
                    Services.removeRestaurant();
                    break;
                case 11:
                    Services.addRestaurant();
                    break;
                case 12:
                  return;
            }
        }
    }
}
