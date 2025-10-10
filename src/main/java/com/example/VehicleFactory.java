package com.example;

public final class VehicleFactory {
    private VehicleFactory(){}

    //Create a static method to create the vehicle
    public static Vehicle createVehicle(VehicleType type, String plate, String color, String owner){
        //Validate null inputs of type and plate
        if(type == null || plate == null) {return null;}

        //Create new variables with normalizations and validations
        String normalizedPlate = plate.trim().toUpperCase();
        //.isBlank means it's empty, use ternary operator and .trim to avoid spaces
        String normalizedColor = (color == null || color.isBlank()) ? "Unknown" : color.trim();
        String normalizedOwner = (owner == null || owner.isBlank()) ? "Unknown" : owner.trim(); 

        //Create the constructor depending on the type of vehicle
        switch(type){
            case CAR:
                return new Car(normalizedPlate, normalizedColor, normalizedOwner);
            case TRUCK:
                return new Truck(normalizedPlate, normalizedColor, normalizedOwner);
            case MOTORCYCLE:
                return new Motorcycle(normalizedPlate, normalizedColor, normalizedOwner);
            default:
                return null;
        }
    }
    
    //This is another option to create the object with only strings.
    public static Vehicle createVehicleFromString(String typeStr, String plate, String color, String owner) {
        VehicleType vt = VehicleType.fromString(typeStr);
        if (vt == null) return null;
        return createVehicle(vt, plate, color, owner);
    }
}
