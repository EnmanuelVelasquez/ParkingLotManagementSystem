package com.example.repository;

import com.example.model.Slot;
import com.example.model.SlotSize;
import com.example.model.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * ParkingRepository: port for storage operations.
 * Minimal API to support parking/unparking operations.
 */
public interface ParkingRepository {

    /** Basic info */
    int getNumberOfFloors();
    int getSlotsPerFloor();

    /** Slot access */
    List<List<Slot>> getAllSlots(); // read-only view (optional)
    Optional<Slot> getSlot(int floorIndex, int slotIndex);
    void updateSlot(int floorIndex, int slotIndex, Slot slot); // persist slot changes

    /** Find a free slot that fits the required size. Returns floorIndex,slotIndex as int[2] or empty */
    Optional<int[]> findFirstFreeSlot(SlotSize minSize);

    /** Ticket persistence */
    void saveTicket(Ticket ticket, int floorIndex, int slotIndex);
    Optional<Ticket> findTicket(String ticketId);
    Optional<int[]> findPositionByTicket(String ticketId);
    void removeTicket(String ticketId);
}
