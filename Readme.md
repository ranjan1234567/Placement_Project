# Movie Ticket Booking System - Team L (Basic Version)

A beginner-friendly Java console project. The program uses `Scanner`, runs in the console, and stores data in memory. No GUI or database is required.

## Features

### Customer menu
1. View the movie catalogue.
2. View scheduled shows and seat availability.
3. Book one or more seats.
4. View and cancel the current customer's active bookings.
5. View the number of waiting-list requests for a show.

### Admin menu
1. View movies.
2. View shows and seat availability.
3. View the revenue report.
4. View the waiting-list count.

The revenue report is not available in the customer menu. The demo admin password is `admin123`.

## Waiting list

When a requested seat is already booked or invalid, the customer can choose to join the waiting list. Requests are stored in a manually written linked-node queue. It follows FIFO: First In, First Out. When a booking is cancelled, the program checks the queue. If enough seats are free for the first request, the system books those seats for that customer.

The waiting-list count is the number of customer requests in the queue. It is not the number of seats requested.

## Project structure

```text
MovieTicketBookingSystem_Basic/
├── src/main/java/
│   ├── app/
│   │   └── Main.java
│   ├── datastructures/
│   │   └── WaitingListQueue.java
│   ├── exceptions/
│   │   ├── BookingNotFoundException.java
│   │   ├── InvalidShowException.java
│   │   └── SeatNotAvailableException.java
│   ├── model/
│   │   ├── Admin.java
│   │   ├── Booking.java
│   │   ├── Customer.java
│   │   ├── Movie.java
│   │   ├── Show.java
│   │   ├── User.java
│   │   └── WaitListEntry.java
│   └── service/
│       ├── BookingService.java
│       ├── MovieService.java
│       └── ReportService.java
├── docs/
│   └── COMMIT_GUIDE.md
└── README.md
```

## Run using VS Code terminal (Windows PowerShell)

Run these commands from the project folder:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName })
java -cp out app.Main
```

## Run using macOS/Linux terminal

```bash
mkdir -p out
javac -d out $(find src/main/java -name "*.java")
java -cp out app.Main
```

## Assignment requirements covered

| Requirement | Where it is used |
|---|---|
| Encapsulation | Model fields are private and accessed through methods. |
| Inheritance | `Customer` and `Admin` extend `User`. |
| Abstraction | `User` is an abstract class. |
| Polymorphism | `showWelcome(User user)` calls the overridden `getRole()` method. |
| Custom exceptions | Three custom exceptions are created and handled with try-catch. |
| DSA | `WaitingListQueue` is a linked-node FIFO queue written by the team. |
| Collections | `ArrayList`, `HashMap`, and `HashSet` are used in actual features. |
| Comments | Classes and important methods have comments. |

## Important notes

- The movie list and show list are separate. A movie can have multiple scheduled shows.
- Each show has 20 sample seats. Available is shown as `A`, and booked is shown as `B`.
- Bookings and seat changes are stored in memory. They reset when the program exits.
- This project is intentionally written with basic Java syntax, loops, if-else, and traditional switch statements.
- Each team member should understand the complete program and make genuine commits while developing it.
