public class Driver {

    public static void main(String[] args) {
        RegisteredUser user = new RegisteredUser("johndoe@gmail.com", "pass", "John", "Doe", "Nowhere 3546 Street", 123456789, 0);

        user.addRegUserToDB();

        user = new RegisteredUser("janedoe@gmail.com", "pass", "Jane", "Doe", "FSU Sucks", 123456788, 1);

        user.addRegUserToDB();

        user = new RegisteredUser("fsusucks@gmail.com", "pass", "Gibby", "Unknown", "Where do I live?", 12345677, 1);
        user.addRegUserToDB();

        RegisteredUser test = new RegisteredUser();

        test.retrieveUserData("fsusucks@gmail.com");
        System.out.println(test.toString());

        test.retrieveUserData("janedoe@gmail.com");
        System.out.println(test.toString());

        //test.deleteRegUserFromDB();

        test.retrieveUserData("johndoe@gmail.com");
        System.out.println(test.toString());
        //test.deleteRegUserFromDB();

        test.retrieveUserData("fsusucks@gmail.com");
        test.deleteRegUserFromDB();

        user = new RegisteredUser("IRULE@gmail.com", "pass", "Mister", "Nobody", "HURT ME", 12334545, 0);
        user.addRegUserToDB();

        test.retrieveUserData("IRULE@gmail.com");
        System.out.println(test.toString());

        test.deleteRegisterdUserTable();
    }
}