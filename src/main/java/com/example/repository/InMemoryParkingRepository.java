package com.example.repository;

import com.example.model.Slot;
import com.example.model.SlotSize;
import com.example.model.Ticket;

import java.util.*;

/**
 * Simple in-memory repository.
 * Moves the List<List<Slot>> and ticket->pos map here.
 * NOTE: single-threaded; if you need concurrency later, use synchronization or concurrent maps.
 */
public class InMemoryParkingRepository implements ParkingRepository {

    private final String parkingLotId;
    private final List<List<Slot>> slots;
    private final Map<String, int[]> ticketToPos = new HashMap<>();
    private final Map<String, Ticket> tickets = new HashMap<>();

    private final int numberFloors;
    private final int slotsPerFloor;

    public InMemoryParkingRepository(String parkingLotId, int numberFloors, int slotsPerFloor, SlotSize[] initialLayoutPerFloor) {
        this.parkingLotId = Objects.requireNonNull(parkingLotId, "parkingLotId required");
        this.numberFloors = numberFloors;
        this.slotsPerFloor = slotsPerFloor;
        this.slots = new ArrayList<>(numberFloors);

        // Build default layout using provided layout array for first slots and medium for rest.
        for (int f = 0; f < numberFloors; f++) {
            List<Slot> floorSlots = new ArrayList<>(slotsPerFloor);
            // if initialLayoutPerFloor provided: add those first indexes as slot sizes
            if (initialLayoutPerFloor != null) {
                for (SlotSize s : initialLayoutPerFloor) {
                    floorSlots.add(new Slot(s));
                }
            }
            // fill remaining slots with MEDIUM by default
            for (int i = floorSlots.size(); i < slotsPerFloor; i++) {
                floorSlots.add(new Slot(SlotSize.MEDIUM));
            }
            slots.add(floorSlots);
        }
    }

    /* -------- Basic info -------- */
    @Override
    public int getNumberOfFloors() {
        return numberFloors;
    }

    @Override
    public int getSlotsPerFloor() {
        return slotsPerFloor;
    }

    /* -------- Slot access -------- */
    @Override
    public List<List<Slot>> getAllSlots() {
        return Collections.unmodifiableList(slots);
    }

    @Override
    public Optional<Slot> getSlot(int floorIndex, int slotIndex) {
        if (floorIndex < 0 || floorIndex >= slots.size()) return Optional.empty();
        List<Slot> floor = slots.get(floorIndex);
        if (slotIndex < 0 || slotIndex >= floor.size()) return Optional.empty();
        return Optional.of(floor.get(slotIndex));
    }

    @Override
    public void updateSlot(int floorIndex, int slotIndex, Slot slot) {
        // simple replace (caller should ensure valid index)
        slots.get(floorIndex).set(slotIndex, slot);
    }

    @Override
    public Optional<int[]> findFirstFreeSlot(SlotSize minSize) {
        for (int f = 0; f < slots.size(); f++) {
            List<Slot> floor = slots.get(f);
            for (int s = 0; s < floor.size(); s++) {
                Slot slot = floor.get(s);
                if (slot.isFree() && minSize != null && fits(slot.getSize(), minSize)) {
                    return Optional.of(new int[]{f, s});
                }
            }
        }
        return Optional.empty();
    }

    // helper: check if slotSize >= required (small logic kept simple)
    private boolean fits(SlotSize slotSize, SlotSize required) {
        // exact match behavior: only allow equals or larger if you define ordering
        if (slotSize == required) return true;
        if (required == SlotSize.SMALL) return slotSize == SlotSize.MEDIUM || slotSize == SlotSize.LARGE;
        if (required == SlotSize.MEDIUM) return slotSize == SlotSize.LARGE;
        return false;
    }

    /* -------- Ticket methods -------- */
    @Override
    public void saveTicket(Ticket ticket, int floorIndex, int slotIndex) {
        tickets.put(ticket.getId(), ticket);
        ticketToPos.put(ticket.getId(), new int[]{floorIndex, slotIndex});
        // also update slot meta (set ticketId and parkedAtMillis if desired)
        Slot slot = slots.get(floorIndex).get(slotIndex);
        slot.setTicketId(ticket.getId());
        slot.setParkedAtMillis(ticket.getInTimeMillis());
        // note: the Slot.vehicle should be set by service via updateSlot or a separate API
    }

    @Override
    public Optional<Ticket> findTicket(String ticketId) {
        return Optional.ofNullable(tickets.get(ticketId));
    }

    @Override
    public Optional<int[]> findPositionByTicket(String ticketId) {
        return Optional.ofNullable(ticketToPos.get(ticketId));
    }

    @Override
    public void removeTicket(String ticketId) {
        tickets.remove(ticketId);
        ticketToPos.remove(ticketId);
    }
}
