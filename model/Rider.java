package model;
import constants.EntityType;

public class Rider extends Entity {
    public Rider(String name, int latitude, int longitude) {
        super(name, EntityType.RIDER, latitude, longitude);
    }
}
