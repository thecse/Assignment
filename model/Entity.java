package model;

import java.util.List;

import constants.EntityType;

public class Entity {
    private EntityType entityType;
    private String name;
    private int latitude, longitude;
    private List<Entity> nextPath;

    public Entity(String name, EntityType entityType, int latitude, int longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.entityType = entityType;
    }

    public String getName() {
        return this.name;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public int getLatitude() {
        return this.latitude;
    }

    public int getLongitude() {
        return this.longitude;
    }

    public List<Entity> getNextPaths() {
        return this.nextPath;
    }
}