package com.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ParkingLot {
    //Create a private constant named parkingLotId
    private final String parkingLotId;
    //We gonna create a list with another list inside (similar to dictionary)
    List<List<Slot>> slots;
    private final Map<String, int[]> ticketToPos = new HashMap<>();

    public ParkingLot(String parkingLotId, int numberFloors, int numberOfSlotsPerFloor){
        //Assing the variable into the constructor using a validation object to not receive an empty value
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

    public String parkVehicle(Vehicle vehicle){
        //Add a validation to prevent receiving null values
        if (vehicle == null) {
                System.out.println("Invalid vehicle (null)");
                return null;
            }
        //create vt to save the type of vehicle
        VehicleType saveVehicleType = vehicle.getType();
        
        // Use nested for loop to find first free slot that fits this vehicle type
        for (int i = 0; i < slots.size(); i++) {
            //Create another list for getting the information of the slots of the floor
            List<Slot> floorSlots = slots.get(i);
            for (int j = 0; j < floorSlots.size(); j++) {
                Slot slot = floorSlots.get(j);
                //Validate the slot is free and the size is correct for the type of vehicle
                //Use of functions of Slot and VehicleType
                if (slot.isFree() && saveVehicleType.fitsSlot(slot.getSize())) {
                    //Finally park
                    String ticket = generateTicketId(i+1, j+1);
                    slot.vehicle = vehicle;
                    slot.ticketId = ticket;
                    //Implement the variable and use the built-in function for the current time
                    slot.parkedAtMillis = System.currentTimeMillis();
                    //record position in map for O(1) lookup on unPark
                    ticketToPos.put(ticket, new int[]{i,j});
                    return ticket;
                }
            }
        }
        //If there's no slot available print the message
        return "No slot available for given type";
    }

    public String parkVehicle(String type, String regNumber, String color, String owner){
        // validation to prevent receiving null values
        if (type == null || regNumber == null || color == null || owner == null) {
            System.out.println("Invalid vehicle data");
            return null;
        }

        // use the factory to create a Vehicle (normalizes input)
        Vehicle vehicle = VehicleFactory.createVehicleFromString(type, regNumber, color, owner);
        if (vehicle == null) {
            System.out.println("Unknown vehicle type: " + type);
            return null;
        }

        // call the domain method
        return parkVehicle(vehicle);
    }

    private String generateTicketId(int floor, int slotNumber){
        return parkingLotId + "_" + floor + "_" + slotNumber;
    }

    public void unPark(String ticketId) {
    //Prevents null-pointer exceptions later and gives a clear error message to the caller.
    if (ticketId == null || ticketId.isBlank()) {
        System.out.println("Invalid ticket");
        return;
    }

    //Removes leading/trailing whitespace 
    ticketId = ticketId.trim();

    // Map lookup is O(1). Remove to avoid stale entries and keep the map sync. This is the most common way to do it.
    //Once remove is used ir returns the removed value to save into the position list
    int[] position = ticketToPos.remove(ticketId); 
    int floorIndex = -1, slotIndex = -1;

    //
    if (position != null) {
        floorIndex = position[0];
        slotIndex  = position[1];
    } else {
        // Fallback (alternativa): tolerant scan (helps if tickets existed before map or user passed "_1_4" or a different version of the ticketId)
        // Try exact match, endsWith match, or full-with-prefix match
        //This is a flag to see if matches
        boolean found = false;
        //ternary condicional, if it's coorect let ticketId like that. If it finds the fallback, it changes
        String maybeFull = ticketId.startsWith(parkingLotId) ? ticketId : parkingLotId + ticketId;

        for (int i = 0; i < slots.size() && !found; i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                String ticketIteration = slot.ticketId;
                //If it's empty continue with the next one.
                if (ticketIteration == null) continue;
                //If you find the ticket with some of the fallbacks then you continue
                if (ticketIteration.equals(ticketId) || ticketIteration.equals(maybeFull) || ticketIteration.endsWith(ticketId)) {
                    floorIndex = i;
                    slotIndex = j;
                    // remove stale map entry if present
                    ticketToPos.remove(ticketIteration);
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("Unknown ticket: " + ticketId);
            return;
        }
    }

    // Defensive bounds check (out of range)
    if (floorIndex < 0 || floorIndex >= slots.size()
        || slotIndex < 0 || slotIndex >= slots.get(floorIndex).size()) {
        System.out.println("Ticket refers to a non-existing slot: " + ticketId);
        return;
    }

    //call the information of slots
    Slot slot = slots.get(floorIndex).get(slotIndex);

    // Verify the slot has the same ticket (tolerant check for endsWith accepted earlier)
    if (slot.ticketId == null || !(slot.ticketId.equals(ticketId) || slot.ticketId.endsWith(ticketId))) {
        System.out.println("Ticket does not match the slot: " + ticketId);
        return;
    }

    // If there's no vehicle, nothing to unpark
    if (slot.vehicle == null) {
        System.out.println("Slot already empty for ticket: " + ticketId);
        // defensive cleanup in case the gaps were filled
        slot.ticketId = null;
        slot.parkedAtMillis = 0L;
        return;
    }

    // Use existing Slot helper to compute minutes
    long minutesParked = slot.getParkedMinutes();

    // Polymorphic fee calculation
    double fee = slot.vehicle.calculateFee(minutesParked);

    // Clear slot
    slot.vehicle = null;
    slot.ticketId = null;
    slot.parkedAtMillis = 0L;

    // Print friendly receipt
    System.out.printf("Unparked Vehicle (ticket=%s). Time parked: %d minutes. Fee: %.2f%n", ticketId, minutesParked, fee);
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
