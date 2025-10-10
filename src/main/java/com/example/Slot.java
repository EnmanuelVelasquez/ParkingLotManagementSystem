package com.example;

public class Slot {
    private final SlotSize size;
    Vehicle vehicle;
    String ticketId;
    long parkedAtMillis;

    public Slot(SlotSize size){
        this.size = size;
        this.vehicle = null;
        this.ticketId = null;
    }

    public long getParkedMinutes(){
        //Validate it's not 0 or negative
        if (parkedAtMillis <= 0L) return 0L;
        //Use the built-in function to know the current time and minus the parked time
        long elapsedMillis = System.currentTimeMillis() - parkedAtMillis;
        //Get the minutes in integer
        long minutes = elapsedMillis / 60000L; 
        //Treat any ms as 1 minute
        if(minutes == 0 && elapsedMillis > 0) minutes = 1;
        return minutes;
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
