package com.example;

//We create the enumerator for the types of vehicle
//The main reason is becuase it makes the code easier to understand and 
//We use the reserved word enum insted of class

public enum VehicleType {
    MOTORCYCLE,
    CAR,
    TRUCK;

//Each of these ones are a singletos constant so we compare them with ==

    public boolean fitsSlot(SlotSize slotSize){
        //This validate it a specific vehicle type can fit in a slot of the given size
        //We use "This" so it switches on the current enum constant
        switch (this) {
            case MOTORCYCLE: //Motorcycle fits small
            return slotSize == SlotSize.SMALL;
            case CAR:        //Fits only medium 
            return slotSize == SlotSize.MEDIUM;     
            case TRUCK:      //Fits only large slots
            return slotSize == SlotSize.LARGE;
            default:         //Defensive fallback, shouldn't happen unless the enum changes
            return false;
        }
    }

    // this fromString method help us to convert a user vlaue like "car" into the corresponding vehicle constant (CAR)
    public static VehicleType fromString(String s){
        //Avoid calling methods on null
        if  (s==null){
            return null;
        } 
        try {
            //Removes leading trailing whitespace and uppercases the string (ABC)
            return  VehicleType.valueOf(s.trim().toUpperCase());
            //IllegalArgumentException helps us to validate we receive the enum constant (CAR,TRUCK,MOTORCYCLE)
        } catch (IllegalArgumentException e) {
            //If it doesn't match it throws null 
            return null;
        }
    }
}
