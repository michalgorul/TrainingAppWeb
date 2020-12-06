package servlets;

import model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ExerciseServlet", urlPatterns = {"/exercise"})
public class ExerciseServlet extends HttpServlet {

    private Model model;
    private Double dis;
    private Double dur;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String category = request.getParameter("category");
        String comment = request.getParameter("comment");
        String date = request.getParameter("date");
        String distance = request.getParameter("distance");
        String duration = request.getParameter("duration");

        if(category == null || date == null || distance == null || duration == null ||
                category.length() == 0 || date.length() == 0 || distance.length() == 0
                || duration.length() == 0){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "all parameters needed");
        }
        else{

            try{
                distance = distance.replace(",",".");
                duration = duration.replace(",",".");
                dis = Double.parseDouble(distance);
                dur = Double.parseDouble(duration);
                exerciseSaved(category, comment, date, distance, duration, response);

                /* adding exercise */
                model.addExercise(category, comment, date, dis, dur);

            }catch (NumberFormatException ex){

                exerciseNotSaved("distance and duration",response);
            }

        }
    }

    protected void exerciseSaved(String category, String comment, String date,
                                    String distance, String duration, HttpServletResponse response)
                                    throws ServletException, IOException{

        try{
            /* TODO output your page here. You may use following sample code. */
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Exercise</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Exercise saved!</h2>");

            out.println("<p class=\"b\"> category: " + category + "</p>");
            out.println("<p class=\"b\"> comment: " + comment + "</p>");
            out.println("<p class=\"b\"> date: " + date + "</p>");
            out.println("<p class=\"b\"> distance: " + distance + "</p>");
            out.println("<p class=\"b\"> duration: " + duration + "</p>");

            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception ex){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Numbers are needed");
        }
    }

    protected void exerciseNotSaved(String wrongInput, HttpServletResponse response)
            throws ServletException, IOException{

        try{
            /* TODO output your page here. You may use following sample code. */
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Exercise</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Exercise not saved!</h2>");

            out.println("<p> Please enter correct " + wrongInput + "</p>");

            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception ex){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Numbers are needed");
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
