package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.HttpResponceWriter;
import ru.akirakozov.sd.refactoring.database.Database;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        Database db = new Database();
        HttpResponceWriter httpResponceWriter = new HttpResponceWriter(response);
        List<String> httpResult;

        try {
            if ("max".equals(command)) {
                httpResult = db.maxQuery();
                httpResponceWriter.addHtmlBody(httpResult, "<h1>Product with max price: </h1>");
            } else if ("min".equals(command)) {
                httpResult = db.minQuery();
                httpResponceWriter.addHtmlBody(httpResult, "<h1>Product with min price: </h1>");
            } else if ("sum".equals(command)) {
                httpResult = db.sumQuery();
                httpResponceWriter.addHtmlBody(httpResult, "Summary price: ");
            } else if ("count".equals(command)) {
                httpResult = db.countQuery();
                httpResponceWriter.addHtmlBody(httpResult, "Number of products: ");
            } else {
                response.getWriter().println("Unknown command: " + command);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpResponceWriter.setContentTypeAndStatus("text/html", HttpServletResponse.SC_OK);
    }
}
