package ru.akirakozov.sd.refactoring;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class HttpResponceWriter {
    private final HttpServletResponse response;

    public HttpResponceWriter(HttpServletResponse response) {
        this.response = response;
    }

    public void WriteString(String str) throws IOException {
        response.getWriter().println(str);
    }

    public void addHtmlBody(List<String> html_strings, String header) throws IOException {
        WriteString("<html><body>");
        if (!header.isEmpty()) {
            WriteString(header);
        }
        for (String html_string: html_strings) {
            WriteString(html_string + "</br>");
        }
        WriteString("</body></html>");
    }

    public void setContentTypeAndStatus(String contentType, int status) {
        response.setContentType(contentType);
        response.setStatus(status);
    }
}
