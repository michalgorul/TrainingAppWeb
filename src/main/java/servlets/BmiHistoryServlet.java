package servlets;

import model.Bmi;
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

@WebServlet(name = "BmiHistoryServlet", urlPatterns = {"/BmiHistoryServlet"})
public class BmiHistoryServlet extends HttpServlet {

    /**
     * A vector of exercises
     */
    Vector<Bmi> exs;

    /**
     * An entity manager
     */
    private ExerciseDao exerciseDao;


    /**
     * This method will handle request
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        exs = exerciseDao.readHistoryBmi();
        PrintWriter out = response.getWriter();

        for(Bmi e : exs){

            out.println("<tr>");

            out.println("<td>");
            out.println(e.getHeight());
            out.println("</td>");
            out.println("<td>");
            out.println(e.getWeight());
            out.println("</td>");
            out.println("<td>");
            out.println(e.getBmi());
            out.println("</td>");

            out.println("</tr>");
        }

    }
    /**
     * This method will check if in the session the model exists
     * @param request servlet request
     */
    protected void checkIfModelAndDatabaseManagerExists(HttpServletRequest request){

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
        processRequest(request, response);
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
        processRequest(request, response);
    }
}

