package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class QueryServletTest  extends ru.akirakozov.sd.refactoring.servlet.ServletTest {
    public void doGetTest(String opearation, String bodyOutput) {
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter PrintWriter = new PrintWriter(stringWriter);
            Mockito.when(response.getWriter()).thenReturn(PrintWriter);

            Mockito.when(request.getParameter("command")).thenReturn(opearation);
            try {
                new QueryServlet().doGet(request, response);
            } catch (IOException e) {
                Assertions.fail("Exception: ", e);
            }

            Assertions.assertTrue(stringWriter.toString().contains("<html><body>"));
            Assertions.assertTrue(stringWriter.toString().contains(bodyOutput));
            Assertions.assertTrue(stringWriter.toString().contains("</body></html>"));

            Mockito.verify(response).setContentType("text/html");
            Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    public void doGetTestMin() {
        doGetTest("min", "<h1>Product with min price: </h1>");
    }

    @Test
    public void doGetTestMax() {
        doGetTest("max", "<h1>Product with max price: </h1>");
    }

    @Test
    public void doGetTestSum() {
        doGetTest("sum", "Summary price: ");
    }

    @Test
    public void doGetTestCount() {
        doGetTest("count", "Number of products: ");
    }
}
