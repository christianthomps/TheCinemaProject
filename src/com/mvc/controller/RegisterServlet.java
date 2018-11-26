package com.mvc.controller;

import javax.servlet.RequestDispatcher;
import java.lang.String;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
        String address = request.getParameter("street") + "/" + request.getParameter("city")  + ", " + request.getParameter("state") + "/" + request.getParameter("zipcode");

        int promoSub = 0;
        if(request.getParameter("promoOpt") !=  null){
            promoSub = 1;
        }
        int userType = 1;
        int userStatus = 1;

        RegisteredUser user = new RegisteredUser(firstName, lastName, email, password, phonenumber, address, userStatus, promoSub, userType);

        String status = user.addRegUserToDB();

        if (status == "SUCCESS")
        //On success, you can display a message to user on Home page
        {
            //Send email here?
            request.setAttribute("email", email);
            RequestDispatcher rd = request.getRequestDispatcher("RegistrationEmailServlet");
            rd.forward(request,response);
            //RequestDispatcher req = request.getRequestDispatcher("registrationConfirmation.html");
            //req.forward(request, response);
        }
        else{
            out.print("There was an error with registration.");
            RequestDispatcher rd=request.getRequestDispatcher("registration.jsp");
            rd.include(request,response);
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


    }
}
