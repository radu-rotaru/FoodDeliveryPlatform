package services.read;

import foodPlatform.Courier;
import services.Services;

public class CourierReader implements Reader {
    private static CourierReader instance = null;

    private CourierReader() {}

    public static CourierReader getInstance() {
        if(instance == null)
            instance = new CourierReader();

        return instance;
    }

    public void read(String[] attrs) {
        Courier courier =  new Courier(1, attrs[0], Float.parseFloat(attrs[1]),
                                       Integer.parseInt(attrs[2]), Boolean.parseBoolean(attrs[3]));

        // Cod partea a 2-a
//        List<Courier> courierList = Services.getCouriers();
//        courierList.add(courier);
//        Services.setCouriers(courierList);
        Services.courierAdd(courier);

    }
}
