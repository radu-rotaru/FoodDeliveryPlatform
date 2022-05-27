package services.databases;

import foodPlatform.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDatabase {

    private Connection connection;

    public SubscriptionDatabase(Connection connection) {
        this.connection = connection;
    }

    public List<Subscription> read() {
        List<Subscription> subscriptions = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Subscriptions";

            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                Subscription s = new Subscription(res);
                subscriptions.add(s);
            }

            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return subscriptions;
    }

//    public Integer returnSubscriptionId(Subscription s) {
//        ResultSet res;
//
//        String query = "SELECT subscriptionsId FROM Subscriptions WHERE freeDelivery = ? AND discount = ? AND price = ?";
//        res = createPrepAndExec(query, s, false, -1);
//
//        int x = -1;
//        try {
//            x = res.getInt(1);
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return x;
//    }

    public void update(Subscription s, Integer subscriptionsId) {
        String query = "UPDATE Subscriptions SET freeDelivery = ?, discount = ?, price = ? WHERE subscriptionsId = ?";
        createPrepAndExec(query, s, true, subscriptionsId);
    }

    public void create(Subscription s) {
        String query = "INSERT INTO Subscriptions (freeDelivery, discount, price) VALUES (?, ?, ?)";
        createPrepAndExec(query, s, true, -1);
    }

    public void delete(Subscription s) {

        String query = "DELETE FROM Subscriptions WHERE freeDelivery = ? AND discount = ? AND price = ?";
        createPrepAndExec(query, s, true, -1);

    }

    // Create the PreparedStatement, set the attributes and execute the query
    private ResultSet createPrepAndExec(String query, Subscription s, Boolean isUpdate, Integer subscriptionsId) {
        ResultSet res = null;
        try {
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setBoolean(1, s.isFreeDelivery());
            prepStatement.setInt(2, s.getDiscount());
            prepStatement.setFloat(3, s.getPrice());

            // If the query is an update, I also set the id
            if(isUpdate) {
                if(subscriptionsId != -1) {
                    prepStatement.setInt(4, subscriptionsId);
                }
                prepStatement.executeUpdate();
            }

            else {
                res = prepStatement.executeQuery();
            }

            prepStatement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
