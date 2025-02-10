package util;

import java.util.*;

import model.Order;

public class BiKeyHashMap<C, R, V> {
    private final Map<C, V> customerMap = new HashMap<>();
    private final Map<R, V> restaurantMap = new HashMap<>();
    private final Map<V, C> reverseCustomerMap = new HashMap<>();
    private final Map<V, R> reverseRestaurantMap = new HashMap<>();

    public BiKeyHashMap() {}

    public BiKeyHashMap(BiKeyHashMap<C, R, V> other) {
        for (C customer : other.customerMap.keySet()) {
            R restaurant = other.reverseRestaurantMap.get(other.customerMap.get(customer));
            V value = other.customerMap.get(customer);
    
            if (value instanceof Order) {
                Order oldOrder = (Order) value;
                Order newOrder = new Order(oldOrder.getFrom(), oldOrder.getTo(), oldOrder.getPrepTime());
                if (oldOrder.getOrderPickedUpStatus()) {
                    newOrder.setOrderPickedUpStatus();
                }
                put(customer, restaurant, (V) newOrder);
            } else {
                put(customer, restaurant, value);
            }
        }
    }

    public void put(C customer, R restaurant, V value) {
        customerMap.put(customer, value);
        restaurantMap.put(restaurant, value);
        reverseCustomerMap.put(value, customer);
        reverseRestaurantMap.put(value, restaurant);
    }

    public void putByCustomer(C customer, V value) {
        if (!customerMap.containsKey(customer)) {
            throw new IllegalArgumentException("Customer key not found");
        }
        R restaurant = reverseRestaurantMap.get(customerMap.get(customer));
        put(customer, restaurant, value);
    }

    public void putByRestaurant(R restaurant, V value) {
        if (!restaurantMap.containsKey(restaurant)) {
            throw new IllegalArgumentException("Restaurant key not found");
        }
        C customer = reverseCustomerMap.get(restaurantMap.get(restaurant));
        put(customer, restaurant, value);
    }

    public V getByCustomer(C customer) {
        return customerMap.get(customer);
    }

    public V getByRestaurant(R restaurant) {
        return restaurantMap.get(restaurant);
    }
    
    public Set<C> customerKeySet() {
        return customerMap.keySet();
    }

}