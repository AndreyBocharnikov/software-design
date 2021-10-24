package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class AddProductServletTest extends ru.akirakozov.sd.refactoring.servlet.ServletTest {

    @Test
    public void doGetTest() {
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            Mockito.when(response.getWriter()).thenReturn(printWriter);

            Mockito.when(request.getParameter("name")).thenReturn("kek");
            Mockito.when(request.getParameter("price")).thenReturn("228");
            try {
                new AddProductServlet().doGet(request, response);
            } catch (IOException e) {
                Assertions.fail("Exception: ", e);
            }

            Assertions.assertTrue(stringWriter.toString().contains("OK"));

            Mockito.verify(response).setContentType("text/html");
            Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

}
