package com.mvc.controller;

import javax.servlet.RequestDispatcher;
import java.lang.String;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class forgotPassServlet extends HttpServlet {
    public forgotPassServlet(){
    }
    RegisteredUser user = new RegisteredUser();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");

        String userExists = user.retrieveUsersData(email);

        if(userExists == "USER EXISTS"){
            request.setAttribute("email", email);
            RequestDispatcher rd = request.getRequestDispatcher("recoverPassEmailServlet");
            rd.forward(request,response);
        }
        else{
            out.print("There is not an account associated with this email address. Try again.");
            RequestDispatcher rd=request.getRequestDispatcher("forgotPassword.jsp");
            rd.include(request,response);
        }

    }
}
