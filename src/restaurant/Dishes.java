package restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Dishes {
    private Integer dishId;
    private String name;
    private Integer grams;
    private Float price;

    private Dishes() { }

    public Dishes(Integer id, String name, Integer grams, Float price) {
        this.dishId = id;
        this.name = name;
        this.grams = grams;
        this.price = price;
    }

    public Dishes(ResultSet res) throws SQLException {
        this.dishId = res.getInt("dishesId");
        this.name = res.getString("name");
        this.grams = res.getInt("grams");
        this.price = res.getFloat("price");
    }


    public String getName() {
        return name;
    }

    public Integer getGrams() {
        return grams;
    }

    public Float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrams(Integer grams) {
        this.grams = grams;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
