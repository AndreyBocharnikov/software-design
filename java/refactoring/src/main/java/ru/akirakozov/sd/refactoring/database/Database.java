package ru.akirakozov.sd.refactoring.database;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    @FunctionalInterface
    public interface ConsumerException<T> {
        void accept(T t) throws IOException, SQLException;
    }

    private final List<String> httpResult;

    public Database() {
        httpResult = new ArrayList<>();
    }

    private List<String> connectAndExecuteQuery(ConsumerException<ResultSet> processResultSet, String query) throws IOException, SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            processResultSet.accept(rs);
            return httpResult;
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
            httpResult.add(name + "\t" + price);
        }
    }

    private void getFirstInt(ResultSet rs) throws SQLException, IOException {
        if (rs.next()) {
            httpResult.add(String.valueOf(rs.getInt(1)));
        }
    }

    public List<String> getProductQuery() throws SQLException, IOException {
        return connectAndExecuteQuery(this::getNameAndPrice, "SELECT * FROM PRODUCT");
    }

    public List<String> maxQuery() throws SQLException, IOException {
        return connectAndExecuteQuery(this::getNameAndPrice, "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }

    public List<String> minQuery() throws SQLException, IOException {
        return connectAndExecuteQuery(this::getNameAndPrice, "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    public List<String> sumQuery() throws SQLException, IOException {
        return connectAndExecuteQuery(this::getFirstInt, "SELECT SUM(price) FROM PRODUCT");
    }

    public List<String> countQuery() throws SQLException, IOException {
        return connectAndExecuteQuery(this::getFirstInt, "SELECT COUNT(*) FROM PRODUCT");
    }
}
