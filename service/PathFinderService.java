package service;

import java.util.*;

import constants.EntityType;
import model.Entity;
import model.Order;
import model.PathNode;
import util.BiKeyHashMap;
import util.Graph;

public class PathFinderService {

    public static List<PathNode> shortestTimePath(Graph pathNetworkGraph, BiKeyHashMap<Entity, Entity, Order> customerRestaurantOrderMap, Entity source, int totalOrderToDeliver) {
        List<PathNode> shortestPath = new ArrayList<>();
        PriorityQueue<State> pq = new PriorityQueue<>();
        Map<String, Double> bestMap = new HashMap<>();
        
        pq.add(new State(source, new HashSet<>(Set.of(source)), new ArrayList<>(List.of(new PathNode(source, 0.0))), 0, new BiKeyHashMap<>(customerRestaurantOrderMap), totalOrderToDeliver));

        while (!pq.isEmpty()) {
            State currentState = pq.poll();
            Entity currentEntity = currentState.getEntity();
            double currentTime = currentState.getTimeElapsed();
            Set<Entity> currentVisited = currentState.getVisited();
            List<PathNode> currentPath = currentState.getPath();
            BiKeyHashMap<Entity, Entity, Order> currentCustomerRestaurantOrder = currentState.getCustomerRestaurantOrderMap();
            int currentTotalOrderToDeliver = currentState.getTotalOrderToDeliver();

            if (currentTotalOrderToDeliver <= 0) {
                return currentPath;
            }

            List<Entity> neighbors = pathNetworkGraph.getNeigbours(currentEntity);

            for (Entity adjEntity : neighbors) {
                if (currentVisited.contains(adjEntity)) {
                    continue;
                }

                Set<Entity> newVisitedSet = new HashSet<>(currentVisited);
                int newDeliveryCount = currentTotalOrderToDeliver;
                BiKeyHashMap<Entity, Entity, Order> newCustomerRestaurantOrder = new BiKeyHashMap<>(currentCustomerRestaurantOrder);
                double newTime = currentTime + getTimeRequired(currentEntity, adjEntity);

                if (adjEntity.getEntityType() == EntityType.RESTAURANT) {
                    Order order = newCustomerRestaurantOrder.getByRestaurant(adjEntity);
                    if (order != null && !order.getOrderPickedUpStatus()) {
                        order.setOrderPickedUpStatus();
                        newCustomerRestaurantOrder.putByRestaurant(adjEntity, order);
                        newTime += Math.max(0, newCustomerRestaurantOrder.getByRestaurant(adjEntity).getPrepTime() - newTime);
                    }
                    newVisitedSet.add(adjEntity);
                } 
                else if (adjEntity.getEntityType() == EntityType.CUSTOMER) {
                    Order customerOrder = newCustomerRestaurantOrder.getByCustomer(adjEntity);
                    if (customerOrder != null && customerOrder.getOrderPickedUpStatus() && !customerOrder.getOrderDeliveredStatus()) {
                        customerOrder.setOrderDeliveredStatus();
                        newCustomerRestaurantOrder.putByCustomer(adjEntity, customerOrder);
                        newDeliveryCount--;
                    }
                    newVisitedSet.add(adjEntity);
                }
                
                List<PathNode> newPath = new ArrayList<>(currentPath);
                newPath.add(new PathNode(adjEntity, newTime));

                String stateKey = getUniqueStateKey(adjEntity, newVisitedSet);

                if (!bestMap.containsKey(stateKey) || newTime < bestMap.get(stateKey)) {
                    bestMap.put(stateKey, newTime);
                    pq.add(new State(adjEntity, newVisitedSet, newPath, newTime, newCustomerRestaurantOrder, newDeliveryCount));
                }
            }
        }
        return shortestPath;
    }

    private static String getUniqueStateKey(Entity adjEntity, Set<Entity> newVisited) {
        StringBuilder stateKeyBuilder = new StringBuilder(adjEntity.getName()).append(" Visited: ").append(newVisited.size());
        for (Entity e : newVisited) {
            stateKeyBuilder.append("-").append(e.getName());
        }
        return stateKeyBuilder.toString();
    }

    private static double getDistance(Entity from, Entity to) {
        return Math.sqrt(Math.pow(from.getLatitude() - to.getLatitude(), 2) + Math.pow(from.getLongitude() - to.getLongitude(), 2));     
    }

    private static double getTimeRequired(Entity from, Entity to) {
        int speed = 20;
        return getDistance(from, to) / speed;
    }
}