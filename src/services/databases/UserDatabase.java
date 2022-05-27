package services.databases;

import foodPlatform.Subscription;
import services.databases.SubscriptionDatabase;
import user.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {

    private Connection connection;

    public UserDatabase(Connection connection) {
        this.connection = connection;
    }

    public List<User> read() {
        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Users";

            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                Integer subscriptionId = res.getInt("subscriptionId");
                SubscriptionDatabase s = new SubscriptionDatabase(connection);

                if(subscriptionId != 0) {
                    Subscription sub = s.read().stream()
                            .filter(subscription -> subscription.getSubscriptionId() == subscriptionId)
                            .findFirst().get();

                    PremiumUser u = new PremiumUser(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), sub, res.getString(6));
                    users.add(u);
                }
                else {
                    User u = new User(res.getInt(1), res.getString(2), res.getString(3), res.getString(4));
                    users.add(u);
                }
            }

            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void update(User u, Integer usersId) {

        String query = "UPDATE Users SET name = ?, address = ?, city = ?, subscriptionId = ?, cardNumber = ? WHERE usersId = ?";
        createPrepAndExec(query, u, true, usersId);
    }

    public void create(User u) {
        String query = "INSERT INTO Users (name, address, city, subscriptionId, cardNumber) VALUES (?, ?, ?, ?, ?)";
        createPrepAndExec(query, u, true, -1);
    }

    public void delete(User u) {
        String query = "DELETE FROM Users WHERE name = ? AND address = ? AND city = ? AND subscriptionId = ? AND cardNumber = ?";
        createPrepAndExec(query, u, true, -1);
    }

    // Create the PreparedStatement, set the attributes and execute the query
    private ResultSet createPrepAndExec(String query, User u, Boolean isUpdate, Integer usersId) {
        ResultSet res = null;
        try {
            PreparedStatement prepStatement = connection.prepareStatement(query);

            if(u instanceof PremiumUser) {
                PremiumUser p = (PremiumUser) u;
                prepStatement.setString(1, p.getName());
                prepStatement.setString(2, p.getAddress());
                prepStatement.setString(3, p.getCity());
                prepStatement.setInt(4, p.getSubscription().getSubscriptionId());
                prepStatement.setString(5, p.getCardNumber());

                if(isUpdate) {
                    // If the query is an update, I also set the id
                    if(usersId != -1) {
                        prepStatement.setInt(6, usersId);
                    }
                    prepStatement.executeUpdate();
                }
                else {
                    res = prepStatement.executeQuery();
                }

                prepStatement.close();
            }
            else {
                prepStatement.setString(1, u.getName());
                prepStatement.setString(2, u.getAddress());
                prepStatement.setString(3, u.getCity());

                if(isUpdate) {
                    // If the query is an update, I also set the id
                    // The user is default, so the subscription and cardNumber are null
                    prepStatement.setNull(4, Types.INTEGER);
                    prepStatement.setNull(5, Types.VARCHAR);

                    if(usersId != -1) {
                        prepStatement.setInt(6, usersId);
                    }
                    prepStatement.executeUpdate();
                }
                else {
                    res = prepStatement.executeQuery();
                }

                prepStatement.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
