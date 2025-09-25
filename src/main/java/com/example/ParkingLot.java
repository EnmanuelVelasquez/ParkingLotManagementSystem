package com.example;
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    String parkingLotId;
    //We gonna create a list with 2 rows and 3 columns (2*3)
    List<List<Slot>> slots;

    public ParkingLot(String parkingLotId, int numberFloors, int numberOfSlotsPerFloor){
        this.parkingLotId = parkingLotId;
        slots = new ArrayList<>(); //Initialize all the slots. Firs floor = trucks, second = bike, third = cars;
        for (int floor = 0; floor < numberFloors; floor++){
            slots.add(new ArrayList<>());
            //We create a new list named floorSlots for filling the inner slots
            List<Slot> floorSlots = slots.get(floor);
            floorSlots.add(new Slot("truck"));
            floorSlots.add(new Slot("bike"));
            floorSlots.add(new Slot("bike"));

            //We do the same for the cars with the rest of slots of the current floor
            for(int slotIndex = 3; slotIndex < numberOfSlotsPerFloor; slotIndex++){
                floorSlots.add(new Slot("car"));
            }
        }
        
    }

    public String parkVehicle(String type, String regNumber, String color){
        //We add a validation to prevent receiving null values
        if (type == null || regNumber == null || color == null) {
                System.out.println("Invalid vehicle data");
                return null;
            }

       //First we create a Vehicle object
        Vehicle vehicle = new Vehicle(type, regNumber, color);
        for(int i = 0; i < slots.size();i++){
            for (int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                //We use the .equal comparison for Strings in the type validation. Remember == it's more for some booleans (in depends)
                if(slot.type.equals(type) && slot.vehicle == null){
                    slot.vehicle = vehicle;
                    slot.ticketId = generateTicketId(i + 1, j + 1);
                    return slot.ticketId;
                }
            }
        }
        System.out.println("NO slot available for given type");
        return null;
    }

    private String generateTicketId(int floor, int slno){
        return parkingLotId + "_" + floor + "_" + slno;
    }

    public void unPark(String ticketId){
        //This .split is spliting the String into pieces using the underscore as the separator
        String[] extract = ticketId.split("_");

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

        for (int i = 0; i < slots.size(); i++) {                // (1) iterate over the floors of the mainList of Slots (i = floor index)
            for (int j = 0; j < slots.get(i).size(); j++) {     // (2) iterate slots on floor i (j = slot index)
                if (i == floorIndex && j == slotIndex) {       // (3) check if this is the target slot
                    Slot slot = slots.get(i).get(j);           // (4) fetch (Ir a buscar, ir por; venderse) the Slot object
                    slot.vehicle = null;                       // (5) remove the vehicle from the slot
                    slot.ticketId = null;                      // (6) clear the ticket id
                    System.out.println("Unparked Vehicle");    // (7) print confirmation
                }
            }
        }

    }

    public void getNumberOfOpenSlots(String type){
        int count = 0;
        //We start creating a loop where we iterate over the floors of the lists
        for (List<Slot> floor : slots) {
            //Now we iterate over the slots of the floors
            for (Slot slot : floor) {
                //Once we find a null space with the requiered type we count how many gaps we have
                if(slot.vehicle == null && slot.type.equals(type)) count++;
            }
        }
        System.out.println("Number of slots available for " + type + " = " + count);
    }

    public void displayOpenSlots(String type){
        System.out.println("Available slots for " + type + ":");
        //We iterate over the floors (whole first list)
        for(int i = 0; i < slots.size(); i++){
            //Then we itareate over the slots of each floor (slots)
            for(int j = 0; j < slots.get(i).size(); j++){
                //Now we make a validation to make sure we get the open slots and then print them
                Slot slot = slots.get(i).get(j);
                if(slot.vehicle == null && slot.type.equals(type) ){
                    //We print in a basic way, remember we need to add +1 'cause we don't need the positions from 0
                    System.out.println("Floor: "+ (i+1) + " slot: " + (j+1));
                }
            }
        }
    }

    public void displayOccupiedSlots(String type){
        //We iterate over the floors (whole first list)
        for(int i = 0; i < slots.size(); i++){
            //Then we itareate over the slots of each floor (slots)
            for(int j = 0; j < slots.get(i).size(); j++){
                //Now we make a validation to make sure we get the busy  slots and then print them
                Slot slot = slots.get(i).get(j);
                if(slot.vehicle != null && slot.type.equals(type) ){
                    //We print in a basic way, remember we need to add +1 'cause we don't need the positions from 0
                    System.out.println("Occupied by " + type + " in = " + "Floor: " + (i+1) + " slot: " + (j+1));
                }
            }
        }
    }
}
