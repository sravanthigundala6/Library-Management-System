package com.xadmin.usermanagement.dao;

import com.xadmin.usermanagement.bean.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/usersdb";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Gsnp123@";

    private static final String INSERT_USERS_SQL = "INSERT INTO users (name, email, country, bookname, issuedDate, dueDate, fine) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT id, name, email, country, bookname, issuedDate, dueDate, fine FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT id, name, email, country, bookname, issuedDate, dueDate, fine FROM users";
    private static final String DELETE_USERS_SQL = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_USERS_SQL = "UPDATE users SET name = ?, email = ?, country = ?, bookname = ?, issuedDate = ?, dueDate = ?, fine = ? WHERE id = ?";
    private static final String UPDATE_FINE_SQL = "UPDATE users SET fine = ? WHERE id = ?";

    public UserDAO() {
    }

    protected Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertUser(User user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            preparedStatement.setString(4, user.getBookname());
            preparedStatement.setDate(5, user.getIssuedDate());
            preparedStatement.setDate(6, user.getDueDate());
            preparedStatement.setInt(7, user.getFine());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public User selectUser(int id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String bookname = rs.getString("bookname");
                Date issuedDate = rs.getDate("issuedDate");
                Date dueDate = rs.getDate("dueDate");
                int fine = rs.getInt("fine");
                user = new User(id, name, email, country, bookname, issuedDate, dueDate, fine);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String bookname = rs.getString("bookname");
                Date issuedDate = rs.getDate("issuedDate");
                Date dueDate = rs.getDate("dueDate");
                int fine = rs.getInt("fine");
                users.add(new User(id, name, email, country, bookname, issuedDate, dueDate, fine));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(int id) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL)) {
            statement.setInt(1, id);

            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }

    public boolean updateUser(User user) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setString(4, user.getBookname());
            statement.setDate(5, user.getIssuedDate());
            statement.setDate(6, user.getDueDate());
            statement.setInt(7, user.getFine());
            statement.setInt(8, user.getId());

            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

//    public void applyPenaltyIfDueDateCrossed() {
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OVERDUE_USERS)) {
//            
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                String email = rs.getString("email");
//                String country = rs.getString("country");
//                String bookname = rs.getString("bookname");
//                Date issuedDate = rs.getDate("issuedDate");
//                Date dueDate = rs.getDate("dueDate");
//                int fine = calculateFine(dueDate.toLocalDate());
//                updateFine(id, fine);
//            }
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//    }
//
//    private int calculateFine(LocalDate dueDate) {
//        LocalDate today = LocalDate.now();
//        long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);
//        return daysOverdue > 0 ? (int) daysOverdue * 10 : 0;
//    }
//
//    private void updateFine(int userId, int newFine) {
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement("UPDATE users SET fine = ? WHERE id = ?")) {
//            statement.setInt(1, newFine);
//            statement.setInt(2, userId);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//    }
    public void updateFine() {
        List<User> users = selectAllUsers();
        LocalDate currentDate = LocalDate.now();
        for (User user : users) {
            LocalDate dueDate = user.getDueDate().toLocalDate();
            if (currentDate.isAfter(dueDate)) {
                long daysOverdue = ChronoUnit.DAYS.between(dueDate, currentDate);
                int newFine = user.getFine() + (int) daysOverdue * 10;
                
                System.out.println("Updating fine for user: " + user.getId() + " New fine: " + newFine);

                try (Connection connection = getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FINE_SQL)) {
                    preparedStatement.setInt(1, newFine);
                    preparedStatement.setInt(2, user.getId());
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Fine updated successfully for user: " + user.getId());
                    } else {
                        System.out.println("Failed to update fine for user: " + user.getId());
                    }
                } catch (SQLException e) {
                    printSQLException(e);
                } 
    		} else {
                System.out.println("User " + user.getId() + " is not overdue. No fine update needed.");
            }
    	}
    	
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}

