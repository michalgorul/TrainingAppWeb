package servlets;

import exceptions.MyException;
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
 * The ExerciseServlet handling page with adding exercises
 * @author Michal Goral
 * @version 1.0
 */
@WebServlet(name = "ExerciseServlet", urlPatterns = {"/exercise", "/exerciseNames"})
public class ExerciseServlet extends HttpServlet {

    /**
     * A model object from MVC
     */
    private Model model;

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

        String category = request.getParameter("category");
        String comment = request.getParameter("comment");
        String date = request.getParameter("date");
        String distance = request.getParameter("distance");
        String duration = request.getParameter("duration");

        Vector<String> names = model.readCategoriesFromFile("C:\\Users\\micha\\IdeaProjects\\TrainingAppWeb\\categories.txt");

        if(date == null || date.length() == 0){
            exerciseNotSaved("date", response);
        }
        else if(distance == null || distance.length() == 0){
            exerciseNotSaved("distance", response);
        }
        else if(duration == null || duration.length() == 0){
            exerciseNotSaved("duration", response);
        }
        else{

            try{

                distance = distance.replace(",",".");
                duration = duration.replace(",",".");
                Double dis = Double.parseDouble(distance);
                Double dur = Double.parseDouble(duration);

                try{
                    model.checkDoublesIfNegative(dis, dur);
                    exerciseSaved(category, comment, date, distance, duration, response);

                    /* adding exercise */
                    model.addExercise(category, comment, date, dis, dur);

                    try{
                        Exercise ex = new Exercise(category, comment, date, dis, dur);
                        exerciseDao.registerExercise(ex);
                    }
                    catch(ClassNotFoundException ex){
                        ex.printStackTrace();
                    }

                }
                catch (MyException ex){
                    exerciseNotSaved("distance and duration",response);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or wrong parameters");

                }

            }catch (NumberFormatException ex){

                exerciseNotSaved("distance and duration",response);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or wrong parameters");

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

            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception ex){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error");
        }
    }

    /**
     * This method will check if in the session the model and database manager exists
     * @param request servlet request
     */
    protected void checkIfModelAndDatabaseManagerExists(HttpServletRequest request){
        if(request.getSession().getServletContext().getAttribute("model") == null){
            this.model = new Model();
            request.getSession().getServletContext().setAttribute("model", model);
        }
        else{
            this.model = (Model) request.getSession().getServletContext().getAttribute("model");
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
