package com.mvc.controller;

import java.lang.String;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.bean.RegisterBean;
import com.mvc.dao.RegisterDao;

public class RegisterServlet extends HttpServlet {
    public RegisterServlet() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //Copying all the input parameters in to local variables
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phonenumber = request.getParameter("phonenumber");
        String address = request.getParameter("address");
        int promoOpt = Integer.parseInt(request.getParameter("promoOpt"));
        System.out.println("Set variables.");

        RegisteredUser user = new RegisteredUser(email, password, firstName, lastName, address, phonenumber, promoOpt);

        user.addRegUserToDB();

        response.sendRedirect("registrationConfirmation.html  ");
        if (user.getStatus() == "SUCCESS")
        //On success, you can display a message to user on Home page
        {
            response.sendRedirect("/registrationConfirmation.html");
        }
        else{
            response.sendRedirect("/index.html");
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


    }
}
            //if(SUCCESS){
            //forward to confirmation page
            //}
            //else{
            //error
            //}









            //Using Java Beans - An easiest way to play with group of related data
            /*
            registerBean.setfirstName(firstName);
            registerBean.setlastName(lastName);
            registerBean.setEmail(email);
            registerBean.setPassword(password);
            registerBean.setPhonenumber(phonenumber);
            registerBean.setAddress(address);
            registerBean.setPromoOpt(promoOpt);
            RegisterDao registerDao = new RegisterDao();
            System.out.println("Added variables to bean");


            //The core Logic of the Registration application is present here. We are going to insert user data in to the database.
            //String userRegistered = registerDao.registerUser(registerBean);

            if (userRegistered.equals("SUCCESS"))
            //On success, you can display a message to user on Home page
            {
                request.getRequestDispatcher("/registrationConfirmation.html").forward(request, response);
            } else
            //On Failure, display a meaningful message to the User.
            {
                request.setAttribute("errMessage", userRegistered);
                request.getRequestDispatcher("/registration.jsp").forward(request, response);
            }
            */
