package controller.dto;

import javax.validation.constraints.Size;

public class OrderDTO {
    private long id;
    private double discount;
    private double price;

    public static Builder builder() {
        return new Builder();
    }
     public static class Builder {
        private double discount;
        private double price;

        public Builder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public OrderDTO build() {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setDiscount(discount);
            orderDTO.setPrice(price);
            return orderDTO;
        }
     }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
