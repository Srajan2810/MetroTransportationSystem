# Metro Transportation System: A Java-Based Route Planner
**Object-Oriented Programming in Java | College Lab Project**

---

## 🚇 Project Overview

This project is a clean, student-friendly **Metro Route Planner and Transit System** in Java based on the **Delhi Metro**. It is designed specifically for an **OOP in Java Laboratory Course**, making all core concepts easy to learn, run, and explain during a lab viva.

### Key Features
1. **Interactive Route Planner**: Uses **Breadth-First Search (BFS)** to find the shortest route with fewest stops between any two stations.
2. **Fare & Ticket Booking**: Calculates fares based on distance and applies commuter discounts (Standard Token vs. 10% Smart Card discount).
3. **Threading & Concurrency**:
   - Background **Announcement Thread** simulating train arrivals.
   - **Thread Synchronization** demonstration on a ticket counter.
   - **Inter-Thread Communication** using `wait()` and `notify()`.
4. **Garbage Collection (GC)**: Demonstrates object dereferencing and requests JVM GC via `System.gc()`.
5. **Applet Lifecycle**: Demonstrates the classic Java Applet lifecycle (`init`, `start`, `paint`, `stop`, `destroy`) with visual graphics.
6. **Dual User Interface**:
   - Console Menu ([`MetroApp.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/main/MetroApp.java))
   - Simple Java Swing GUI ([`SimpleMetroGUI.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/SimpleMetroGUI.java))

---

## 📋 Direct Mapping to Your Java Lab Syllabus

| Syllabus Concept | Where & How It Is Used | File Reference |
| :--- | :--- | :--- |
| **1. Primitives & JVM/JDK** | `int`, `double`, `boolean`, `char`, tokens, operators | [`Station.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Station.java) |
| **2. Static Variables & Methods** | `Station.totalStations`, `Ticket.totalTickets`, `Station.isValidCode()` | [`Station.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Station.java#L8), [`Ticket.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Ticket.java#L10) |
| **3. Constructors & Chaining** | Default constructor, parameterized constructors, and `this(...)` constructor chaining | [`Station.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Station.java#L23-L37) |
| **4. 1D & 2D Arrays** | 1D array for station facilities (`String[]`), 2D array for zone fare matrix (`int[][]`) | [`Station.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Station.java#L19), [`MetroPlanner.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/MetroPlanner.java#L19-L24) |
| **5. Single Inheritance** | `Passenger extends Person` | [`Passenger.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Passenger.java#L5) |
| **6. Multilevel Inheritance** | `Person` ➔ `Passenger` ➔ `SmartCardPassenger` | [`SmartCardPassenger.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/SmartCardPassenger.java#L6) |
| **7. Hierarchical Inheritance** | `Person` is inherited by both `Passenger` AND `Admin` | [`Admin.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Admin.java#L4), [`Passenger.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Passenger.java#L5) |
| **8. Association** | `Ticket` associates with `Passenger` and `Station` (Commuter buys a Ticket) | [`Ticket.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Ticket.java#L7-L17) |
| **9. Aggregation** | `MetroLine` has a `List<Station>` (stations can exist independently of lines) | [`MetroLine.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/MetroLine.java#L7-L13) |
| **10. Composition** | `Route` creates and owns `RouteStep` instances (steps are tied to Route) | [`Route.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Route.java#L7-L29) |
| **11. Abstract Classes & Methods** | `abstract class Person` (`abstract void displayInfo()`), `abstract class Passenger` (`abstract double getDiscountRate()`) | [`Person.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Person.java#L4), [`Passenger.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Passenger.java#L16) |
| **12. Interfaces** | `Searchable` and `FareCalculable` interfaces implemented by `MetroPlanner` | [`Searchable.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/Searchable.java), [`FareCalculable.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/FareCalculable.java) |
| **13. Packages** | Organized into `metro.model`, `metro.service`, `metro.exception`, `metro.threading`, `metro.applet`, `metro.main` | Clean package layout |
| **14. Java Strings** | `trim()`, `toUpperCase()`, `equalsIgnoreCase()`, `split()`, `StringBuilder`, `String.format()` | [`Station.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Station.java#L33), [`Route.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Route.java#L49-L68) |
| **15. Exception Handling** | Custom checked exceptions (`InvalidStationException`, `RouteNotFoundException`, `InsufficientBalanceException`) with `try-catch` | [`InvalidStationException.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/exception/InvalidStationException.java), [`InsufficientBalanceException.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/exception/InsufficientBalanceException.java) |
| **16. Collections Framework** | `ArrayList` (lists), `HashMap` (graph adjacency), `HashSet` (visited set), `LinkedList` (Queue for BFS) | [`MetroPlanner.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/MetroPlanner.java#L14-L17) |
| **17. Multithreading** | Extending `Thread` (`AnnouncementThread`), Implementing `Runnable` (`TicketCounterSyncDemo`) | [`AnnouncementThread.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/threading/AnnouncementThread.java#L4), [`TicketCounterSyncDemo.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/threading/TicketCounterSyncDemo.java#L5) |
| **18. Synchronization** | `synchronized` method on booking counter to prevent race condition | [`TicketCounterSyncDemo.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/threading/TicketCounterSyncDemo.java#L10) |
| **19. Inter-Thread Communication** | `wait()` (passenger waits for train) and `notify()` (train arrives) | [`TrainCoordinator.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/threading/TrainCoordinator.java#L12-L24) |
| **20. Garbage Collection** | Object reachability, nullifying references, and invoking `System.gc()` | [`GCDemo.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/GCDemo.java#L6-L31) |
| **21. Applet Life Cycle** | `init()`, `start()`, `paint()`, `stop()`, `destroy()` + Graphics (`drawLine`, `fillOval`, `drawString`) | [`MetroApplet.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/applet/MetroApplet.java#L17-L85) |

---

## 📂 Package and File Structure

```
JavaProject/
├── bin/                                # Compiled .class bytecode
├── src/
│   └── metro/
│       ├── model/                      # Domain classes
│       │   ├── Station.java            # Station attributes, constructors, 1D array
│       │   ├── MetroLine.java          # Aggregation of Stations
│       │   ├── Person.java             # Abstract base class
│       │   ├── Admin.java              # Hierarchical inheritance (extends Person)
│       │   ├── Passenger.java          # Single inheritance (extends Person)
│       │   ├── SmartCardPassenger.java # Multilevel inheritance (extends Passenger)
│       │   ├── Ticket.java             # Association (Ticket <-> Passenger, Station)
│       │   ├── RouteStep.java          # Composition unit
│       │   └── Route.java              # Composition container & StringBuilder
│       ├── service/                    # Business logic & algorithms
│       │   ├── Searchable.java         # Interface for route finding
│       │   ├── FareCalculable.java     # Interface for fare calculations
│       │   ├── MetroPlanner.java       # Graph adjacency list, BFS algorithm, 2D array
│       │   ├── GCDemo.java             # Garbage Collection demonstration
│       │   └── SimpleMetroGUI.java     # Simple Swing window
│       ├── exception/                  # Custom exceptions
│       │   ├── InvalidStationException.java
│       │   ├── RouteNotFoundException.java
│       │   └── InsufficientBalanceException.java
│       ├── threading/                  # Multithreading & Synchronization
│       │   ├── AnnouncementThread.java # Extends Thread
│       │   ├── TicketCounterSyncDemo.java # Implements Runnable & synchronized method
│       │   └── TrainCoordinator.java   # wait() and notify()
│       ├── applet/
│       │   └── MetroApplet.java        # Applet lifecycle (init, start, paint, stop, destroy)
│       └── main/
│           └── MetroApp.java           # Interactive Console Menu
├── compile.sh                          # 1-click build script
├── run.sh                              # Run console route planner
├── run_gui.sh                          # Launch Java Swing GUI
├── run_applet.sh                       # Launch Applet lifecycle window
└── README.md
```

---

## ⚡ How to Compile and Run

### 1. Compile the Project
```bash
./compile.sh
```

### 2. Run the Interactive Console Application
```bash
./run.sh
```

### 3. Launch the Simple Java Swing GUI
```bash
./run_gui.sh
```

### 4. Launch the Java Applet Demonstration
```bash
./run_applet.sh
```

---

## 🎓 Quick Viva Questions & Answers

1. **What is the difference between Aggregation and Composition?**
   - **Aggregation** (`MetroLine` has `Station`s): The child (`Station`) can exist independently of the parent (`MetroLine`).
   - **Composition** (`Route` has `RouteStep`s): The child (`RouteStep`) is created inside `Route` and its lifecycle depends entirely on `Route`.

2. **How does BFS find the shortest route?**
   - It explores stations level by level using a **FIFO Queue** (`LinkedList`) and tracks visited stations with a `HashSet` to avoid infinite loops, guaranteeing the path with the minimum number of stops.

3. **Why do we use `synchronized` in `TicketCounterSyncDemo`?**
   - To prevent a race condition when multiple passenger threads try to book the last available tickets at the exact same moment.

4. **How do `wait()` and `notify()` work in `TrainCoordinator`?**
   - `wait()` releases the object lock and pauses the passenger thread until the train arrives.
   - `notify()` is called by the train thread to wake up the waiting passenger thread.
