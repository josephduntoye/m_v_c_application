/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

/**
 *
 * @author king_wizard
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();

        try {
            HttpSession session = request.getSession();
            List<User> users = us.getAll();
            // setting this value for the jsp page
            request.setAttribute("users", users);
            System.out.print(users.isEmpty());    
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            // setting an error message to the next jsp
            request.setAttribute("message", "error");
            System.out.println(ex);
        }
        // I might receive a request that contains a parameter 
        // with action = view&noteId=2
        String action = request.getParameter("action");
        if (action != null && action.equals("edit")) {
            try {
                String email = (String) (request.getParameter("email"));
                // get a specific note from notedb based on the id
                User user = us.get(email);
                // setting a variable to next jsp
                request.setAttribute("selectedUser", user);
                request.setAttribute("message", "editing");
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action != null && action.equals("delete")) {
            try {
                String email = (String) (request.getParameter("email"));
                // get a specific note from notedb based on the id
                us.delete(email);
                List<User> users = us.getAll();
                request.setAttribute("users", users);
                request.setAttribute("message", "editing");
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserService us = new UserService();

        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String userFN = request.getParameter("userFN");
        String userLN = request.getParameter("userLN");
        String userPassword = request.getParameter("userPassword");
        int userRole = Integer.parseInt(request.getParameter("userRole"));
        String error = "";

        try {
            if ((email.equals("") || email == null) && (userFN.equals("") || userFN == null) && (userLN.equals("") || userLN == null) && (userPassword.equals("") || userPassword == null)) {
                request.setAttribute("message", "errorInval");
                throw new IllegalArgumentException();
            } else if (email.equals("") || email == null) {
                error = "Email";
                throw new IllegalArgumentException();
            } else if (userFN.equals("") || userFN == null) {
                error = "FN";
                throw new IllegalArgumentException();
            } else if (userLN.equals("") || userLN == null) {
                error = "LN";
                throw new IllegalArgumentException();
            } else if (userPassword.equals("") || userPassword == null) {
                error = "PW";
                throw new IllegalArgumentException();
            }
            switch (action) {
                case "create":
                    us.insert(email, userFN, userLN, userPassword, userRole);
                    break;
                case "edit":
                    us.update(email, userFN, userLN, userPassword, userRole);
            }
            request.setAttribute("actUser", email);
            request.setAttribute("message", action);
        } catch (Exception e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
                request.setAttribute("message", "errorDup");
            } else if (e instanceof IllegalArgumentException) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
                request.setAttribute("message", "errorInval" + error);
            } else {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
                request.setAttribute("message", "error");
            }
        }

        try {
            List<User> users = us.getAll();
            request.setAttribute("users", users);
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("message", "error");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

}


