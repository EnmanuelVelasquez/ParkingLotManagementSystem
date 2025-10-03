    package com.example;

    public class Main {
        public static void main(String[] args) {
            int numberOfFloors = 4;
            int numberOfSlotsPerFloor = 6;
            ParkingLot parkingLot1 = new ParkingLot("PR1234", numberOfFloors, numberOfSlotsPerFloor);
        
            parkingLot1.getNumberOfOpenSlots("car");
            
            System.out.println("Number of open slots:");
            parkingLot1.displayOpenSlots("car");

            String ticket1 = parkingLot1.parkVehicle("car", "MH-03", "Red");
            String ticket2 = parkingLot1.parkVehicle("car", "MH-04", "Purple");

            parkingLot1.displayOccupiedSlots("car");

            parkingLot1.unPark(ticket2);
            System.out.println("Number of occupied slots:");
            parkingLot1.displayOccupiedSlots("car");

            parkingLot1.displayOpenSlots("truck");
            parkingLot1.parkVehicle("truck", "SF-09", "blue");
            parkingLot1.displayOccupiedSlots("truck");
        }
    }