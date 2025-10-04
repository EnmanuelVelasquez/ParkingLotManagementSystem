package com.example;

public class Motorcycle extends Vehicle{
    public Motorcycle (String plate, String color, String owner){
        super(plate, color, owner, VehicleType.MOTORCYCLE);
    }

    @Override
    public double calculateFee(long minutesParked){
        //We use a simple validation with IllegalArgumentException to avoid receiving numbers less than 0
        if (minutesParked < 0) throw new IllegalArgumentException ("minutesParked must be >= 0");
        double hourly = 1.0;
        //We use math.ceil to round up a fractional number to the following whole number e.g 0.75 = 1.0 
        return Math.ceil(minutesParked / 60.0) * hourly;
    }

}