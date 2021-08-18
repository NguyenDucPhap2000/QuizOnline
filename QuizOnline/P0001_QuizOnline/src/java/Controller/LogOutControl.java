/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
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
public class LogOutControl extends HttpServlet {

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
        //remove session when user logout
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("role");
        session.removeAttribute("list");
        session.removeAttribute("paging");
        session.removeAttribute("number");
        session.removeAttribute("timeEnd");
        session.removeAttribute("userid");
        session.removeAttribute("timeStart");
        session.removeAttribute("LISTQUESTION");
        session.removeAttribute("countTrue");
        session.removeAttribute("idQuestion");
        session.removeAttribute("SCORE");
        session.removeAttribute("CHEATING");
        session.removeAttribute("result");
        session.removeAttribute("result2");
        session.removeAttribute("PassorNot");
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
        request.getRequestDispatcher("HomePage.jsp").forward(request, response);
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
        processRequest(request, response);
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
