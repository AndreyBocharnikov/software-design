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
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        HttpResponceWriter httpResponceWriter = new HttpResponceWriter(response);
        try {
            String sql = "INSERT INTO PRODUCT " +
                        "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
            Database db = new Database();
            db.connectAndUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        httpResponceWriter.setContentTypeAndStatus("text/html", HttpServletResponse.SC_OK);
        httpResponceWriter.WriteString("OK");
    }
}
