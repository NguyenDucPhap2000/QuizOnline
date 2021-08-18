/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.QuizDAO;
import Entity.Role;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nguye
 */
public class RegisterControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        try {
            QuizDAO quiz = new QuizDAO();
            //set role up to jsp
            List<Role> list = quiz.getAllRole();
            request.setAttribute("list", list);
            String roleJsp = request.getParameter("role");
            int roleID = 0;
            // set role for each account
            if (roleJsp.trim().equals("Student")) {
                roleID = 2;
            } else if (roleJsp.trim().equals("Teacher")) {
                roleID = 1;
            }

            //get user, pass and email from jsp to servelet
            String name = request.getParameter("user");
            session.setAttribute("accountName", name);
            String pass = request.getParameter("pass");
            session.setAttribute("Password", pass);
            String email = request.getParameter("email");
            session.setAttribute("email", email);
            // check user input empty
            if (name.trim().length() == 0) {
                request.setAttribute("sucessOrDuplicate", "User name can not be empty");
                request.getRequestDispatcher("RegisterPage.jsp").forward(request, response);
                return;
            } else if (pass.trim().length() == 0) {
                request.setAttribute("sucessOrDuplicate", "Password can not be empty");
                request.getRequestDispatcher("RegisterPage.jsp").forward(request, response);
                return;
            } else if (email.trim().length() == 0) {
                request.setAttribute("sucessOrDuplicate", "Email can not be empty");
                request.getRequestDispatcher("RegisterPage.jsp").forward(request, response);
                return;
            }

            int count = quiz.checkDuplicateUser(name.trim());
            //Account was existed , user must input again
            if (count > 0) {
                request.setAttribute("roleName", roleJsp);
                request.setAttribute("sucessOrDuplicate", "Account was existed in system");
                request.getRequestDispatcher("RegisterPage.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("roleName", roleJsp);
                request.setAttribute("sucess", "Add new sucessful !");
                request.getRequestDispatcher("RegisterPage.jsp").forward(request, response);
            }

            Role r = new Role();
            //get date current in my laptop
            java.util.Date uDate = new java.util.Date();
            //convert util.date to sql.date
            java.sql.Date sDate = r.convertUtilToSql(uDate);
            quiz.insertUser(name, pass, roleID, email, sDate);

            request.getRequestDispatcher("RegisterPage.jsp").forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("accountName");
        session.removeAttribute("Password");
        session.removeAttribute("email");
        try {
            QuizDAO quiz = new QuizDAO();
            List<Role> list = quiz.getAllRole();
            request.setAttribute("list", list);
            request.getRequestDispatcher("RegisterPage.jsp").forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
