package com.mvc.test;

import com.mvc.controller.RegisteredUser;

public class Driver {

    public static void main(String[] args) {
        RegisteredUser user = new RegisteredUser("johndoe@gmail.com", "pass", "John", "Doe", "Nowhere 3546 Street", "1234567890", 0);

        user.addRegUserToDB();

        user = new RegisteredUser("janedoe@gmail.com", "pass", "Jane", "Doe", "FSU Sucks", "1234567890", 1);

        user.addRegUserToDB();

        user = new RegisteredUser("fsusucks@gmail.com", "pass", "Gibby", "Unknown", "Where do I live?", "1234567890", 1);
        user.addRegUserToDB();

        RegisteredUser test = new RegisteredUser();

        test.retrieveUsersData("fsusucks@gmail.com");
        System.out.println(test.toString());

        test.retrieveUsersData("janedoe@gmail.com");
        System.out.println(test.toString());

        //test.deleteRegUserFromDB();

        test.retrieveUsersData("johndoe@gmail.com");
        System.out.println(test.toString());
        //test.deleteRegUserFromDB();

        test.retrieveUsersData("fsusucks@gmail.com");
        test.deleteRegUserFromDB();

        user = new RegisteredUser("IRULE@gmail.com", "pass", "Mister", "Nobody", "HURT ME", "1234567890", 0);
        user.addRegUserToDB();

        test.retrieveUsersData("IRULE@gmail.com");
        System.out.println(test.toString());

        test.deleteRegisterdUserTable();
    }
}