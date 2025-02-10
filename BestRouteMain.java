import java.util.*;

import model.Customer;
import model.Entity;
import model.Order;
import model.PathNode;
import model.Restaurant;
import model.Rider;
import service.PathFinderService;
import util.BiKeyHashMap;
import util.Graph;

public class BestRouteMain {
    public static void main(String[] args) {
        Rider rider = new Rider("rider", -10, 50);
        Restaurant r1 = new Restaurant("Restaurant 1", 10, 20);
        Restaurant r2 = new Restaurant("Restaurant 2", 25, 25);
        Restaurant r3 = new Restaurant("Restaurant 3", -100, 50);

        Customer c1 = new Customer("Customer 1", 60, 80);
        Customer c2 = new Customer("Customer 2", 75, 25);
        Customer c3 = new Customer("Customer 3", -250, 50);

        Order o1 = new Order(r1, c1, 10);
        Order o2 = new Order(r2, c2, 5);
        Order o3 = new Order(r3, c3, 20);

        BiKeyHashMap<Entity, Entity, Order> customerRestaurantOrderMap = new BiKeyHashMap<>();
        customerRestaurantOrderMap.put(c1, r1, o1);
        customerRestaurantOrderMap.put(c2, r2, o2);
        customerRestaurantOrderMap.put(c3, r3, o3);

        Graph path = new Graph();

        // Connect rider to all restaurants
        path.addBiEntity(rider, r1);
        path.addBiEntity(rider, r2);
        path.addBiEntity(rider, r3);

        path.addBiEntity(r1, r2);
        path.addBiEntity(r2, r3);
        path.addBiEntity(r3, r1);

        path.addBiEntity(r1, c1);
        path.addBiEntity(r1, c2);
        path.addBiEntity(r1, c3);

        path.addBiEntity(r2, c1);
        path.addBiEntity(r2, c2);
        path.addBiEntity(r2, c3);

        path.addBiEntity(r3, c1);
        path.addBiEntity(r3, c2);
        path.addBiEntity(r3, c3);

        path.addBiEntity(c1, c2);
        path.addBiEntity(c2, c3);
        path.addBiEntity(c3, c1);

        List<PathNode> res = PathFinderService.shortestTimePath(path, customerRestaurantOrderMap, rider, customerRestaurantOrderMap.customerKeySet().size());
        for (int i=1; i<res.size(); i++) {
            System.err.printf("Rider reached at (%s) in %d min\n", res.get(i).getEntity().getName(), Math.round(res.get(i).getTime()));
        }
        System.out.println();
    }
}