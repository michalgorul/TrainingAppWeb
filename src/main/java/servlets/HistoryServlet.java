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

@WebServlet(name = "HistoryServlet", urlPatterns = {"/HistoryServlet"})
public class HistoryServlet extends HttpServlet {

    private Model model;
    Vector<Exercise> exs;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // response.setContentType("text/html;charset=UTF-8");

        exs = model.getExercises();
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
