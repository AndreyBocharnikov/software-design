package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.List;

public class ServletTest {
    private static final String DB_PATH = "jdbc:sqlite:test.db";

    @Mock
    protected HttpServletRequest request;

    @Mock
    protected HttpServletResponse response;

    protected static void updateDB(String statement) {
        try (Connection c = DriverManager.getConnection(DB_PATH)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(statement);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @BeforeAll
    public static void setupDB() {
        updateDB("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    @AfterAll
    public static void dropTable() {
        updateDB("DROP TABLE IF EXISTS PRODUCT");
    }

    @BeforeEach
    public void clearDB() {
        updateDB("DELETE FROM PRODUCT");
    }

    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }



}
