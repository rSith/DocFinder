package com.docfinder;

import com.docfinder.dao.UserDAO;
import com.docfinder.model.User;

public class TestUser {
    public static void main(String[] args) {
        System.out.println("=== 🧪 Testing User System ===\n");

        UserDAO userDAO = new UserDAO();

        // 1. Create a dummy user object
        // (Name, Age, Gender, Contact, Username, Password)
        User newUser = new User(
                "Sanjana Test",
                22,
                "Male",
                "071-1234567",
                "sanjana_test",
                "my_secure_pass"
        );

        // 2. Test Registration (Saving to DB)
        System.out.print("1. Attempting to Register User... ");
        if (userDAO.registerUser(newUser)) {
            System.out.println("✅ Success!");
        } else {
            System.out.println("❌ Failed. (User might already exist in DB)");
        }

        // 3. Test Login (Reading from DB)
        System.out.print("2. Attempting to Fetch User... ");
        User fetchedUser = userDAO.getUserByUsername("sanjana_test");

        if (fetchedUser != null) {
            System.out.println("✅ Success!");
            System.out.println("   -------------------------");
            System.out.println("   Name: " + fetchedUser.getName());
            System.out.println("   Age: " + fetchedUser.getAge());
            System.out.println("   Contact: " + fetchedUser.getContactNumber());
            System.out.println("   -------------------------");
        } else {
            System.out.println("❌ Failed. User not found.");
        }
    }
}