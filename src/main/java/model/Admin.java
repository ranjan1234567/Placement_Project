package model;

/** Admin can view revenue reports. */
public class Admin extends User {
    public Admin(String userId, String name, String phone) {
        super(userId, name, phone);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
