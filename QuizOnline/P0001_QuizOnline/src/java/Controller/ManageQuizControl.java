/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.QuizDAO;
import Entity.Question;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ManageQuizControl extends HttpServlet {

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
        try {
            QuizDAO DAO = new QuizDAO();
            int countQuestion = DAO.countQuestion();
            int paging = 6;
            int endPage = countQuestion / paging;
            // number of question is odd number
            if (countQuestion % paging != 0) {
                endPage++;
            }
            int index = 0;
            // check key = index passing controller or not
            if (!request.getParameterMap().containsKey("index")) {
                index = 1;
            } else {
                index = Integer.parseInt(request.getParameter("index"));
            }
            String url = request.getRequestURI();
            if (index > endPage) {
                request.setAttribute("notfound", "Not Found");
                request.getRequestDispatcher("ManageQuizPage.jsp").forward(request, response);
                return;
            }
            List<Question> list = DAO.pagingManageQuiz(paging, index);
            // user click delete
            if (null != request.getParameter("Delete")) {
                String delete = request.getParameter("Delete");
                if (!delete.trim().isEmpty()) {
                    int idQuestion = Integer.parseInt(request.getParameter("IdQuestion"));
                    DAO.DeleteQuestion(idQuestion);
//                    request.setAttribute("SucessOrFail", "Delete Sucessful");
                    response.sendRedirect("ManageQuiz");
                }
            }
            request.setAttribute("index", index);
            request.setAttribute("endpage", endPage);
            request.setAttribute("listquestion", list);
            request.setAttribute("count", countQuestion);
            request.getRequestDispatcher("ManageQuizPage.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
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
        String btn = request.getParameter("Delete");
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("HomeControl");
            return;
        }
        if (Integer.parseInt(session.getAttribute("role").toString()) == 2) {
            response.sendRedirect("TakeQuizControl");
            return;
        }
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
        String btn = request.getParameter("Delete");
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
