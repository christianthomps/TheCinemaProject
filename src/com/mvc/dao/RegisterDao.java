package com.mvc.dao;

import java.lang.String;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mvc.bean.RegisterBean;
import com.mvc.util.connectToMySQL;


public class RegisterDao {

    public String registerUser(RegisterBean registerBean)
    {
        String firstName = registerBean.getfirstName();
        String lastName = registerBean.getlastName();
        String email = registerBean.getEmail();
        String password = registerBean.getPassword();
        String phonenumber = registerBean.getPhonenumber();
        long longUID = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        String UID = Long.toString(longUID);
        String address = registerBean.getAddress();
        String promoOpt = registerBean.getPromoOpt();

        System.out.println("Take variables from servlet");

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try
        {
            con = connectToMySQL.createConnection();
            String query = "insert into users(UID, firstName, lastName, email, password, phonenumber, promoOpt) values (?,?,?,?,?,?,?)"; //Insert user details into the table 'USERS'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setString(1, UID);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, phonenumber);
            preparedStatement.setString(7, promoOpt);

            int i= preparedStatement.executeUpdate();

            if (i!=0)  //Just to ensure data has been inserted into the database
                return "SUCCESS";
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return "Oops.. Something went wrong there..!";  // On failure, send a message from here.
    }
}