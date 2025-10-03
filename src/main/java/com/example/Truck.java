package com.example;

public class Truck extends Vehicle{
    public Truck (String plate, String color, String owner){
        super(plate, color, owner, VehicleType.TRUCK);
    }

    @Override
    public double calculateFee(long minutesParked){
        double hourly = 3.5;
        return Math.ceil(minutesParked / 60.0) * hourly;
    }

}
