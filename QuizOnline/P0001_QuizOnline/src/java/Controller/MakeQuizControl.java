/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.QuizDAO;
import Entity.Role;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class MakeQuizControl extends HttpServlet {

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
        request.setCharacterEncoding("utf-8");
        try {
            QuizDAO DAO = new QuizDAO();
            String q = "Question: ";
            request.setAttribute("q", q);
            HttpSession session = request.getSession();
            String question = request.getParameter("question");
            request.setAttribute("question", question);
            //Dont allow user input empty in question
            if (question.trim().length() == 0) {
                request.setAttribute("alert", "Question cannot be emtpy !");
                request.getRequestDispatcher("MakeQuizPage.jsp").forward(request, response);
                return;
            }
            try {
                String op1 = request.getParameter("op1");
                String op2 = request.getParameter("op2");
                String op3 = request.getParameter("op3");
                String op4 = request.getParameter("op4");
                request.setAttribute("op1", op1);
                request.setAttribute("op2", op2);
                request.setAttribute("op3", op3);
                request.setAttribute("op4", op4);
                //Dont allow user in order to be empty answer
                if (op1.trim().isEmpty() || op2.trim().isEmpty() || op3.trim().isEmpty() || op4.trim().isEmpty()) {
                    request.setAttribute("alert2", "Option cannot be emtpy !");
                    request.getRequestDispatcher("MakeQuizPage.jsp").forward(request, response);
                    return;
                }
                String[] cbo = request.getParameterValues("cbo");
                String str = "";
                for (int i = 0; i < cbo.length; i++) {
                    if (cbo[i] != null) {
                        str += cbo[i] + ", ";
                    }
                }
                String answerTrue = str.substring(0, str.trim().length() - 1);
                // Dont allow user checked 4 answers
                if (cbo.length == 4) {
                    request.setAttribute("alert3", "Cannot have 4 answer are true !");
                    request.getRequestDispatcher("MakeQuizPage.jsp").forward(request, response);
                    return;
                }

                //Dont allow user insert duplicate question
                int duplicate = DAO.checkDuplicateQuestion(question.trim());
                if (duplicate > 0) {
                    request.setAttribute("alert3", "Duplicate Question Input Again !");
                    request.getRequestDispatcher("MakeQuizPage.jsp").forward(request, response);
                    return;
                }

                //insert question
                Role r = new Role();
                //get date current in my laptop
                java.util.Date uDate = new java.util.Date();
                //convert util.date to sql.date
                java.sql.Date sDate = r.convertUtilToSql(uDate);
                int userid = Integer.parseInt(session.getAttribute("userid").toString());
                String username = session.getAttribute("user").toString();
                DAO.insertQuestion(question, op1, op2, op3, op4, answerTrue, userid, username, sDate);
                request.setAttribute("alert4", "Add new successful");
            } catch (Exception e) {
                request.setAttribute("alert3", "You must check at least one true answer");
                request.getRequestDispatcher("MakeQuizPage.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("MakeQuizPage.jsp").forward(request, response);
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
        String q = "Question: ";
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("HomeControl");
            return;
        }
        if (Integer.parseInt(session.getAttribute("role").toString()) == 2) {
            response.sendRedirect("TakeQuizControl");
            return;
        }
        request.setAttribute("q", q);
        request.getRequestDispatcher("MakeQuizPage.jsp").forward(request, response);
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
