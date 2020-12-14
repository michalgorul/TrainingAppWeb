package servlets;

import model.Exercise;
import model.ExerciseDao;
import model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * The HistoryServlet handling page with exercises history
 * @author Michal Goral
 * @version 1.0
 */
@WebServlet(name = "HistoryServlet", urlPatterns = {"/HistoryServlet"})
public class HistoryServlet extends HttpServlet {

    /**
     * A vector of exercises
     */
    Vector<Exercise> exs;

    /**
     * An entity manager
     */
    private ExerciseDao exerciseDao;


    /**
     * This method will handle request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        exs = exerciseDao.readHistory();
        PrintWriter out = response.getWriter();

        for(Exercise e : exs){

            out.println("<tr>");

            out.println("<td>");
            out.println(e.getExerciseName());
            out.println("</td>");
            out.println("<td>");
            out.println(e.getComment());
            out.println("</td>");
            out.println("<td>");
            out.println(e.getExerciseDate());
            out.println("</td>");
            out.println("<td>");
            out.println(e.getDistance());
            out.println("</td>");
            out.println("<td>");
            out.println(e.getDuration());
            out.println("</td>");

            out.println("</tr>");
       }

    }

    /**
     * This method will check if in the session the model exists
     * @param request servlet request
     */
    protected void checkIfModelAndDatabaseManagerExists(HttpServletRequest request){

        Model model;
        if(request.getSession().getServletContext().getAttribute("model") == null){
            model = new Model();
            request.getSession().getServletContext().setAttribute("model", model);
        }
        else{
            model = (Model) request.getSession().getServletContext().getAttribute("model");
        }

        if(request.getSession().getServletContext().getAttribute("database") == null){
            this.exerciseDao = new ExerciseDao();
            request.getSession().getServletContext().setAttribute("database", exerciseDao);
        }
        else{
            this.exerciseDao = (ExerciseDao) request.getSession().getServletContext().getAttribute("database");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        checkIfModelAndDatabaseManagerExists(request);
        processRequest(response);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        checkIfModelAndDatabaseManagerExists(request);
        processRequest(response);
    }
}
