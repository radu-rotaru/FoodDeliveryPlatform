package services.read;

import services.Services;
import user.User;

public class UserReader implements Reader {
    private static UserReader instance = null;

    private UserReader() {}

    public static UserReader getInstance() {
        if(instance == null)
            instance = new UserReader();

        return instance;
    }

    public void read(String[] attrs) {
        User user =  new User(1, attrs[0], attrs[1], attrs[2]);

        // Cod partea a 2-a
//        List<User> userList = Services.getUsers();
//        userList.add(user);
//        Services.setUsers(userList);
        Services.userAdd(user);
    }
}
