package model;

public class Order {
    private Entity from, to;
    private int prepTime;
    private boolean pickedUp, delivered;

    public Order(Entity from, Entity to, int prepTime) {
        this.to = to;
        this.from = from;
        this.prepTime = prepTime;
        this.pickedUp = false;
        this.delivered = false;
    }

    public Entity getFrom() {
        return this.from;
    }

    public Entity getTo() {
        return this.to;
    }

    public int getPrepTime() {
        return this.prepTime;
    }

    public boolean getOrderPickedUpStatus() {
        return this.pickedUp;
    }

    public void setOrderPickedUpStatus() {
        this.pickedUp = true;
    }

    public boolean getOrderDeliveredStatus() {
        return this.delivered;
    }

    public void setOrderDeliveredStatus() {
        this.delivered = true;
    }
}
