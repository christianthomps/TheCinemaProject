

import java.sql.*;

public class RegisteredUser {

    private static final String MYHOST = "jbdc:mysql://127.0.01:3306/Project";//not used atm
    private static final String USER = "localhost";//"localhost";//not used atm
    private static final String PASS = "root";//"mysql-0327";//not used atm

    private static final String TEST = "jdbc:mysql://127.0.01:3306/Project?user=root&password=root";//change this to appropriate url/user/pass

    //Reference from MYSQL
    /* 1 uid: int
     * 2 Email: string
     * 3 Password: string // should not print password out anyway
     * 4 First name: string
     * 5 Last name: string
     * 6 Address: string
     * 7 Phone: int
     * 8 Payment: string
     * 9 Promo sub: bool
     * 10 status: enum*/

    //data stuff
    private int UID;
    private String EMAIL;
    private String PASSWORD;
    private String FNAME;
    private String LNAME;
    private String ADDRESS;
    private int PHONE;
    private String PAYMENT; //fix this later, should be credit card information, should switch to table I think or class
    private int PROMO;//change this to boolean?
    //private enum STATUS; //fix this later

    //container for stuff related to queries
    static Statement stmt = null;
    static ResultSet rs = null;
    static Connection con = null;
    static PreparedStatement pstmt = null;

    // Constructor for RegisteredUser();
    // Initializes variables to NULL/0 as placeholder.
    // Probably best used for debugging purpose?
    public RegisteredUser() {
        this.UID = 0;
        this.EMAIL = null;
        this.PASSWORD = null;
        this.FNAME = null;
        this.LNAME = null;
        this.ADDRESS = null;
        this.PHONE = 0;
        this.PAYMENT = null;
        this.PROMO = 0;
        //this.STATUS = ??? //I am not sure how to convert enum crap to something usable?
    }

    // Overloaded constructor for RegisteredUser();
    // Initializes variables to values set by user.
    public RegisteredUser(String email, String pass, String first, String last, String address, int phone, int subscription) {
        this.UID = 0;
        this.EMAIL = email;
        this.PASSWORD = pass;
        this.FNAME = first;
        this.LNAME = last;
        this.ADDRESS = address;
        this.PHONE = phone;
        this.PAYMENT = null;//Not sure how to implement this
        this.PROMO = subscription;
        //this.STATUS = ?????;
    }

    //Retrieve user's data from database using email
    public void retrieveUserData(String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(TEST);
            String query = "SELECT uid, Password, `First Name`, `Last Name`, Address, Phone, `Promo Sub` FROM RegisteredUser WHERE Email = ? ";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                this.UID = rs.getInt("uid");
                this.EMAIL = email;
                this.PASSWORD = rs.getString("Password");
                this.FNAME = rs.getString("First Name");
                this.LNAME = rs.getString("Last Name");
                this.ADDRESS = rs.getString("Address");
                this.PHONE = rs.getInt("Phone");
                this.PROMO = rs.getInt("Promo Sub");
            }

            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }//retrieveUserData(String email)

    //should return false or true if password match
    //this need to be updated to more secure way of testing than loading the data from other functions
    //this will be changed to string email, string password with connector for security
    public boolean checkPassword(String password) {
        return this.PASSWORD.equals(password);
    }

    //Adds user to database EX: user.addRegUserToDB();
    public void addRegUserToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(TEST);//change later to different URL if needed
            String query = "INSERT into RegisteredUser(Email, Password, `First Name`, `Last Name`, Address, Phone, `Promo Sub`) values(?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, EMAIL);
            pstmt.setString(2, PASSWORD);
            pstmt.setString(3, FNAME);
            pstmt.setString(4, LNAME);
            pstmt.setString(5, ADDRESS);
            pstmt.setInt(6, PHONE);
            pstmt.setInt(7, PROMO);
            pstmt.executeUpdate();

            pstmt.close();
            con.close();

            //System.out.println("ADDED to DB successfully");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }//addRegUserToDB

    //Deletes user from database EX: user.deleteRegUserFromDB();
    public void deleteRegUserFromDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(TEST);
            String query = "DELETE FROM RegisteredUser WHERE Email = ?";
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, EMAIL);
            pstmt.executeUpdate();

            pstmt.close();
            con.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        //clears the attributes to allow same object to be used.
        reset();

    }//deleteRegUserFromDB

    public void reset() {
        this.UID = 0;
        this.EMAIL = null;
        this.PASSWORD = null;
        this.FNAME = null;
        this.LNAME = null;
        this.ADDRESS = null;
        this.PHONE = 0;
        this.PAYMENT = null;
        this.PROMO = 0;
        //this.STATUS = ??? //I am not sure how to convert enum crap to something usable?
    }

    public void logout() {
        //for now, just reset to clear user crap
        reset();
    }

    //Deletes entire Registered User table, IRREVERSIBLE
    //can be used in conjunction with timer to clear database but not necessary for this class
    //Next revision, add opt of 1 or 2? 1 for delete which starts off at last digit once cleared? 2 for trunc, etc....
    public void deleteRegisterdUserTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(TEST);
            String query = "TRUNCATE TABLE RegisteredUser";//Truncate resets AUTO_INCREMENT to 1 unlike DELETE which does not reset AUTO_INCREMENT and will resume at last digit.
            pstmt = con.prepareStatement(query);

            pstmt.executeUpdate();

            pstmt.close();
            con.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    //@Override
    public String toString() {
        return "Current User data:\n"
                + "UID: \t" + UID + "\n"
                + "EMAIL: \t" + EMAIL + "\n"
                + "FULL NAME:\t" + FNAME + " " + LNAME + "\n"
                + "ADDRESS: \t" + ADDRESS + "\n"
                + "PHONE #\t" + PHONE + "\n"
                + "PROMOTION SUBSCRIPTION:\t" + (PROMO == 1 ? "YES\n" : "NO\n");
    }
}//RegisteredUser