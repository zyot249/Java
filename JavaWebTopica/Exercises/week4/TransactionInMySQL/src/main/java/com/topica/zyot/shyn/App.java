package com.topica.zyot.shyn;

import java.sql.*;

public class App {
    private static final String URL = "jdbc:mysql://localhost:3306/FirstSchema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "conkuncon249";
    private static Savepoint savepoint;

    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            testCommitAndRollback(connection);
            testSavepointAndRollback(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void testCommitAndRollback(Connection connection) {
        Statement statement = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String update1 = "UPDATE FirstSchema.subject SET note = 'noted 2' WHERE id = 'PE2020';";
            statement.executeUpdate(update1);
            String update2 = "UPDATE FirstSchema.subject set id = 'IT2121' WHERE name = 'Database Lab';";
            statement.executeUpdate(update2);
            // if no error
            connection.commit();
            System.out.println("All changes are committed!");
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("All changes are rolled back");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private static void testSavepointAndRollback(Connection connection) {
        Statement statement = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String update1 = "UPDATE FirstSchema.subject SET note = 'noted 2' WHERE id = 'PE2020';";
            statement.executeUpdate(update1);
            savepoint = connection.setSavepoint("savepoint_A");
            String update2 = "UPDATE FirstSchema.subject set id = 'IT2121' WHERE name = 'Database Lab';";
            statement.executeUpdate(update2);
            // if no error
            connection.commit();
            System.out.println("All changes are committed!");
        } catch (SQLException e) {
            try {
                connection.rollback(savepoint);
                System.out.println("Roll back to savepoint A");
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
