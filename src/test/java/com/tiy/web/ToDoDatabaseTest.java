package com.tiy.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static com.tiy.web.TodoJSONController.todoDatabase;

/**
 * Created by dbashizi on 1/3/17.
 */
public class ToDoDatabaseTest {


    @Before
    public void setUp() throws Exception {
        if (todoDatabase == null) {
            // try to connect to the database (in case we're running the web app
            // unit tests at the same time, in which case the container initializes
            // the database and we don't need to
            // if we can't connect, then let's initialize the database here
            try {
                System.out.println("@@@@@@@@@@@@@ checking if the database is already running");
                Connection conn = DriverManager.getConnection("jdbc:h2:./main");
                PreparedStatement todoQuery = conn.prepareStatement("SELECT * FROM todos");
            } catch (Exception exception) {
                System.out.println("********** Initializing the database");
                todoDatabase = new ToDoDatabase();
                todoDatabase.init();
                System.out.println("&&&&&&&&&&& Database Initialized!!!!!");
            }
        } else {
            System.out.println("######### Database already initialized");
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInit() throws Exception {

        // test to make sure we can access the new database
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        PreparedStatement todoQuery = conn.prepareStatement("SELECT * FROM todos");
        ResultSet results = todoQuery.executeQuery();
        assertNotNull(results);
    }

    @Test
    public void testInsertToDo() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        String todoText = "UnitTest-ToDo";

        String userName = "d@b.com";
        String fullName = "Dominique Bashizi";
        int userId = todoDatabase.insertUser(conn, userName, fullName);

        todoDatabase.insertToDo(conn, todoText, userId);
        // make sure we can retrieve the todo we just created
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos where text = ?");
        stmt.setString(1, todoText);
        ResultSet results = stmt.executeQuery();
        assertNotNull(results);
        // count the records in results to make sure we get what we expected
        int numResults = 0;
        while (results.next()) {
            numResults++;
        }
        assertEquals(1, numResults);

        ToDoItem retrievedItem = todoDatabase.retrieveToDo(conn, todoText);
        assertNotNull(retrievedItem);
        assertEquals(todoText, retrievedItem.getText());

        todoDatabase.deleteToDo(conn, todoText);
        todoDatabase.deleteUser(conn, userName);

        // make sure there are no more records for our test todo
        results = stmt.executeQuery();
        numResults = 0;
        while (results.next()) {
            numResults++;
        }
        assertEquals(0, numResults);
    }

    @Test
    public void testInsertUser() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        String userName = "d@b.com";
        String fullName = "Dominique Bashizi";
        int newUserId = todoDatabase.insertUser(conn, userName, fullName);
        // make sure we can retrieve the user we just created
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users where username = ?");
        stmt.setString(1, userName);
        ResultSet results = stmt.executeQuery();
        assertNotNull(results);
        // count the records in results to make sure we get what we expected
        int numResults = 0;
        int retrievedUserId = -1;
        while (results.next()) {
            numResults++;
            retrievedUserId = results.getInt("id");
        }
        assertEquals(1, numResults);
        assertEquals(newUserId, retrievedUserId);

        todoDatabase.deleteUser(conn, userName);

        // make sure there are no more records for our test user
        results = stmt.executeQuery();
        numResults = 0;
        while (results.next()) {
            numResults++;
        }
        assertEquals(0, numResults);
    }

    @Test
    public void testToggleToDo() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        String todoText = "UnitTest-ToDo";

        String username = "unittester@tiy.com";
        String fullName = "Unit Tester";
        int userID = todoDatabase.insertUser(conn, username, fullName);

        todoDatabase.insertToDo(conn, todoText, userID);

        // test the toggle method
        // first, toggle from the default "false" value to true
        todoDatabase.toggleToDo(conn, todoText);
        ToDoItem retrievedItem = todoDatabase.retrieveToDo(conn, todoText);
        assertEquals(true, retrievedItem.isDone());
        // then, toggle back to false
        todoDatabase.toggleToDo(conn, todoText);
        retrievedItem = todoDatabase.retrieveToDo(conn, todoText);
        assertEquals(false, retrievedItem.isDone());

        todoDatabase.deleteToDo(conn, todoText);
        todoDatabase.deleteUser(conn, username);
    }

    @Test
    public void testSelectAllToDos() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        String firstToDoText = "UnitTest-ToDo1";
        String secondToDoText = "UnitTest-ToDo2";

        String username = "unittester@tiy.com";
        String fullName = "Unit Tester";
        int userID = todoDatabase.insertUser(conn, username, fullName);

        todoDatabase.insertToDo(conn, firstToDoText, userID);
        todoDatabase.insertToDo(conn, secondToDoText, userID);

        ArrayList<ToDoItem> todos = todoDatabase.selectToDos(conn);
        System.out.println("Found " + todos.size() + " todos in the database");

        assertTrue("There should be at least 2 todos in the database (there are " +
                todos.size() + ")", todos.size() > 1);

        todoDatabase.deleteToDo(conn, firstToDoText);
        todoDatabase.deleteToDo(conn, secondToDoText);
        todoDatabase.deleteUser(conn, username);
    }

    @Test
    public void testInsertToDoForUser() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        String todoText = "UnitTest-ToDo";
        String todoText2 = "UnitTest-ToDo2";

        // adding a call to insertUser, so we have a user to add todos for
        String username = "unittester@tiy.com";
        String fullName = "Unit Tester";
        int userID = todoDatabase.insertUser(conn, username, fullName);

        String username2 = "unitester2@tiy.com";
        String fullName2 = "Unit Tester 2";
        int userID2 = todoDatabase.insertUser(conn, username2, fullName2);

        todoDatabase.insertToDo(conn, todoText, userID);
        todoDatabase.insertToDo(conn, todoText2, userID2);

        // make sure each user only has one todo item
        ArrayList<ToDoItem> todosUser1 = todoDatabase.selectToDosForUser(conn, userID);
        ArrayList<ToDoItem> todosUser2 = todoDatabase.selectToDosForUser(conn, userID2);

        assertEquals(1, todosUser1.size());
        assertEquals(1, todosUser2.size());

        // make sure each todo item matches
        ToDoItem todoUser1 = todosUser1.get(0);
        assertEquals(todoText, todoUser1.getText());
        ToDoItem todoUser2 = todosUser2.get(0);
        assertEquals(todoText2, todoUser2.getText());

        todoDatabase.deleteToDo(conn, todoText);
        todoDatabase.deleteToDo(conn, todoText2);
        // make sure we remove the test user we added earlier
        todoDatabase.deleteUser(conn, username);
        todoDatabase.deleteUser(conn, username2);

    }
}