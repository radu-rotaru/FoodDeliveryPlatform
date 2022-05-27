package user;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private Integer userId;
    private String name;
    private String address;
    private String city;

    protected User() { }

    public User(Integer id, String name, String address, String city) {
        this.userId = id;
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public User(ResultSet res) throws SQLException {
        this.userId = res.getInt("usersId");
        this.name = res.getString("name");
        this.address = res.getString("address");
        this.city = res.getString("city");
    }


    public Integer getUserId() { return userId; }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;

        if(!(o instanceof User))
            return false;

        User u = (User) o;

        return name.equals(u.getName())
                && city.equals(u.getCity())
                && address.equals(u.getAddress());
    }
}
