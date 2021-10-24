package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.Database;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        Database db = new Database(response);

        try {
            if ("max".equals(command)) {
                db.maxQuery();
            } else if ("min".equals(command)) {
                db.minQuery();
            } else if ("sum".equals(command)) {
                db.sumQuery();
            } else if ("count".equals(command)) {
                db.countQuery();
            } else {
                response.getWriter().println("Unknown command: " + command);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
