package util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Entity;

public class Graph {
    private final Map<Entity, List<Entity>> graph = new HashMap<>();
    
    public void addEntity(Entity from, Entity to) {
        if(!this.graph.containsKey(from)) {
            this.graph.put(from, new ArrayList<>());
        }
        this.graph.get(from).add(to);
    }

    public void addBiEntity(Entity entity1, Entity entity2) {
        addEntity(entity1, entity2);
        addEntity(entity2, entity1);
    }

    public List<Entity> getNeigbours(Entity entity) {
        return this.graph.getOrDefault(entity, new ArrayList<>());
    }

    public int getPathGraph() {
        return this.graph.size();
    }
}