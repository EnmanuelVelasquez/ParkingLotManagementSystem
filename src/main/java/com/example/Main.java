    package com.example;

    public class Main {
        public static void main(String[] args) {
            int numberOfFloors = 2;
            int numberOfSlotsPerFloor = 4;
            ParkingLot parkingLot1 = new ParkingLot("ParkingNo1", numberOfFloors, numberOfSlotsPerFloor);
            
            Car car1 = new Car("XAD-123", "Green", "Diego");
            Car car2 = new Car("ADF-123", "Blue", "Fred");
            Truck truck1 = new Truck("ASSD-34", "Black-White", "Tim");
            Motorcycle motorcycle1 = new Motorcycle("AAS-142", "Black", "James");


            System.out.println(parkingLot1.parkVehicle(car1.getType().toString(), car1.getLicensePlate(), car1.getColor(), car1.getOwnerName()));
            System.out.println(parkingLot1.parkVehicle(car2.getType().toString(), car2.getLicensePlate(), car2.getColor(), car2.getOwnerName()));
            System.out.println(parkingLot1.parkVehicle("car", "ADF-46", "BLUE", "Ritt"));
            System.out.println(parkingLot1.parkVehicle(truck1.getType().toString(), truck1.getLicensePlate(), truck1.getColor(), truck1.getOwnerName()));
            System.out.println(parkingLot1.parkVehicle(motorcycle1.getType().toString(), motorcycle1.getLicensePlate(), motorcycle1.getColor(), motorcycle1.getOwnerName()));


            parkingLot1.displayOccupiedSlots("car");
            parkingLot1.displayOpenSlots("car");
            parkingLot1.getNumberOfOpenSlots("car");
            parkingLot1.displayOccupiedSlots("truck");
            parkingLot1.displayOpenSlots("truck");
            parkingLot1.getNumberOfOpenSlots("truck");
            parkingLot1.displayOccupiedSlots("motorcycle");
            parkingLot1.displayOpenSlots("motorcycle");
            parkingLot1.getNumberOfOpenSlots("motorcycle");

            parkingLot1.unPark("_1_4");
            System.out.println(parkingLot1.parkVehicle("car", "ADF-46", "BLUE", "Ritt"));
            parkingLot1.displayOccupiedSlots("car");

            parkingLot1.parkVehicle("bus", "BUS1", "white","Dan");
            
        }
    }