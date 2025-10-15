package com.example;

public class Slot {
    private final SlotSize size;
    Vehicle vehicle;
    String ticketId;
    //Millis = milliseconds
    long parkedAtMillis;

    public Slot(SlotSize size){
        this.size = size;
        this.vehicle = null;
        this.ticketId = null;
    }

    public long getParkedMinutes(){
        //Validate it's not 0 or negative
        //Use L at the end of the number if u want it to be treated like long
        if (parkedAtMillis <= 0L) return 0L;
        //Use the built-in function to know the current time and minus the parked time
        //This feature uses the Unix Epoch
        long elapsedMillis = System.currentTimeMillis() - parkedAtMillis;
        //Get the minutes in integer by dividing two long numbers
        long minutes = elapsedMillis / 60000L; 
        //Treat any ms as 1 minute - if it's less than 1 minute I'd be 1 minute anyway
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
