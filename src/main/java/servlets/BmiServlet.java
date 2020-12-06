package servlets;

import model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "BmiServlet", urlPatterns = {"/bmi"})
public class BmiServlet extends HttpServlet {

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

        String arg1 = request.getParameter("arg1");
        String arg2 = request.getParameter("arg2");

        if(arg1 == null || arg2 == null || arg1.length() == 0 || arg2.length() == 0){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Two parameters needed");
        }
        else{

            try{
                arg1 = arg1.replace(",",".");
                arg2 = arg2.replace(",",".");
                Double height = Double.parseDouble(arg1);
                Double weight = Double.parseDouble(arg2);
                calculated(height, weight, response);

            }catch (NumberFormatException ex){

                notCalculated("height and weight", response);
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

            out.println("<p class=\"b\"> height: " + Double.toString(height) + "</p>");
            out.println("<p class=\"b\"> weight: " + Double.toString(weight) + "</p>");
            out.println("<p class=\"b\"> BMI: " + Double.toString(model.calculateBmi(height, weight)) + "</p>");


            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception ex){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Numbers are needed");
        }
    }

    /**
     * This method will handle action after user input was wrong
     * @param wrongInput what value was badly input
     * @param response servlet request
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void notCalculated(String wrongInput, HttpServletResponse response)
            throws ServletException, IOException{

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
