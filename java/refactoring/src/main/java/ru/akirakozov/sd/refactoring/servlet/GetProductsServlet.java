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
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpResponceWriter httpResponceWriter = new HttpResponceWriter(response);
        try {
            Database db = new Database();
            List<String> httpResult = db.getProductQuery();
            httpResponceWriter.addHtmlBody(httpResult, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpResponceWriter.setContentTypeAndStatus("text/html", HttpServletResponse.SC_OK);
    }
}
