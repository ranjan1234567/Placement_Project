package app;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.BookingNotFoundException;
import exceptions.InvalidShowException;
import exceptions.SeatNotAvailableException;
import model.Admin;
import model.Booking;
import model.Customer;
import model.Movie;
import model.Show;
import model.User;
import service.BookingService;
import service.MovieService;
import service.ReportService;

/** Main class: displays menus and takes input from the user. */
public class Main {
    private Scanner sc = new Scanner(System.in);
    private MovieService movieService = new MovieService();
    private BookingService bookingService = new BookingService(movieService);
    private ReportService reportService = new ReportService();
    private Customer currentCustomer;
    private int customerCounter = 0;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    /** Starts the application and loads sample movies and shows. */
    public void start() {
        movieService.loadSampleData();

        System.out.println("====================================");
        System.out.println("     MOVIE TICKET BOOKING SYSTEM");
        System.out.println("====================================");

        int roleChoice = -1;
        while (roleChoice != 0) {
            System.out.println("\n1. Continue as Customer");
            System.out.println("2. Login as Admin");
            System.out.println("0. Exit");
            roleChoice = readInt("Choose your role: ");

            if (roleChoice == 1) {
                customerLogin();
            } else if (roleChoice == 2) {
                adminLogin();
            } else if (roleChoice == 0) {
                System.out.println("Thank you. Goodbye!");
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }

    /** Creates a customer account for this demo session. */
    private void customerLogin() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your phone number: ");
        String phone = sc.nextLine();

        customerCounter++;
        String customerId = "C00" + customerCounter;
        currentCustomer = new Customer(customerId, name, phone);
        showWelcome(currentCustomer);
        customerMenu();
    }

    /** Checks the demo password before showing admin options. */
    private void adminLogin() {
        System.out.print("Enter admin password: ");
        String password = sc.nextLine();

        if (password.equals("admin123")) {
            Admin admin = new Admin("A001", "Administrator", "Not provided");
            showWelcome(admin);
            adminMenu();
        } else {
            System.out.println("Wrong password. Admin access denied.");
        }
    }

    /** Uses the parent User type. The correct getRole() method runs for each user. */
    private void showWelcome(User user) {
        System.out.println("Welcome, " + user.getName() + " (" + user.getRole() + ")");
    }

    /** Menu available to customers. Revenue is not shown here. */
    private void customerMenu() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n----------- CUSTOMER MENU -----------");
            System.out.println("1. View movies");
            System.out.println("2. View shows and seat availability");
            System.out.println("3. Book tickets");
            System.out.println("4. Cancel my booking");
            System.out.println("5. View waiting-list count");
            System.out.println("0. Logout");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewMovies();
                    break;
                case 2:
                    viewShowsAndSeats();
                    break;
                case 3:
                    bookTickets();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    viewWaitingListCount();
                    break;
                case 0:
                    System.out.println("Customer logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /** Menu available only to an admin. */
    private void adminMenu() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n------------- ADMIN MENU ------------");
            System.out.println("1. View movies");
            System.out.println("2. View shows and seat availability");
            System.out.println("3. View revenue report");
            System.out.println("4. View waiting-list count");
            System.out.println("0. Logout");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewMovies();
                    break;
                case 2:
                    viewShowsAndSeats();
                    break;
                case 3:
                    reportService.printRevenueReport(bookingService);
                    break;
                case 4:
                    viewWaitingListCount();
                    break;
                case 0:
                    System.out.println("Admin logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /** Option 1 displays movie details only. */
    private void viewMovies() {
        System.out.println("\n--------------- MOVIES ---------------");
        ArrayList<Movie> movies = movieService.getMovies();

        for (int i = 0; i < movies.size(); i++) {
            System.out.println(movies.get(i));
        }
    }

    /** Option 2 displays scheduled shows and the seat map for one selected show. */
    private void viewShowsAndSeats() {
        showAllShows();
        System.out.print("Enter show ID to see seats (or 0 to return): ");
        String showId = sc.nextLine();

        if (showId.equals("0")) {
            return;
        }

        Show show = movieService.findShow(showId);
        if (show == null) {
            System.out.println("Show not found.");
            return;
        }

        displaySeatMap(show);
    }

    /** Prints all show details. */
    private void showAllShows() {
        System.out.println("\n---------------- SHOWS ----------------");
        ArrayList<Show> shows = movieService.getShows();

        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);
            Movie movie = movieService.findMovie(show.getMovieId());
            System.out.println(show.getShowId() + " | " + movie.getTitle()
                    + " | " + show.getTheatreName()
                    + " | " + show.getShowTime()
                    + " | Rs." + show.getTicketPrice()
                    + " | Available seats: " + show.getAvailableSeatCount()
                    + "/" + show.getTotalSeats());
        }
    }

    /** Prints available and booked seats for a selected show. */
    private void displaySeatMap(Show show) {
        System.out.println("\nSeat map: A = Available, B = Booked");

        for (int seat = 1; seat <= show.getTotalSeats(); seat++) {
            if (show.isSeatBooked(seat)) {
                System.out.print("[" + seat + ":B] ");
            } else {
                System.out.print("[" + seat + ":A] ");
            }

            if (seat % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println("\nAvailable seats: " + show.getAvailableSeatCount());
    }

    /** Takes seat numbers and asks the customer about the waiting list if booking fails. */
    private void bookTickets() {
        showAllShows();
        System.out.print("Enter show ID: ");
        String showId = sc.nextLine();

        Show show = movieService.findShow(showId);
        if (show == null) {
            System.out.println("Show not found.");
            return;
        }

        displaySeatMap(show);
        int seatCount = readInt("How many seats do you want? ");

        if (seatCount <= 0 || seatCount > show.getTotalSeats()) {
            System.out.println("Enter a valid number of seats.");
            return;
        }

        ArrayList<Integer> seatNumbers = new ArrayList<Integer>();
        for (int i = 1; i <= seatCount; i++) {
            int seat = readInt("Enter seat number " + i + ": ");
            seatNumbers.add(seat);
        }

        try {
            Booking booking = bookingService.bookTickets(currentCustomer, showId, seatNumbers);
            System.out.println("\nBooking successful!");
            System.out.println(booking);
        } catch (SeatNotAvailableException e) {
            System.out.println("Booking failed: " + e.getMessage());
            System.out.print("Do you want to join the waiting list? (y/n): ");
            String answer = sc.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                try {
                    bookingService.joinWaitingList(currentCustomer, showId, seatCount);
                    System.out.println("You have joined the waiting list.");
                    System.out.println("Your request will be handled in FIFO order.");
                } catch (InvalidShowException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (InvalidShowException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Shows and cancels only the active bookings that belong to this customer. */
    private void cancelBooking() {
        ArrayList<Booking> myBookings = bookingService.getCustomerBookings(currentCustomer.getUserId());

        if (myBookings.size() == 0) {
            System.out.println("You do not have any active bookings.");
            return;
        }

        System.out.println("\n---------- YOUR BOOKINGS ----------");
        for (int i = 0; i < myBookings.size(); i++) {
            System.out.println(myBookings.get(i));
        }

        System.out.print("Enter booking ID to cancel: ");
        String bookingId = sc.nextLine();

        try {
            Booking cancelled = bookingService.cancelBooking(bookingId, currentCustomer.getUserId());
            System.out.println("Booking cancelled successfully.");

            // After cancellation, the oldest waiting request gets the available seats first.
            ArrayList<Booking> newBookings = bookingService.processWaitingList(
                    cancelled.getShow().getShowId());

            for (int i = 0; i < newBookings.size(); i++) {
                Booking newBooking = newBookings.get(i);
                System.out.println("Waiting-list request promoted for: "
                        + newBooking.getCustomer().getName()
                        + ". New booking ID: " + newBooking.getBookingId());
            }
        } catch (BookingNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Displays how many customer requests are waiting for a selected show. */
    private void viewWaitingListCount() {
        System.out.print("Enter show ID: ");
        String showId = sc.nextLine();

        try {
            int count = bookingService.getWaitingListCount(showId);
            System.out.println("Waiting-list requests for " + showId.toUpperCase() + ": " + count);
        } catch (InvalidShowException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Reads an integer safely so letters do not crash the application. */
    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
