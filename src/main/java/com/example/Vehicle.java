package com.example;

//We create vehicle as abstract because we're gonna use this as the 
public abstract class Vehicle {
    private final String licensePlate;
    private final String color;
    private final VehicleType type;
    private String ownerName;


    protected Vehicle(String licensePlate, String color, String ownerName, VehicleType type){
        //validate to not have null information
        if (licensePlate == null || licensePlate.isBlank()){
            throw new IllegalArgumentException("Plate required");
        }
        // .trim helps us to avoid unwanted spaces and .toUpperCase helps us to use the capitalized word
        this.licensePlate = licensePlate.trim().toUpperCase();
        this.ownerName = ownerName;
        //we use the ternary operator for if; true = unkown ; false = color without spaces
        this.color = color == null ? "unknown" : color.trim();
        //The following line is a standtard helper in Java.util (java 7) that doesn't accept null types so the constructor would stop there
        this.type = java.util.Objects.requireNonNull(type, "type required");
    }

    //Do the getters of each attribute and the setter for the ownerName (simplified version)
    public String getLicensePlate(){ return licensePlate; }
    public String getColor(){ return color; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public VehicleType getType() { return type; }

    //We create and abstract method that will help us to calculate the fee of every vehicle
    public abstract double calculateFee (long minutesParked);
}
