package com.topica.zyot.shyn;


import java.sql.*;

public class App {
    private static final String URL = "jdbc:mysql://localhost:3306/DemoShop";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "conkuncon249";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            saveOrder("ORDER001", 1, 500, connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void saveOrder(String orderCode, int prodId, int quantity, Connection connection) {
        Savepoint savepoint;
        try {
            connection.setAutoCommit(false);
            // insert order to order of customer
            insertOrderCus(orderCode, prodId, quantity, connection);
            // savepoint
            savepoint = connection.setSavepoint("save_to_order_cus");
            // insert order to order
            insertOrder(orderCode, prodId, quantity, connection);
            // get quantity in stock
            int quanInStock = getQuantityInStock(prodId, connection);
            if (quanInStock < 0) {
                System.out.println("Cannot get quantity of product in stock!");
                return;
            }
            // check if quantity <= quan_in_stock
            if (quantity <= quanInStock) {
                // if quantity in stock is enough --> order success and update inventory
                // update quan_in_stock
                updateQuanInStock(prodId, quantity, connection);
                // update sales
                updateSales(prodId, quantity, connection);
                // commit transaction
                connection.commit();
                System.out.println("Customer orders successfully!");
            } else {
                // if quantity in stock is not enough --> order fail --> roll back and update success of order of customer
                connection.rollback(savepoint);
                setOrderCusFail(orderCode, connection);
                // commit
                connection.commit();
                System.out.println("Customer orders failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // if there is any error --> roll back all
                connection.rollback();
                System.out.println("All changes are rolled back since some errors!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void insertOrderCus(String orderCode, int prodId, int quantity, Connection connection) throws SQLException {
        String insertOrderCus = "INSERT INTO DemoShop.order_from_customer(id, prod_id, quantity, success) VALUES (?, ?, ?, true);";
        PreparedStatement statement = connection.prepareStatement(insertOrderCus);
        statement.setString(1, orderCode);
        statement.setInt(2, prodId);
        statement.setInt(3, quantity);
        statement.executeUpdate();
        statement.close();
    }

    private static void insertOrder(String orderCode, int prodId, int quantity, Connection connection) throws SQLException {
        String insertOrder = "INSERT INTO DemoShop.order(id ,prod_id, quantity) VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(insertOrder);
        statement.setString(1, orderCode);
        statement.setInt(2, prodId);
        statement.setInt(3, quantity);
        statement.executeUpdate();
        statement.close();
    }

    private static int getQuantityInStock(int prodId, Connection connection) throws SQLException {
        String getQuanInStock = "SELECT quan_in_stock FROM DemoShop.inventory WHERE prod_id = ?";
        PreparedStatement statement = connection.prepareStatement(getQuanInStock);
        statement.setInt(1, prodId);
        ResultSet resultSet = statement.executeQuery();
        int quanInStock = -1;
        if (resultSet.next())
            quanInStock = resultSet.getInt("quan_in_stock");
        statement.close();
        return quanInStock;
    }

    private static void updateQuanInStock(int prodId, int quantity, Connection connection) throws SQLException {
        String updateQuanInStock = "UPDATE DemoShop.inventory SET quan_in_stock = quan_in_stock - ? WHERE prod_id = ?";
        PreparedStatement statement = connection.prepareStatement(updateQuanInStock);
        statement.setInt(1, quantity);
        statement.setInt(2, prodId);
        statement.executeUpdate();
        statement.close();
    }

    private static void updateSales(int prodId, int quantity, Connection connection) throws SQLException {
        String updateSales = "UPDATE DemoShop.inventory SET sales = sales + ? WHERE prod_id = ?";
        PreparedStatement statement = connection.prepareStatement(updateSales);
        statement.setInt(1, quantity);
        statement.setInt(2, prodId);
        statement.executeUpdate();
        statement.close();
    }

    private static void setOrderCusFail(String orderCode, Connection connection) throws SQLException {
        String updateOrderFail = "UPDATE DemoShop.order_from_customer SET success = false WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(updateOrderFail);
        statement.setString(1, orderCode);
        statement.executeUpdate();
        statement.close();
    }
}
