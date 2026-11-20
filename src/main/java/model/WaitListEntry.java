package model;

/** Stores one customer's request in the waiting list. */
public class WaitListEntry {
    private Customer customer;
    private int seatCount;

    public WaitListEntry(Customer customer, int seatCount) {
        this.customer = customer;
        this.seatCount = seatCount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getSeatCount() {
        return seatCount;
    }
}
