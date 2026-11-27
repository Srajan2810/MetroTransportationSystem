# Metro Transportation System: A Java-Based Route Planner
**Object-Oriented Programming in Java | College Lab Project**

---

## Project Overview

This project is a clean, student-friendly **Metro Route Planner and Transit System** in Java based on the **Delhi Metro**. It is designed specifically for an **OOP in Java Laboratory Course**, making all core concepts easy to learn, run, and explain during a lab viva.

### Key Features
1. **Official Delhi Metro Network (10 Operational Lines & 220+ Stations)**: Official DMRC Delhi-NCR Network lines across:
   - Line 1: **Red Line** (Rithala to Shaheed Sthal - 29 stations)
   - Line 2: **Yellow Line** (Samaypur Badli to Millennium City Centre Gurugram - 37 stations)
   - Line 3: **Blue Line Main** (Dwarka Sector 21 to Noida Electronic City - 50 stations)
   - Line 4: **Blue Line Branch** (Yamuna Bank to Vaishali - 8 stations)
   - Line 5: **Green Line** (Inderlok / Kirti Nagar to Brig. Hoshiar Singh - 24 stations)
   - Line 6: **Violet Line** (Kashmere Gate to Raja Nahar Singh - 34 stations)
   - Line 7: **Pink Line** (Majlis Park to Shiv Vihar - 38 stations)
   - Line 8: **Magenta Line** (Janakpuri West to Botanical Garden - 25 stations)
   - Line 9: **Grey Line** (Dwarka to Dhansa Bus Stand - 4 stations)
   - Orange Line: **Airport Express** (New Delhi to Yashobhoomi Dwarka Sector-25 - 7 stations)
2. **Interactive Route Planner**: Uses **Breadth-First Search (BFS)** to find the shortest route with fewest stops and seamless line transfers between any two stations across Delhi-NCR.
3. **Fare & Ticket Booking**: Calculates fares based on distance slabs.
4. **Threading & Concurrency**:
   - Background **Announcement Thread** simulating train arrivals.
5. **Garbage Collection (GC)**: Demonstrates object dereferencing and requests JVM GC via `System.gc()`.
6. **Dual User Interface**:
   - Console Menu ([`MetroApp.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/main/MetroApp.java))
   - Simple Java Swing GUI ([`SimpleMetroGUI.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/SimpleMetroGUI.java))

---

## Direct Mapping to Your Java Lab Syllabus

| Syllabus Concept | Where & How It Is Used | File Reference |
| :--- | :--- | :--- |
| **1. Primitives & JVM/JDK** | `int`, `double`, `boolean`, `char`, tokens, operators | [`Station.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Station.java) |
| **2. Static Variables & Methods** | `Station.totalStations`, `Ticket.totalTickets`, `Station.isValidCode()` | [`Station.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Station.java#L8), [`Ticket.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Ticket.java#L10) |
| **3. Constructors & Chaining** | Default constructor, parameterized constructors, and `this(...)` constructor chaining | [`Station.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Station.java#L23-L37) |
| **4. 1D & 2D Arrays** | 1D array for line station lists (`String[]`), 2D array for zone fare matrix (`int[][]`) | [`MetroPlanner.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/MetroPlanner.java#L22-L40) |
| **5. Single Inheritance** | `Passenger extends Person` | [`Passenger.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Passenger.java#L5) |
| **6. Multilevel Inheritance** | Demonstrated via class hierarchy patterns | [`Passenger.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Passenger.java#L5) |
| **7. Hierarchical Inheritance** | `Person` is inherited by both `Passenger` AND `Admin` | [`Admin.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Admin.java#L4), [`Passenger.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Passenger.java#L5) |
| **8. Association** | `Ticket` associates with `Passenger` and `Station` (Commuter buys a Ticket) | [`Ticket.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Ticket.java#L7-L17) |
| **9. Aggregation** | `MetroLine` has a `List<Station>` (stations can exist independently of lines) | [`MetroLine.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/MetroLine.java#L7-L13) |
| **10. Composition** | `Route` creates and owns `RouteStep` instances (steps are tied to Route) | [`Route.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Route.java#L7-L29) |
| **11. Abstract Classes & Methods** | `abstract class Person` (`abstract void displayInfo()`), `abstract class Passenger` (`abstract double getDiscountRate()`) | [`Person.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Person.java#L4), [`Passenger.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Passenger.java#L16) |
| **12. Interfaces** | `Searchable` and `FareCalculable` interfaces implemented by `MetroPlanner` | [`Searchable.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/Searchable.java), [`FareCalculable.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/FareCalculable.java) |
| **13. Packages** | Organized into `metro.model`, `metro.service`, `metro.exception`, `metro.threading`, `metro.main` | Clean package layout |
| **14. Java Strings** | `trim()`, `toUpperCase()`, `equalsIgnoreCase()`, `split()`, `StringBuilder`, `String.format()` | [`Station.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Station.java#L33), [`Route.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/model/Route.java#L49-L68) |
| **15. Exception Handling** | Custom checked exceptions (`InvalidStationException`, `RouteNotFoundException`) with `try-catch` | [`InvalidStationException.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/exception/InvalidStationException.java), [`RouteNotFoundException.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/exception/RouteNotFoundException.java) |
| **16. Collections Framework** | `ArrayList` (lists), `HashMap` (graph adjacency), `HashSet` (visited set), `LinkedList` (Queue for BFS) | [`MetroPlanner.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/MetroPlanner.java#L14-L17) |
| **17. Multithreading** | Extending `Thread` (`AnnouncementThread`) | [`AnnouncementThread.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/threading/AnnouncementThread.java#L4) |
| **18. Synchronization** | Thread `join()` to synchronize announcement before menu | [`MetroApp.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/main/MetroApp.java#L59) |
| **19. Inter-Thread Communication** | Thread coordination via `join()` and shared state | [`MetroApp.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/main/MetroApp.java#L56-L60) |
| **20. Garbage Collection** | Object reachability, nullifying references, and invoking `System.gc()` | [`GCDemo.java`](file:///Users/srajan/Desktop/Programming/JavaProject/src/metro/service/GCDemo.java#L6-L31) |

---

## Package and File Structure

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
│       │   └── RouteNotFoundException.java
│       ├── threading/                  # Multithreading
│       │   └── AnnouncementThread.java # Extends Thread
│       └── main/
│           └── MetroApp.java           # Interactive Console Menu
├── compile.sh                          # 1-click build script
├── run.sh                              # Run console route planner
├── run_gui.sh                          # Launch Java Swing GUI
└── README.md
```

---

## How to Compile and Run

### 1. Compile the Project
```bash
./compile.sh
```

### 2. Run the Interactive Console Application
```bash
./run.sh
```

### 3. Launch the Java Swing GUI
```bash
./run_gui.sh
```

---

## Quick Viva Questions & Answers

1. **What is the difference between Aggregation and Composition?**
   - **Aggregation** (`MetroLine` has `Station`s): The child (`Station`) can exist independently of the parent (`MetroLine`).
   - **Composition** (`Route` has `RouteStep`s): The child (`RouteStep`) is created inside `Route` and its lifecycle depends entirely on `Route`.

2. **How does BFS find the shortest route?**
   - It explores stations level by level using a **FIFO Queue** (`LinkedList`) and tracks visited stations with a `HashSet` to avoid infinite loops, guaranteeing the path with the minimum number of stops.
