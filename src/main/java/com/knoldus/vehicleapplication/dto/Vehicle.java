package com.knoldus.vehicleapplication.dto;

import lombok.Data;

/**
 * Represents a Vehicle.
 */
@Data
public class Vehicle {
    /**
     * The unique identifier of the car.
     */
    private Integer carId;

    /**
     * The model of the car.
     */
    private String carModel;

    /**
     * The brand of the car.
     */
    private String brand;

    /**
     * The manufacturing year of the car.
     */
    private Integer year;

    /**
     * The color of the car.
     */
    private String color;

    /**
     * The mileage of the car.
     */
    private Double mileage;

    /**
     * The price of the car.
     */
    private Double price;

    /**
     * The location of the car.
     */
    private String location;
}

