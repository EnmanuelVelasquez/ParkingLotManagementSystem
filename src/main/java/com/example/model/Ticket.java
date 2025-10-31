package com.example.model;

public class Ticket {
    private final String id;
    private final String plate;
    private final int floor;
    private final int slot;
    private final long inTimeMillis;

    public Ticket(String id, String plate, int floor, int slot, long inTimeMillis) {
        this.id = id;
        this.plate = plate;
        this.floor = floor;
        this.slot = slot;
        this.inTimeMillis = inTimeMillis;
    }

    public String getId() { return id; }
    public String getPlate() { return plate; }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public long getInTimeMillis() { return inTimeMillis; }
}
