/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.QuizDAO;
import Entity.Question;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nguye
 */
@WebServlet(name = "TakeQuizControl", urlPatterns = {"/TakeQuizControl"})
public class TakeQuizControl extends HttpServlet {

    public void formatMark(double result, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        DecimalFormat f = new DecimalFormat("#.#");
        double resultFormat = 0;
        resultFormat = Double.valueOf(f.format(result));
        if (result == 10 || result == 0) {
            request.setAttribute("PassorNot", "pass");
            session.setAttribute("result", (int) resultFormat);
            session.setAttribute("result2", (int) (resultFormat * 10));
        } else {
            session.setAttribute("result", resultFormat);
            session.setAttribute("result2", (int) (resultFormat * 10));
        }
        // set color for pass and not pass
        if (result < 5) {
            session.setAttribute("PassorNot", "notPass");
        } else {
            session.setAttribute("PassorNot", "pass");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            if (null == session.getAttribute("user")) {
                response.sendRedirect("HomeControl");
                return;
            }
            String view = "TakeQuizPage.jsp";
            String username = (String) session.getAttribute("user");
            // user logined in system
            if (!username.isEmpty()) {
                List<Question> list = (List<Question>) session.getAttribute("LISTQUESTION");
                if (list != null) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    Integer paging = (int) session.getAttribute("paging");
                    Long timeEnd = (Long) session.getAttribute("timeEnd");
                    Long timeCurrent = (Long) timestamp.getTime();

                    // when time out page of question == number of list question
                    if (timeEnd < timeCurrent || paging == list.size()) {
                        session.removeAttribute("paging");
                        session.removeAttribute("timeEnd");
                        session.removeAttribute("LISTQUESTION");
                        session.removeAttribute("idQuestion");
                        // if user cheating and submit lately more 2 second
                        if (timeCurrent - timeEnd > 2000) {
                            session.setAttribute("CHEATING", 0);
                            formatMark(0, request, response);
                            request.getRequestDispatcher(view).forward(request, response);
                            return;
                        } else {
                            double result;
                            // if time out and reload page
                            if (null == session.getAttribute("SCORE")) {
                                double Score = 0;
                                double numberofques = Double.parseDouble(session.getAttribute("number").toString());
                                Score = (Double.parseDouble(session.getAttribute("countTrue").toString()) / numberofques) * 10;
                                session.setAttribute("SCORE", Score);
                                formatMark(Score, request, response);
                                request.getRequestDispatcher(view).forward(request, response);
                                return;
                            } else { // user submited and have true question
                                session.removeAttribute("countTrue");
                                session.removeAttribute("number");
                                result = (double) session.getAttribute("SCORE");
                                //format mark display
                                formatMark(result, request, response);
                            }
                        }
                    } else if (paging != null && paging <= list.size() - 1) { // set question to jsp
                        Question question = list.get(paging);
                        request.setAttribute("QUESTION", question);
                        session.setAttribute("idQuestion", list.get(paging).getId());
                    }
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/HomeControl");
            }
            RequestDispatcher rd = request.getRequestDispatcher(view);
            rd.forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO dao = new QuizDAO();
        HttpSession session = request.getSession();
        try {
            // when user input number of question and click start
            if (request.getParameterMap().containsKey("numberQuestion")) {
                session.setAttribute("countTrue", 0);
                int number = 0;
                try {
                    //get Number Question from jsp
                    number = Integer.parseInt(request.getParameter("numberQuestion"));
                    // Check user input number of question wrong , request user input again
                    if (number > dao.countQuestion()) {
                        request.setAttribute("number", number);
                        request.setAttribute("error", "Number of question out range ! Input again");
                        request.getRequestDispatcher("TakeQuizPage.jsp").forward(request, response);
                        return;
                    } else if (number <= 0) {
                        request.setAttribute("error", "Input again number of question !");
                        request.getRequestDispatcher("TakeQuizPage.jsp").forward(request, response);
                        return;
                    }
                } catch (Exception e) {
                    request.setAttribute("error", "Input again number of question !");
                    request.getRequestDispatcher("TakeQuizPage.jsp").forward(request, response);
                    return;
                }
                Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
                Long timeStart = timeStamp.getTime();
                Long timeEnd = timeStart + (number * 10 * 1000);
                //get list of question from DAO
                List<Question> listRandomQuestion = dao.getRandomQuestion(number);
                // check list not null
                if (!listRandomQuestion.isEmpty()) {
                    session.setAttribute("timeEnd", timeEnd);
                    session.setAttribute("LISTQUESTION", listRandomQuestion);
                    session.setAttribute("paging", 0);
                    session.setAttribute("number", number);
                }
            } else if (request.getParameterMap().containsKey("btnSubmit")) { // click next button
                Integer paging = (int) session.getAttribute("paging");
                if (paging != null) {
                    paging = paging + 1;
                    session.setAttribute("paging", paging);

                    //Caculate mark
                    // Map return all of couple (key, value)
                    Map m = request.getParameterMap();
                    // one set = one couple (key,value)
                    Set s = m.entrySet();
                    // Iterator uses browse elements
                    Iterator it = s.iterator();
                    String key;
                    String[] value;
                    List<String> listanswer = new ArrayList<>();
                    // return true if next elements is exist
                    while (it.hasNext()) {
                        // return current element and pointer to next element
                        Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();
                        key = entry.getKey();
                        value = entry.getValue();
                        if (key.equals("op") && value.length > 1) {
                            for (int i = 0; i < value.length; i++) {
                                listanswer.add(value[i]);
                            }
                        }
                    }
                    double countTrue = Double.parseDouble(session.getAttribute("countTrue").toString());
                    String option = request.getParameter("op");
                    int idQuestion = (int) session.getAttribute("idQuestion");
                    List<Question> listQuestion = dao.getQuestionByID(idQuestion);
                    // Browse all of element in list question
                    for (Question q : listQuestion) {
                        if (q.getAnswers().equals(option)) {
                            session.setAttribute("countTrue", countTrue + 1);
                        } else if (listanswer.size() > 1) {
                            for (int i = 0; i < 1; i++) {
                                String str = listanswer.toString().substring(1, listanswer.toString().length() - 1);
                                if (q.getAnswers().trim().equals(str.trim())) {
                                    session.setAttribute("countTrue", countTrue + 1);
                                } else {
                                    session.setAttribute("countTrue", countTrue);
                                }
                            }
                        } else {
                            session.setAttribute("countTrue", countTrue);
                        }
                    }
                    // Caculate mark
                    List<Question> list = (List<Question>) session.getAttribute("LISTQUESTION");
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    Long timeEnd = (Long) session.getAttribute("timeEnd");
                    Long timeCurrent = System.currentTimeMillis();
                    // time out or paging in last of index
                    if (timeEnd < timeCurrent || paging == list.size()) {
                        double result = 0;
                        double numberofques = Double.parseDouble(session.getAttribute("number").toString());
                        result = (Double.parseDouble(session.getAttribute("countTrue").toString()) / numberofques) * 10;
                        session.setAttribute("SCORE", result);
                    }
                }
            } else if (request.getParameterMap().containsKey("StartResult")) {
                session.removeAttribute("CHEATING");
                session.removeAttribute("SCORE");
                session.removeAttribute("result");
                session.removeAttribute("result2");
                session.removeAttribute("PassorNot");
            }
            response.sendRedirect(request.getContextPath() + "/TakeQuizControl");
        } catch (Exception e) {
            request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
        }
    }
}
