package com.topica.zyot.shyn;

import java.sql.*;

public class App {
    private static final String URL = "jdbc:mysql://localhost:3306/FirstSchema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "conkuncon249";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            getAllStudents(connection);
            getAllStudentsByClass("ICT02-K61", connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    private static void getAllStudents(Connection connection) throws SQLException {
        String query = "{call GetAllStudents()}";
        CallableStatement callableStatement = connection.prepareCall(query);
        ResultSet resultSet = callableStatement.executeQuery();
        printStudentTable(resultSet);
        callableStatement.close();
    }

    private static void getAllStudentsByClass(String className, Connection connection) throws SQLException {
        String query = "{call GetAllStudentsByClass(?)}";
        CallableStatement callableStatement = connection.prepareCall(query);
        callableStatement.setString(1, className);
        ResultSet resultSet = callableStatement.executeQuery();
        printStudentTable(resultSet);
        callableStatement.close();
    }

    private static void printStudentTable(ResultSet resultSet) throws SQLException {
        System.out.println("---------------------RESULT---------------------");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstname = resultSet.getString("first_name");
            String lastname = resultSet.getString("last_name");
            String clazz = resultSet.getString("clazz");
            System.out.printf("id: %-4s \tfirstname: %-12s \tlastname: %-22s \tclass: %s\n", id, firstname, lastname, clazz);
        }
    }
}
