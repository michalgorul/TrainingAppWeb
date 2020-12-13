package servlets;

import model.Exercise;
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
 * The StatisticsServlet handling page with statistics
 * @author Michal Goral
 * @version 1.0
 */
@WebServlet(name = "StatisticsServlet", urlPatterns = {"/StatsServlet"})
public class StatisticsServlet extends HttpServlet {

    /**
     * A model object from MVC
     */
    private Model model;

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

        PrintWriter out = response.getWriter();
        Vector<String> names = model.readCategoriesFromFile("C:\\Users\\micha\\IdeaProjects\\TrainingAppWeb\\categories.txt");

        for(String n: names){

            out.println("<tr>");
            out.println("<td>");
            out.println(n);
            out.println("</td>");
            out.println("<td>");
            out.println(model.getSumDistanceForEach(n));
            out.println("</td>");
            out.println("<td>");
            out.println(model.getSumDurationForEach(n));
            out.println("</td>");
            out.println("</tr>");
        }
    }

    /**
     * This method will check if in the session the model exists
     * @param request servlet request
     */
    protected void checkIfModelExists(HttpServletRequest request){
        if((Model) request.getSession().getServletContext().getAttribute("model") == null){
            this.model = new Model();
            request.getSession().getServletContext().setAttribute("model", model);
        }
        else{
            this.model = (Model) request.getSession().getServletContext().getAttribute("model");
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

        checkIfModelExists(request);
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

        checkIfModelExists(request);
        processRequest(request, response);
    }
}
