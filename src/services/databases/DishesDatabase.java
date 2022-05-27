package services.databases;

import restaurant.Dishes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishesDatabase {

    public Connection connection;

    public DishesDatabase(Connection connection) {
        this.connection = connection;
    }

    public List<Dishes> read() {
        List<Dishes> dishes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Dishes";

            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                Dishes d = new Dishes(res);
                dishes.add(d);
            }

            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return dishes;
    }

    public void update(Dishes d, Integer dishesId) {
        String query = "UPDATE Dishes SET name = ?, grams = ?, price = ? WHERE dishesId = ?";
        createPrepAndExec(query, d, true, dishesId);
    }

    public void create(Dishes d) {
        String query = "INSERT INTO Dishes (name, grams, price) VALUES (?, ?, ?)";
        createPrepAndExec(query, d, true, -1);
    }

    public void delete(Dishes d) {
        String query = "DELETE FROM Dishes WHERE name = ? AND grams = ? AND price = ?";
        createPrepAndExec(query, d, true, -1);
    }

    // Create the PreparedStatement, set the attributes and execute the query
    private ResultSet createPrepAndExec(String query, Dishes d, Boolean isUpdate, Integer dishesId) {
        ResultSet res = null;
        try {
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, d.getName());
            prepStatement.setInt(2, d.getGrams());
            prepStatement.setFloat(3, d.getPrice());

            // If the query is an update, I also set the id
            if(isUpdate) {
                if(dishesId != -1) {
                    prepStatement.setInt(4, dishesId);
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
