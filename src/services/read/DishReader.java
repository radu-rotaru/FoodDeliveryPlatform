package services.read;

import restaurant.Dishes;
import services.Services;

public class DishReader implements Reader {
    private static DishReader instance = null;

    private DishReader() {}

    public static DishReader getInstance() {
        if(instance == null)
            instance = new DishReader();

        return instance;
    }

    public void read(String[] attrs) {
        Dishes dish =  new Dishes(1, attrs[0], Integer.parseInt(attrs[1]), Float.parseFloat(attrs[2]));

        // Cod partea a 2-a
//        List<Dishes> dishesList = Services.getDishes();
//        dishesList.add(dish);
//        Services.setDishes(dishesList);
        Services.dishAdd(dish);
    }
}
