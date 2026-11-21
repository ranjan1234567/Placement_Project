package service;

import java.util.ArrayList;
import java.util.HashMap;

import model.Booking;

/** Creates a simple revenue report for the admin. */
public class ReportService {
    public void printRevenueReport(BookingService bookingService) {
        ArrayList<Booking> allBookings = bookingService.getAllBookings();
        HashMap<String, Double> revenueByShow = new HashMap<String, Double>();
        double totalRevenue = 0;
        int activeBookings = 0;

        for (int i = 0; i < allBookings.size(); i++) {
            Booking booking = allBookings.get(i);

            if (!booking.isCancelled()) {
                activeBookings++;
                totalRevenue = totalRevenue + booking.getTotalAmount();

                String showId = booking.getShow().getShowId();
                Double oldAmount = revenueByShow.get(showId);

                if (oldAmount == null) {
                    oldAmount = 0.0;
                }

                revenueByShow.put(showId, oldAmount + booking.getTotalAmount());
            }
        }

        System.out.println("\n========== REVENUE REPORT ==========");
        System.out.println("Bookings recorded: " + allBookings.size());
        System.out.println("Active bookings: " + activeBookings);
        System.out.println("Total revenue: Rs." + totalRevenue);
        System.out.println("Revenue by show:");

        if (revenueByShow.size() == 0) {
            System.out.println("No ticket sales yet.");
        } else {
            for (String showId : revenueByShow.keySet()) {
                System.out.println(showId + " : Rs." + revenueByShow.get(showId));
            }
        }

        System.out.println("====================================");
    }
}
