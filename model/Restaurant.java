package model;
import constants.EntityType;

public class Restaurant extends Entity {
    public Restaurant(String name, int latitude, int longitude) {
        super(name, EntityType.RESTAURANT, latitude, longitude);
        
    }
}
