package servlets;

import model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

@WebServlet(name = "ExerciseServlet", urlPatterns = {"/exercise", "/exerciseNames"})
public class ExerciseServlet extends HttpServlet {

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

        String category = request.getParameter("category");
        String comment = request.getParameter("comment");
        String date = request.getParameter("date");
        String distance = request.getParameter("distance");
        String duration = request.getParameter("duration");

        Vector<String> names = model.readCategoriesFromFile("C:\\Users\\micha\\IdeaProjects\\TrainingAppWeb\\categories.txt");
        PrintWriter out = response.getWriter();

/*        for(String n: names){

            out.println("<option>" + n + "</option>");

        }*/

        if(category == null || date == null || distance == null || duration == null ||
                category.length() == 0 || date.length() == 0 || distance.length() == 0
                || duration.length() == 0){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "all parameters needed");
        }
        else{

            try{
                distance = distance.replace(",",".");
                duration = duration.replace(",",".");
                Double dis = Double.parseDouble(distance);
                Double dur = Double.parseDouble(duration);
                exerciseSaved(category, comment, date, distance, duration, response);

                /* adding exercise */
                model.addExercise(category, comment, date, dis, dur);

            }catch (NumberFormatException ex){

                exerciseNotSaved("distance and duration",response);
            }

        }
    }

    /**
     * This method will save user's exercise
     * @param category a category of exercise
     * @param comment a comment for exercise
     * @param date date of exercise
     * @param distance distance made during exercise
     * @param duration duration of exercise
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

     /** This method will handle action after user input was wrong
     * @param wrongInput what value was badly input
     * @param response servlet request
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
