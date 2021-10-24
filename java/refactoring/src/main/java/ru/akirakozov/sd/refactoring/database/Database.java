package ru.akirakozov.sd.refactoring.database;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class Database {
    @FunctionalInterface
    public interface ConsumerException<T> {
        void accept(T t) throws IOException, SQLException;
    }

    private HttpServletResponse response;

    public Database(HttpServletResponse response) {
        this.response = response;
    }

    private void connectAndExecuteQuery(ConsumerException<ResultSet> processResultSet, String query) throws IOException, SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            processResultSet.accept(rs);
        }
    }

    public void connectAndUpdate(String query) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
             Statement stmt = c.createStatement()) {
             stmt.executeUpdate(query);
        }
    }

    private void getNameAndPrice(ResultSet rs) throws SQLException, IOException {
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            response.getWriter().println(name + "\t" + price + "</br>");
        }
    }

    private void getFirstInt(ResultSet rs) throws SQLException, IOException {
        if (rs.next()) {
            response.getWriter().println(rs.getInt(1));
        }
    }

    public void getProductQuery() throws SQLException, IOException {
        response.getWriter().println("<html><body>");
        connectAndExecuteQuery(this::getNameAndPrice, "SELECT * FROM PRODUCT");
        response.getWriter().println("</body></html>");
    }

    public void maxQuery() throws SQLException, IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Product with max price: </h1>");
        connectAndExecuteQuery(this::getNameAndPrice, "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
        response.getWriter().println("</body></html>");
    }

    public void minQuery() throws SQLException, IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Product with min price: </h1>");
        connectAndExecuteQuery(this::getNameAndPrice, "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
        response.getWriter().println("</body></html>");
    }

    public void sumQuery() throws SQLException, IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("Summary price: ");
        connectAndExecuteQuery(this::getFirstInt, "SELECT SUM(price) FROM PRODUCT");
        response.getWriter().println("</body></html>");
    }

    public void countQuery() throws SQLException, IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("Number of products: ");
        connectAndExecuteQuery(this::getFirstInt, "SELECT COUNT(*) FROM PRODUCT");
        response.getWriter().println("</body></html>");
    }
}
