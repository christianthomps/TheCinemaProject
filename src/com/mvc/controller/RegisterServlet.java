package com.mvc.controller;

import javax.servlet.RequestDispatcher;
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

        if (user.getStatus() == "SUCCESS")
        //On success, you can display a message to user on Home page
        {
            RequestDispatcher req = request.getRequestDispatcher("registrationConfirmation.html");
            req.include(request, response);
        }
        else{
            response.sendRedirect("/registration.jsp");
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


    }
}
