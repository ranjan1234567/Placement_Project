package model;

/** Customer can book and cancel movie tickets. */
public class Customer extends User {
    public Customer(String userId, String name, String phone) {
        super(userId, name, phone);
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}
