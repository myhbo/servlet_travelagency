package controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import model.entity.enums.HotelType;
import model.entity.enums.TourType;

public class TourDTO {
    private long id;
    @NotBlank
    @Size(min = 5, max = 100, message = "{validation.tour.name}")
    private String name;
    private TourType tourType;
    private HotelType hotelType;
    @Positive(message = "{validation.tour.group}")
    private  int groupSize;
    @Positive(message = "{validation.tour.price}")
    private double price;
    private boolean isHot;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private TourType tourType;
        private HotelType hotelType;
        private  int groupSize;
        private double price;

        private Builder() {
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

        public TourDTO build() {
            TourDTO tourDTO = new TourDTO();
            tourDTO.setName(name);
            tourDTO.setTourType(tourType);
            tourDTO.setHotelType(hotelType);
            tourDTO.setGroupSize(groupSize);
            tourDTO.setPrice(price);
            return tourDTO;
        }
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }
}
