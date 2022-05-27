package foodPlatform;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Courier implements Comparable<Courier>{
    private Integer courierId;
    private String name;
    private Float salary;
    private int numberOfOrders;
    private boolean available;

    private Courier() { }

    public Courier(Integer id, String name, Float salary, int numberOfOrders, boolean available) {
        this.courierId = id;
        this.name = name;
        this.salary = salary;
        this.numberOfOrders = numberOfOrders;
        this.available = available;
    }

    public Courier(ResultSet res) throws SQLException {
        this.courierId = res.getInt("couriersId");
        this.name = res.getString("name");
        this.salary = res.getFloat("salary");
        this.numberOfOrders = res.getInt("numberOfOrders");
        this.available = res.getBoolean("available");
    }

    @Override
    public int compareTo(Courier c) {
        if(this.numberOfOrders < c.numberOfOrders)
            return -1;
        if(this.numberOfOrders == c.numberOfOrders)
            return 0;
        return 1;
    }

    public Integer getCourierId() { return courierId; }

    public String getName() {
        return name;
    }

    public Float getSalary() {
        return salary;
    }

    public int getNumberOfOrders() { return numberOfOrders; }

    public boolean getAvailable() {
        return available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public void incrementNumberOfOrders() { this.numberOfOrders += 1; }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
