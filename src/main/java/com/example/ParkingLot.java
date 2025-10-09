package com.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParkingLot {
    //Create a private constant named parkingLotId
    private final String parkingLotId;
    //We gonna create a list with 2 rows and 3 columns (2*3)
    List<List<Slot>> slots;

    public ParkingLot(String parkingLotId, int numberFloors, int numberOfSlotsPerFloor){
        //Assing the variable into the constrcutor using a validation object to not receive am empty data
        this.parkingLotId = Objects.requireNonNull(parkingLotId, "parkingLotId required");
        //Initialize the list of slots and then start filling the slots of each floor
        this.slots = new ArrayList<>(); 
        for (int floor = 0; floor < numberFloors; floor++){
            //Create the list for the slots of each floor and the fill it with the required parameters
            List<Slot> floorSlots = new ArrayList<>();
            //We fill the first three slots for Trucks, and motorcycles respectively 
            floorSlots.add(new Slot(SlotSize.LARGE));
            floorSlots.add(new Slot(SlotSize.SMALL));
            floorSlots.add(new Slot(SlotSize.SMALL));
            //Do the same for cars with the rest of slots of the current floor
            for(int slotIndex = 3; slotIndex < numberOfSlotsPerFloor; slotIndex++){
                floorSlots.add(new Slot(SlotSize.MEDIUM));
            }
            slots.add(floorSlots);
        }
    }

    public String parkVehicle(String type, String regNumber, String color, String Owner){
        //Add a validation to prevent receiving null values
        if (type == null || regNumber == null || color == null) {
                System.out.println("Invalid vehicle data");
                return null;
            }
       //Use the enum to create a new object depending on the type received in the parameters
       //Use froString function to 
        VehicleType vt = VehicleType.fromString(type);
        if (vt == null){
            System.out.println("Unkown vehicle type: " + type );
            return null;
        }

        //Implement a switch for every case of the enum and fill the parameters
        Vehicle vehicle;
        switch (vt) {
            case CAR:
                vehicle = new Car(regNumber, color, Owner);
                break;
            case MOTORCYCLE:
                vehicle = new Motorcycle(regNumber, color, Owner);
                break;
            case TRUCK:
                vehicle = new Truck(regNumber, color, Owner);
                break;
            default:
                System.out.println("Unsupported vehicle type: " + vt);
                return null;
        }

         // Find first free slot that fits this vehicle type
        for (int i = 0; i < slots.size(); i++) {
            //Create another list for getting the information of the slots of the floor
            List<Slot> floorSlots = slots.get(i);
            for (int j = 0; j < floorSlots.size(); j++) {
                Slot slot = floorSlots.get(j);
                //Validate the slot is free and the size is correct for the type of vehicle
                if (slot.isFree() && vt.fitsSlot(slot.getSize())) {
                    //Finally park
                    slot.vehicle = vehicle;
                    slot.ticketId = generateTicketId(i + 1, j + 1);
                    return slot.ticketId;
                }
            }
        }
        //If there's no slot available print the message
        System.out.println("No slot available for given type");
        return null;
    }


    private String generateTicketId(int floor, int slotNumber){
        return parkingLotId + " _" + floor + "_" + slotNumber;
    }



    public void unPark(String ticketId){
        if (ticketId == null) {
            System.out.println("Invalid ticket ");
            return;
        }

        //This .split is spliting the String into pieces using the underscore as the separator
        String[] extract = ticketId.split("_");

        if (extract.length < 3) {
            System.out.println("Invalid ticket format:" + ticketId);
            return;
        }

        //Initialize them outside the try 
        int floorIndex, slotIndex;
        try{
        //We take the floor Index from the first part
        floorIndex = Integer.parseInt(extract[1])-1;
        //We take the slot Index from the second part
        slotIndex = Integer.parseInt(extract[2])-1;  
        } catch (NumberFormatException e) {
            System.out.println("Invalid ticket numbers (not integers)" + ticketId);
            return;
        }

        if (floorIndex < 0 || floorIndex >= slots.size() || slotIndex < 0 || slotIndex >= slots.get(floorIndex).size()){
            System.out.println("Ticket refers to a non existing floor: " + ticketId);
            return;
        }

        Slot slot = slots.get(floorIndex).get(slotIndex);
        slot.vehicle = null;
        slot.ticketId = null;
        System.out.println("Unparked Vehicle with ticked Id: " + ticketId);

    }

    public void getNumberOfOpenSlots(String type){
        VehicleType vt = VehicleType.fromString(type);
        if( vt == null){
            System.out.println("Uknown vehicle type: " + type);
        }

        int count = 0;
        //Use the for elements where we iterate in the slots array
        for (List<Slot> floor : slots) {
            //Then iterate in every slot of the floor
            for(Slot slot : floor){
                //Validate every slot is free and has the same size
                if(slot.isFree() && vt.fitsSlot(slot.getSize())){
                    count++;
                }
            }
        }
        //Finally print the number of slots
        System.out.println("Number of slots available for " + type + " = " + count);
    }

    public void displayOpenSlots(String type) {
        VehicleType vt = VehicleType.fromString(type);
        if (vt == null) {
            System.out.println("Unknown vehicle type: " + type);
            return;
        }

        System.out.println("Available slots for " + type + ":");
        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                if (slot.isFree() && vt.fitsSlot(slot.getSize())) {
                    System.out.println("Floor: " + (i + 1) + " slot: " + (j + 1));
                }
            }
        }
    }

    public void displayOccupiedSlots(String typeStr) {
        VehicleType vt = VehicleType.fromString(typeStr);
        if (vt == null) {
            System.out.println("Unknown vehicle type: " + typeStr);
            return;
        }

        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                if (slot.vehicle != null && slot.vehicle.getType() == vt) {
                    System.out.println("Occupied by " + typeStr + " in " + " Floor: " + (i + 1) + " slot: " + (j + 1));
                }
            }
        }
    }
}
