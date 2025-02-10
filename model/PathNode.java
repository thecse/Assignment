package model;

public class PathNode {
    private final Entity entity;
    private final double time;

    public PathNode(Entity entity, double time) {
        this.entity = entity;
        this.time = time;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public double getTime() {
        return this.time;
    }
}
