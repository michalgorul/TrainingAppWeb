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


    private Model model;
    private Double height;
    private Double weight;

    @Override
    public void init(){

        model = new Model();
    }

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
                height = Double.parseDouble(arg1);
                weight = Double.parseDouble(arg2);
                calculated(height, weight, response);

            }catch (NumberFormatException ex){

                notCalculated("height and weight", response);
            }
        }


    }

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

            out.println("<p> height: " + Double.toString(height) + "</p>");
            out.println("<p> weight: " + Double.toString(weight) + "</p>");
            out.println("<p> BMI: " + Double.toString(model.calculateBmi(height, weight)) + "</p>");


            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception ex){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Numbers are needed");
        }

    }

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
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        processRequest(request, response);

    }
}
