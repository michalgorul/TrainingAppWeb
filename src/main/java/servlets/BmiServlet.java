package servlets;

import exceptions.MyException;
import model.ExerciseDao;
import model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The BmiServlet handling page with calculating BMI
 * @author Michal Goral
 * @version 1.0
 */
@WebServlet(name = "BmiServlet", urlPatterns = {"/bmi", "/BmiServlet"})
public class BmiServlet extends HttpServlet {

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

        PrintWriter out = response.getWriter();

        String arg1 = request.getParameter("arg1");
        String arg2 = request.getParameter("arg2");

            Cookie[] cookies = request.getCookies();

            out.println("<h3> Thanks to cookies we know that: </h3>");

            for(Cookie c : cookies){

                if(c.getName().equals("height")){
                    out.println("<h4> Your last height was: " + c.getValue() + "</h4>");
                }
                if(c.getName().equals("weight")){
                    out.println("<h4>Your last weight was: " + c.getValue() + "</h4>");
                }
            }


        if(arg1 == null || arg1.length() == 0){
            notCalculated(response);

        }
        else if(arg2 == null  || arg2.length() == 0){
            notCalculated(response);
        }
        else{

            try{
                arg1 = arg1.replace(",",".");
                arg2 = arg2.replace(",",".");
                Double height = Double.parseDouble(arg1);
                Double weight = Double.parseDouble(arg2);

                try{
                    model.setHeightAndWeight(arg1,arg2);
                    model.checkHeightAndWeight();
                    /**
                     * A cookie for users height
                     */
                    Cookie cookieHeight = new Cookie("height", height.toString());
                    /**
                     * A cookie for users weight
                     */
                    Cookie cookieWeight = new Cookie("weight", weight.toString());
                    calculated(height, weight, response);

                    exerciseDao.registerBmi(height,weight, model.calculateBmi(height, weight));

                    response.addCookie(cookieHeight);
                    response.addCookie(cookieWeight);
                }
                catch (MyException ignored){
                    notCalculated(response);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or wrong parameters");
                }


            }catch (NumberFormatException ex){

                notCalculated(response);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or wrong parameters");

            }
        }
    }

    /**
     * This method will handle action after user input was accepted
     * @param height users height
     * @param weight users weight
     * @param response servlet request
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void calculated(Double height, Double weight, HttpServletResponse response)
            throws ServletException, IOException{

        try{

            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Calculate BMI</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Your result:</h2>");

            out.println("<p class=\"b\"> height: " + height + "</p>");
            out.println("<p class=\"b\"> weight: " + weight + "</p>");
            out.println("<h3 class=\"b\"> BMI: " + model.calculateBmi(height, weight) + "</h3>");


            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception ex){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error");
        }
    }

    /**
     * This method will handle action after user input was wrong
     * @param response servlet request
     * @throws IOException if an I/O error occurs
     */
    protected void notCalculated(HttpServletResponse response)
            throws IOException{

        try{
            /* TODO output your page here. You may use following sample code. */
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Calculate BMI</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>BMI not calculated!</h2>");
            out.println("</body>");
            out.println("</html>");

        }
        catch(Exception ex){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error");
        }
    }

    /**
     * This method will check if in the session the model exists
     * @param request servlet request
     */
    protected void checkIfModelExists(HttpServletRequest request){
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
