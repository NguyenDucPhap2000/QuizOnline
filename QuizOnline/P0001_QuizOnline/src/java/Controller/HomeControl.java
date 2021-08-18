/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.QuizDAO;
import Entity.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nguye
 */
public class HomeControl extends HttpServlet {

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
            String txtUser = "User Name:";
            String txtPass = "Password:";
            request.setAttribute("txtuser", txtUser);
            request.setAttribute("txtpass", txtPass);
            String user = request.getParameter("user");
            String pass = request.getParameter("pass");
            // user log out not yet 
            if (null != session.getAttribute("user")) {
                // and user login an other account , remove result account before
                if (!user.equals(session.getAttribute("user").toString())) {
                    //remove result of account before
                    session.removeAttribute("CHEATING");
                    session.removeAttribute("SCORE");
                    session.removeAttribute("result");
                    session.removeAttribute("result2");
                    session.removeAttribute("PassorNot");
                    session.removeAttribute("number");
                    session.removeAttribute("paging");
                    session.removeAttribute("timeEnd");
                    session.removeAttribute("LISTQUESTION");
                    session.removeAttribute("idQuestion");
                }
            }
            QuizDAO quiz = new QuizDAO();
            List<Account> list = quiz.getAccount();
            //loop for : find in list user and password from sql server
            for (Account a : list) {
                // account and password inputed existed in data, program move to take quiz page
                if (user.trim().equals(a.getUserName()) && pass.trim().equals(a.getPassWord())) {
                    // access tittle header
                    session.setAttribute("user", a.getUserName());
                    // make quiz
                    session.setAttribute("userid", a.getId());
                    // save user and password up to cookie
                    Cookie uC = new Cookie("user", a.getUserName());
                    Cookie pC = new Cookie("pass", a.getPassWord());
                    uC.setMaxAge(60);
                    pC.setMaxAge(60);
                    response.addCookie(uC);
                    response.addCookie(pC);
                    int roleid = quiz.getRoleByUserPass(user, pass);
                    session.setAttribute("role", roleid);
                    response.sendRedirect(request.getContextPath() + "/TakeQuizControl");
                    return;
                }
            }
            request.setAttribute("wrong", "Account or password are wrong, login again !");
            request.getRequestDispatcher("HomePage.jsp").forward(request, response);
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
        if (request.getCookies() != null) {
            Cookie arr[] = request.getCookies();
            // loop for: take user and password saved before
            for (Cookie c : arr) {
                if (c.getName().equals("user")) {
                    request.setAttribute("us", c.getValue());
                }
                if (c.getName().equals("pass")) {
                    request.setAttribute("ps", c.getValue());
                }
            }
        }
        request.getRequestDispatcher("HomePage.jsp").forward(request, response);
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
