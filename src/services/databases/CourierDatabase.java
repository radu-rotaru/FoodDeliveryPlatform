package services.databases;

import foodPlatform.Courier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourierDatabase {

    private Connection connection;

    public CourierDatabase(Connection connection) {
        this.connection = connection;
    }

    public List<Courier> read() {
        List<Courier> couriers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Couriers";

            ResultSet res = statement.executeQuery(query);

            while(res.next()) {
                Courier c = new Courier(res);
                couriers.add(c);
            }

            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return couriers;
    }

    public void update(Courier c, int courierId) {
        String query = "UPDATE Couriers SET name = ?, salary = ?, numberOfOrders = ?, available = ? WHERE couriersId = ?";
        createPrepAndExec(query, c, true, courierId);
     }

     public void create(Courier c) {
        String query = "INSERT INTO Couriers (name, salary, numberOfOrders, available) VALUES (?, ?, ?, ?)";
        createPrepAndExec(query, c, true, -1);
    }

     public void delete(Courier c) {
        String query = "DELETE FROM Couriers WHERE name = ? AND salary = ? AND numberOfOrders = ? AND available = ?";
         createPrepAndExec(query, c, true, -1);
     }

    // Create the PreparedStatement, set the attributes and execute the query
    private ResultSet createPrepAndExec(String query, Courier c, Boolean isUpdate, Integer courierId) {
        ResultSet res = null;
        try {
            PreparedStatement prepStatement = connection.prepareStatement(query);

            prepStatement.setString(1, c.getName());
            prepStatement.setFloat(2, c.getSalary());
            prepStatement.setInt(3, c.getNumberOfOrders());
            prepStatement.setBoolean(4, c.getAvailable());

            if(isUpdate) {
                // If the query is an update, I also set the id
                if(courierId != -1) {
                    prepStatement.setInt(5, courierId);
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
