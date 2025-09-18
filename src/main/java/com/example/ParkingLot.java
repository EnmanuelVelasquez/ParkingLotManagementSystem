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

    public String parkVehicle(String type, int regNumber, String color){
       //First we create a Vehicle object
        Vehicle vehicle = new Vehicle("truck", "XRK 892", "blue");
        for(int i = 0; i < slots.get(i).size();i++){
            for (int j = 0; j < slots.size(); j++) {
                Slot slot = slots.get(i).get(j);
                if(slot.type == type && slot.vehicle == null){
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

    public void unPark(int ticketId){
        String[] extract = ticketId.split("_");
        int floor_index = Integer.parseInt(extract[1]-1);
        int slot_index = Integer.parseInt(extract[2]-1);
    }

    public void getNumberOfOpenSlots(String type){

    }

    public void displayOpenSlots(String type){

    }

    public void displayOccupiedSlots(String type){

    }
}
