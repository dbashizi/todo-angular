package com.tiy.web;

import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;

public class ToDoDatabase {
    public final static String DB_URL = "jdbc:h2:./main";

    public void init() throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS todos (id IDENTITY, text VARCHAR, is_done BOOLEAN, user_id INT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, username VARCHAR, fullname VARCHAR)");
    }

    public int insertUser(Connection conn, String username, String fullname) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, fullname);
        stmt.execute();

        stmt = conn.prepareStatement("SELECT * FROM users where username = ?");
        stmt.setString(1, username);
        ResultSet results = stmt.executeQuery();
        results.next();
        return results.getInt("id");
    }

    public void deleteUser(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM users where username = ?");
        stmt.setString(1, username);
        stmt.execute();
    }

    public void insertToDo(Connection conn, String text, int userId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO todos VALUES (NULL, ?, false, ?)");
        stmt.setString(1, text);
        stmt.setInt(2, userId);
        stmt.execute();
    }

    public void deleteToDo(Connection conn, String text) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM todos where text = ?");
        stmt.setString(1, text);
        stmt.execute();
    }

    public ToDoItem retrieveToDo(Connection conn, String text) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos WHERE text = ?");
        stmt.setString(1, text);
        ResultSet results = stmt.executeQuery();
        results.next();

        int id = results.getInt("id");
        String retrievedText = results.getString("text");
        boolean isDone = results.getBoolean("is_done");
        ToDoItem retrievedToDo = new ToDoItem(id, retrievedText, isDone);

        return retrievedToDo;
    }

    public User findUser(Connection conn, String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
        stmt.setString(1, username);
        ResultSet results = stmt.executeQuery();

        if (results.next()) {
            int userId = results.getInt("id");
            String email = results.getString("username");
            String fullName = results.getString("fullname");

            return new User(email, fullName, null, null, userId);
        }

        return null;
    }

    public ArrayList<ToDoItem> selectToDos(Connection conn) throws SQLException {
        ArrayList<ToDoItem> items = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos");
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            String text = results.getString("text");
            boolean isDone = results.getBoolean("is_done");
            items.add(new ToDoItem(id, text, isDone));
            // get user id from todo item
            // use user id to get user information from the database
            // set user object on todo item
        }
        return items;
    }

    public ArrayList<ToDoItem> selectToDosForUser(Connection conn, int userID) throws SQLException {
        ArrayList<ToDoItem> items = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos " +
                "INNER JOIN users ON todos.user_id = users.id " +
                "WHERE users.id = ?");
        stmt.setInt(1, userID);
        ResultSet results = stmt.executeQuery();

        while (results.next()) {
            int id = results.getInt("id");
            String text = results.getString("text");
            boolean isDone = results.getBoolean("is_done");
            items.add(new ToDoItem(id, text, isDone));
        }
        return items;
    }

    public void toggleToDo(Connection conn, String text) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE todos SET is_done = NOT is_done WHERE text = ?");
        stmt.setString(1, text);
        stmt.execute();
    }

}
