# 🅿️ Parking Lot System — Java OOP Project

A multi-floor parking lot management system built in Java, designed to practice and demonstrate core **Object-Oriented Programming** principles including inheritance, polymorphism, abstraction, encapsulation, and the use of design patterns like **Factory** and **Repository**.

---

## 📖 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Architecture & Design](#architecture--design)
- [Vehicle Types & Pricing](#vehicle-types--pricing)
- [Slot Layout](#slot-layout)
- [How to Run](#how-to-run)
- [Usage Example](#usage-example)
- [OOP Concepts Applied](#oop-concepts-applied)
- [Future Improvements](#future-improvements)

---

## Overview

This project simulates a real-world parking lot system where different vehicle types (Car, Truck, Motorcycle) can park on multiple floors, each with a defined slot layout. The system handles parking, unparking, fee calculation, and slot availability queries — all driven by clean OOP design.

---

## Features

- ✅ Multi-floor parking lot with configurable floors and slots per floor
- ✅ Support for three vehicle types: **Car**, **Truck**, and **Motorcycle**
- ✅ Automatic slot assignment based on vehicle type and slot size compatibility
- ✅ Ticket generation with unique ID per parking session
- ✅ Fee calculation based on time parked (billed per hour, rounded up)
- ✅ Unpark by ticket ID with a friendly receipt printout
- ✅ Display open and occupied slots filtered by vehicle type
- ✅ Factory pattern for clean vehicle instantiation from strings
- ✅ Repository pattern to decouple storage logic from business logic
- ✅ Fallback ticket lookup (tolerant scan) for flexible unparking

---

## Project Structure

```
src/
└── com/
    └── example/
        ├── model/
        │   ├── Vehicle.java          # Abstract base class for all vehicles
        │   ├── Car.java              # Car with $2.00/hr fee
        │   ├── Truck.java            # Truck with $3.50/hr fee
        │   ├── Motorcycle.java       # Motorcycle with $1.00/hr fee
        │   ├── VehicleType.java      # Enum: CAR, TRUCK, MOTORCYCLE
        │   ├── VehicleFactory.java   # Factory to create vehicles from type strings
        │   ├── Slot.java             # Represents a single parking slot
        │   ├── SlotSize.java         # Enum: SMALL, MEDIUM, LARGE
        │   ├── Ticket.java           # Ticket model with parking metadata
        │   └── ParkingLot.java       # Core domain class — orchestrates parking logic
        └── repository/
            ├── ParkingRepository.java          # Interface (port) for storage
            └── InMemoryParkingRepository.java  # In-memory implementation
```

---

## Architecture & Design

The system follows a **layered architecture** separating domain logic from storage concerns:

```
[ Main / Client ]
       │
       ▼
[ ParkingLot ]  ──── business logic, fee calculation, slot management
       │
       ▼
[ ParkingRepository ]  ──── storage interface (port)
       │
       ▼
[ InMemoryParkingRepository ]  ──── concrete in-memory implementation
```

**Key Design Patterns used:**

| Pattern | Where | Why |
|---|---|---|
| **Factory** | `VehicleFactory` | Decouples object creation from caller logic |
| **Repository** | `ParkingRepository` / `InMemoryParkingRepository` | Separates storage from business logic |
| **Template Method** | `Vehicle` (abstract) + subclasses | Each vehicle defines its own `calculateFee()` |

---

## Vehicle Types & Pricing

| Vehicle | Slot Size | Hourly Rate |
|---|---|---|
| 🏍️ Motorcycle | SMALL | $1.00 / hr |
| 🚗 Car | MEDIUM | $2.00 / hr |
| 🚛 Truck | LARGE | $3.50 / hr |

> Fees are rounded **up** to the nearest full hour using `Math.ceil()`. Any partial minute is counted as a full minute.

---

## Slot Layout

Each floor is initialized with the following default layout:

| Slot Index | Size | Fits |
|---|---|---|
| 1 | LARGE | Trucks |
| 2 | SMALL | Motorcycles |
| 3 | SMALL | Motorcycles |
| 4+ | MEDIUM | Cars |

> The number of floors and slots per floor is configurable at `ParkingLot` creation time.

---

## How to Run

### Prerequisites
- Java 11 or higher
- Any IDE (IntelliJ IDEA, Eclipse, VS Code) or the terminal with `javac`

### Compile & Run

```bash
# From the src/ directory
javac com/example/model/*.java com/example/repository/*.java
java com.example.model.Main
```

Or simply open the project in your IDE and run `Main.java`.

---

## Usage Example

```java
// Create a parking lot with 2 floors, 4 slots each
ParkingLot parkingLot = new ParkingLot("ParkingNo1", 2, 4);

// Park vehicles using the overloaded string-based method
String ticket1 = parkingLot.parkVehicle("car", "XAD-123", "Green", "Diego");
String ticket2 = parkingLot.parkVehicle("truck", "ASSD-34", "Black", "Tim");
String ticket3 = parkingLot.parkVehicle("motorcycle", "AAS-142", "Black", "James");

// Query slot availability
parkingLot.displayOccupiedSlots("car");
parkingLot.displayOpenSlots("car");
parkingLot.getNumberOfOpenSlots("motorcycle");

// Unpark by ticket ID (prints receipt with time and fee)
parkingLot.unPark(ticket1);

// Partial ticket IDs also work (fallback scan)
parkingLot.unPark("_1_4");
```

**Sample output:**
```
ParkingNo1_1_4
Occupied by car in  Floor: 1 slot: 4
Available slots for car:
Floor: 2 slot: 4
Number of slots available for car = 1
Unparked Vehicle (ticket=ParkingNo1_1_4). Time parked: 1 minutes. Fee: 2.00
```

---

## OOP Concepts Applied

| Concept | How it's used |
|---|---|
| **Abstraction** | `Vehicle` is an abstract class — you can't instantiate it directly |
| **Inheritance** | `Car`, `Truck`, `Motorcycle` all extend `Vehicle` |
| **Polymorphism** | `calculateFee()` behaves differently per vehicle type |
| **Encapsulation** | Fields are `private`/`final` with controlled access via getters |
| **Enums** | `VehicleType` and `SlotSize` model fixed domain constants cleanly |
| **Interface** | `ParkingRepository` defines a contract decoupled from implementation |
| **Factory Pattern** | `VehicleFactory` creates vehicles from raw strings without exposing constructors |
| **Defensive programming** | `null` checks, `IllegalArgumentException`, `Objects.requireNonNull` throughout |

---

## Future Improvements

- [ ] Add a `ParkingService` layer between `ParkingLot` and the repository for cleaner separation
- [ ] Persist data to a file or database (swap `InMemoryParkingRepository` for a new implementation)
- [ ] Add unit tests with JUnit 5
- [ ] Support multiple parking lots managed by a central system
- [ ] Add a simple CLI or REST API interface
- [ ] Introduce concurrency safety for multi-threaded environments

---

> 💡 *This project is part of a personal journey to deepen Java proficiency through practical, design-driven exercises.*
