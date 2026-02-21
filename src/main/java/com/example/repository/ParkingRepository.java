package com.example.repository;

import java.util.List;
import java.util.Optional;

import com.example.model.Slot;
import com.example.model.SlotSize;
import com.example.model.Ticket;
/**
 * ParkingRepository: port for storage operations.
 * Minimal API to support parking/unparking operations.
 */
//Define an interface (a contract - the port) that storage implementations must follow
//This is useful to decouple the storage Logic from the business logic or storage implementation

public interface ParkingRepository {

    // Define getters for parking lot dimensions
    int getNumberOfFloors();
    int getSlotsPerFloor();

    //Slot access methods that help us to manage the slots
    List<List<Slot>> getAllSlots(); // read-only view of all the slots (this is optional)
    Optional<Slot> getSlot(int floorIndex, int slotIndex); //Safe lookup of a specific slot
    void updateSlot(int floorIndex, int slotIndex, Slot slot); // method to update a slot's status

    //Returns position of first free slot that meets the size requirement
    Optional<int[]> findFirstFreeSlot(SlotSize minSize);

    //Persist a ticket and tie it to a specific slot
    void saveTicket(Ticket ticket, int floorIndex, int slotIndex);
    //ticket lifecycle methods (find/remove); handles lookup and cleanup
    Optional<Ticket> findTicket(String ticketId);
    Optional<int[]> findPositionByTicket(String ticketId);
    void removeTicket(String ticketId);
}
