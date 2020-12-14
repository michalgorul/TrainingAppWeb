package servlets;

import model.ExerciseDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ClearBmiHistoryServlet", urlPatterns = {"/ClearBmiHistoryServlet"})
public class ClearBmiHistoryServlet extends HttpServlet {
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

        exerciseDao.deleteHistoryBmi();

    }

    /**
     * This method will check if in the session the model exists
     * @param request servlet request
     */
    protected void checkIfDaoExists(HttpServletRequest request){

        if(request.getSession().getServletContext().getAttribute("database") == null){
            this.exerciseDao = new ExerciseDao();
            request.getSession().getServletContext().setAttribute("database", exerciseDao);
        }
        else{
            this.exerciseDao = (ExerciseDao) request.getSession().getServletContext().getAttribute("database");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        checkIfDaoExists(request);
        processRequest(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        checkIfDaoExists(request);
        processRequest(request, response);

    }
}