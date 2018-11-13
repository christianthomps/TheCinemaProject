

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class connectToMySQL {
    public static void main(String[] args) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/DatabaseName", "root", "password");
            Statement myStatment = myConn.createStatement();
            ResultSet myResult = myStatment.executeQuery("SELECT *  FROM student.tbl_student");
            while (myResult.next()) {
                System.out.println(myResult.getString("studentId") + " " + myResult.getString("StudentName") + " " + myResult.getString("StudentLastName") + " " + myResult.getString("StudentIdCard"));
            } //while
        }//try
        catch (Exception ex) {
            ex.printStackTrace();
        }//catch
    }//main
}//connectToMySQL

