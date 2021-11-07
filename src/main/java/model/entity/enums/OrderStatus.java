package model.entity.enums;

public enum OrderStatus {
    PROCESSING("Processing"),
    CONFIRMED("Confirmed"),
    REJECTED("Rejected");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
