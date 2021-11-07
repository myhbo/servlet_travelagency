package model.entity;

import model.entity.enums.HotelType;
import model.entity.enums.TourType;

public class Tour {
    private long id;
    private String name;
    private TourType tourType;
    private HotelType hotelType;
    private  int groupSize;
    private double price;
    private boolean isHot;

    public Tour() {
    }

    public Tour(long id, String name, TourType tourType, HotelType hotelType, int groupSize, double price, boolean isHot) {
        this.id = id;
        this.name = name;
        this.tourType = tourType;
        this.hotelType = hotelType;
        this.groupSize = groupSize;
        this.price = price;
        this.isHot = isHot;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String name;
        private TourType tourType;
        private HotelType hotelType;
        private  int groupSize;
        private double price;
        private boolean isHot;

        private Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder tourType(TourType tourType) {
            this.tourType = tourType;
            return this;
        }

        public Builder hotelType(HotelType hotelType) {
            this.hotelType = hotelType;
            return this;
        }

        public Builder groupSize(int groupSize) {
            this.groupSize = groupSize;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder isHot(boolean isHot) {
            this.isHot = isHot;
            return this;
        }

        public Tour build() {
            Tour tour = new Tour();
            tour.setId(id);
            tour.setName(name);
            tour.setTourType(tourType);
            tour.setHotelType(hotelType);
            tour.setGroupSize(groupSize);
            tour.setPrice(price);
            tour.setHot(isHot);
            return tour;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TourType getTourType() {
        return tourType;
    }

    public void setTourType(TourType tourType) {
        this.tourType = tourType;
    }

    public HotelType getHotelType() {
        return hotelType;
    }

    public void setHotelType(HotelType hotelType) {
        this.hotelType = hotelType;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public void toggleHot() {
        this.isHot = !isHot;
    }
}
