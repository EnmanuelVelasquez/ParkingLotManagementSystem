package com.example;

public class Slot {
    private final SlotSize size;
    Vehicle vehicle;
    String ticketId;

    public Slot(SlotSize size){
        this.size = size;
        this.vehicle = null;
        this.ticketId = null;
    }

    //Getter of size
    public SlotSize getSize(){ return size; }

    //This method will help us to validate if the slot is available
    public boolean isFree() {return vehicle == null; }

    //This produces a quick description of the slot (useful for loggin or debugging)
    //We also use override because we're changing the behaviour of the base object.toString method
    @Override
    public String toString(){
        return "Slot{" + "size=" + size + ", ticketId=" + ticketId +
               ", occupied=" + (vehicle != null) + '}';
    }
}
