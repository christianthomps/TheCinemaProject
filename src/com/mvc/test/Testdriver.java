package com.mvc.test;//import RegisteredUsers;

import com.mvc.controller.RegisteredUser;

public class Testdriver {

    public static void main(String[] args) {
        RegisteredUser user = new RegisteredUser("johndoe@gmail.com", "pass", "John", "Doe", "Nowhere 3546 Street", "123456789", 0);

        user.addRegUserToDB();

        user = new RegisteredUser("janedoe@gmail.com", "pass", "Jane", "Doe", "FSU Sucks", "123456788", 1);

        user.addRegUserToDB();

        user = new RegisteredUser("fsusucks@gmail.com", "pass", "Gibby", "Unknown", "Where do I live?", "12345677", 0);
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

        user = new RegisteredUser("IRULE@gmail.com", "pass", "Mister", "Nobody", "HURT ME", "12334545", 0);
        user.addRegUserToDB();

        test.retrieveUsersData("IRULE@gmail.com");
        System.out.println(test.toString());

        test.retrieveUsersData("fsusucks@gmail.com");//password check currently does not look into the db, will adjust this after demo to more secure method
        System.out.println("Testing password: \"blahblah\" -\t" + (test.checkPassword("blahblah") == true ? "Succeeded" : "Failed"));
        System.out.println("Testing password: \"pass\" -\t" + (test.checkPassword("pass") == true ? "Succeeded" : "Failed"));

        //clear db for next test
        test.deleteRegisterdUserTable();
    }
}