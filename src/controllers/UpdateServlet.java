package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Message;
import models.validators.MessageValidator;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if (_token != null || _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            //Get message ID from the session scope then get only pointed one from the database
            Message m = em.find(Message.class, (Integer)(request.getSession().getAttribute("message_id")));

            //Overwrite each field by content of the form.

            String title = request.getParameter("title");
            m.setTitle(title);

            String content = request.getParameter("content");
            m.setContent(content);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            m.setUpdated_at(currentTime);

            // If error(s) detected when run validation then return edit form
            List<String> errors = MessageValidator.validate(m);
            if (errors.size() > 0) {
                em.close();

                //Set default value at the form, and send error message
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("message", m);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/edit.jsp");
                rd.forward(request, response);
            }else {
                // Update database
                em.getTransaction().begin();
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "Update successful");
                em.close();

                // Delete data that no longer needed on the session scope
                request.getSession().removeAttribute("message_id");

                //Redirect to index page
                response.sendRedirect(request.getContextPath() + "/index");
            }
        }

    }

}
