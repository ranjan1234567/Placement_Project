import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Hardcoded show details for the initial version
        String[] movies = {
            "1. Ramayana: Part1 (10:00 AM) - Rs. 250",
            "2. Godzilla Minus Zero (02:00 PM) - Rs. 300",
            "3. OM (06:30 PM) - Rs. 200"
        };
        int[] prices = {250, 300, 200};

        // 3 shows, 10 seats per show (false = Available, true = Booked)
        boolean[][] seats = new boolean[3][10];

        int choice;

        do {
            System.out.println("\n=== MOVIE TICKET BOOKING SYSTEM ===");
            System.out.println("1. View Show Listings");
            System.out.println("2. View Seats");
            System.out.println("3. Book a Ticket");
            System.out.println("4. Cancel a Ticket");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Display all available movie shows
                    System.out.println("\n--- Available Shows ---");
                    for (int i = 0; i < movies.length; i++) {
                        System.out.println(movies[i]);
                    }
                    break;

                case 2:
                    // Show seat layout for a selected movie
                    System.out.println("\nSelect a show (1-3):");
                    for (int i = 0; i < movies.length; i++) {
                        System.out.println(movies[i]);
                    }
                    System.out.print("Enter show number: ");
                    int showToView = scanner.nextInt() - 1;

                    if (showToView >= 0 && showToView < movies.length) {
                        System.out.println("\nSeat Layout ([O] = Available, [X] = Booked):");
                        for (int i = 0; i < seats[showToView].length; i++) {
                            String status = seats[showToView][i] ? "[X]" : "[O]";
                            System.out.print("Seat " + (i + 1) + status + "  ");
                        }
                        System.out.println();
                    } else {
                        System.out.println("Invalid show number.");
                    }
                    break;

                case 3:
                    // Book a seat for a selected movie
                    System.out.println("\nSelect a show to book (1-3):");
                    for (int i = 0; i < movies.length; i++) {
                        System.out.println(movies[i]);
                    }
                    System.out.print("Enter show number: ");
                    int showToBook = scanner.nextInt() - 1;

                    if (showToBook >= 0 && showToBook < movies.length) {
                        System.out.println("\nSeats ([O] = Available, [X] = Booked):");
                        for (int i = 0; i < seats[showToBook].length; i++) {
                            String status = seats[showToBook][i] ? "[X]" : "[O]";
                            System.out.print((i + 1) + status + " ");
                        }
                        System.out.print("\nEnter seat number to book (1-10): ");
                        int seatNum = scanner.nextInt() - 1;

                        if (seatNum >= 0 && seatNum < 10) {
                            if (!seats[showToBook][seatNum]) {
                                seats[showToBook][seatNum] = true;
                                System.out.println("Booking confirmed! Seat " + (seatNum + 1)
                                        + " booked. Amount: Rs. " + prices[showToBook]);
                            } else {
                                System.out.println("Seat " + (seatNum + 1) + " is already booked.");
                            }
                        } else {
                            System.out.println("Invalid seat number.");
                        }
                    } else {
                        System.out.println("Invalid show number.");
                    }
                    break;

                case 4:
                    // Cancel an existing booking
                    System.out.println("\nSelect the show to cancel from (1-3):");
                    for (int i = 0; i < movies.length; i++) {
                        System.out.println(movies[i]);
                    }
                    System.out.print("Enter show number: ");
                    int showToCancel = scanner.nextInt() - 1;

                    if (showToCancel >= 0 && showToCancel < movies.length) {
                        System.out.print("Enter seat number to cancel (1-10): ");
                        int seatNum = scanner.nextInt() - 1;

                        if (seatNum >= 0 && seatNum < 10) {
                            if (seats[showToCancel][seatNum]) {
                                seats[showToCancel][seatNum] = false;
                                System.out.println("Booking cancelled for Seat " + (seatNum + 1)
                                        + ". Refund: Rs. " + prices[showToCancel]);
                            } else {
                                System.out.println("Seat " + (seatNum + 1) + " is not booked yet.");
                            }
                        } else {
                            System.out.println("Invalid seat number.");
                        }
                    } else {
                        System.out.println("Invalid show number.");
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using the Movie Ticket Booking System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please select between 1 and 5.");
            }

        } while (choice != 5);

        scanner.close();
    }
}