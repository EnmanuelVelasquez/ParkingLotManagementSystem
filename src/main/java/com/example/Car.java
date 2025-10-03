package com.example;

public class Car extends Vehicle{
    public Car (String plate, String color, String owner){
        super(plate, color, owner, VehicleType.CAR);
    }

    @Override
    public double calculateFee(long minutesParked){
        double hourly = 2.0;
        return Math.ceil(minutesParked / 60.0) * hourly;
    }

}
