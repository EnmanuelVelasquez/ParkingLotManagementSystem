package com.example;

public class Motorcycle extends Vehicle{
    public Motorcycle (String plate, String color, String owner){
        super(plate, color, owner, VehicleType.MOTORCYCLE);
    }

    @Override
    public double calculateFee(long minutesParked){
        double hourly = 1.0;
        return Math.ceil(minutesParked / 60.0) * hourly;
    }

}