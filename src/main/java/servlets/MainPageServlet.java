package servlets;

import model.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MainPageServlet", urlPatterns = {"/mainPage"})
public class MainPageServlet extends HttpServlet {

    private final Model model = new Model();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");


        try{
           request.setAttribute("model", model);
           request.getSession().setAttribute("model", model);
           this.getServletConfig().getServletContext().setAttribute("model", model);
        }
        catch(Exception ex){

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error");
        }



    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);

    }
}
