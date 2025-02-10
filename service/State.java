package service;
import java.util.*;

import model.Entity;
import model.Order;
import model.PathNode;
import util.BiKeyHashMap;

public class State implements Comparable<State> {
    private Entity entity;
    private double timeElapsed;
    private int totalOrderToDeliver;
    private Set<Entity> visited;
    private List<PathNode> path;
    private BiKeyHashMap<Entity, Entity, Order> customerRestaurantOrderMap;

    State(Entity current, Set<Entity> visited, List<PathNode> path, double timeTaken, BiKeyHashMap<Entity, Entity, Order> customerRestaurantOrder, int totalOrderToDeliver) {
        this.timeElapsed = timeTaken;
        this.entity = current;
        this.visited = visited;
        this.path = path;
        this.customerRestaurantOrderMap = customerRestaurantOrder;
        this.totalOrderToDeliver = totalOrderToDeliver;
    }

    @Override
    public int compareTo(State other) {
        return Double.compare(this.timeElapsed, other.timeElapsed);
    }

    public double getTimeRequired(Entity from, Entity to) {
        return Math.sqrt(Math.pow(from.getLatitude() - to.getLatitude(), 2) + Math.pow(from.getLongitude() - to.getLongitude(), 2));
    }

    public Entity getEntity() {
        return this.entity;
    }

    public double getTimeElapsed() {
        return this.timeElapsed;
    }

    public Set<Entity> getVisited() {
        return this.visited;
    }

    public List<PathNode> getPath() {
        return this.path;
    }

    public BiKeyHashMap<Entity, Entity, Order> getCustomerRestaurantOrderMap() {
        return this.customerRestaurantOrderMap;
    }

    public int getTotalOrderToDeliver() {
        return this.totalOrderToDeliver;
    }
}
