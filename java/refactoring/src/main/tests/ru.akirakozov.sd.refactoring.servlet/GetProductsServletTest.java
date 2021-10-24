package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class GetProductsServletTest extends ru.akirakozov.sd.refactoring.servlet.ServletTest  {
    @Test
    public void doGetTest() {
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            Mockito.when(response.getWriter()).thenReturn(printWriter);

            try {
                new GetProductsServlet().doGet(request, response);
            } catch (IOException e) {
                Assertions.fail("Exception: ", e);
            }

            Assertions.assertTrue(stringWriter.toString().contains("<html><body>"));
            Assertions.assertTrue(stringWriter.toString().contains("</body></html>"));

            Mockito.verify(response).setContentType("text/html");
            Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

}
