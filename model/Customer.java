package model;
import constants.EntityType;

public class Customer extends Entity {
    public Customer(String name, int latitude, int longitude) {
        super(name, EntityType.CUSTOMER, latitude, longitude);
    }
}
