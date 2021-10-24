import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.HttpResponceWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class HttpResponceWriterTest {
    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    HttpServletResponse response;

    @Test
    public void addHtmlBodyTest() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        HttpResponceWriter httpResponceWriter = new HttpResponceWriter(response);
        httpResponceWriter.addHtmlBody(List.of("cock" + "\t" + "300$", "latex gloves" + "\t" + "300$"), "");

        Assertions.assertEquals(stringWriter.toString(),
                   """
                         <html><body>
                         cock	300$</br>
                         latex gloves	300$</br>
                         </body></html>
                         """);
    }

    @Test
    public void addHtmlBodyWithHeaderTest() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        HttpResponceWriter httpResponceWriter = new HttpResponceWriter(response);
        httpResponceWriter.addHtmlBody(List.of("cock" + "\t" + "300$", "latex gloves" + "\t" + "300$"), "<h1>Product with max price: </h1>");

        Assertions.assertEquals(stringWriter.toString(),
                  """
                        <html><body>
                        <h1>Product with max price: </h1>
                        cock	300$</br>
                        latex gloves	300$</br>
                        </body></html>
                        """);
    }
}
