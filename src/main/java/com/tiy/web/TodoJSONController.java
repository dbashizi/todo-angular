package com.tiy.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by dbashizi on 1/5/17.
 */

@RestController
public class TodoJSONController {

    public static ToDoDatabase todoDatabase;
    static Connection conn; 


    static {
        try {
            conn = DriverManager.getConnection("jdbc:h2:./main");
            todoDatabase = new ToDoDatabase();
            todoDatabase.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping(path = "/test.json", method = RequestMethod.GET)
    public ArrayList<BankAccount> sample() {
        AccountHolder holder = new AccountHolder("Dominique Bashizi", LocalDateTime.now(), true);
        ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
        accounts.add(new BankAccount("Checking", 100.00, false, holder));
        accounts.add(new BankAccount("Savings", 200.00, false, holder));

        return accounts;
    }

    @RequestMapping(path = "/get-all-books.json", method = RequestMethod.GET)
    public ArrayList<Book> getAllBooks() {
        User user = new User("dev@tiy.com", "Master", "Developer", "testpassword", 7000);
        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book(false, LocalDateTime.now(), "iOS for Dummies", "TJ (who else?)", "Technical", null));
        books.add(new Book(false, LocalDateTime.now(), "Harry Potter and the something", "JK Rowling", "Fantasy", null));
        books.add(new Book(false, LocalDateTime.now(), "String Theory", "Foster Wallace", "Biography", null));
        books.add(new Book(false, LocalDateTime.now(), "100 years of solitude", "Gabriel Garcia Marquez", "Fiction", null));
        return books;
    }

    @RequestMapping(path = "/toggleTodo.json", method = RequestMethod.GET)
    public ArrayList<ToDoItem> toggleTodo(String todoText) throws SQLException {
        System.out.println("toggling todo with text " + todoText);
        todoDatabase.toggleToDo(conn, todoText);
        return getTodos();
    }

    @RequestMapping(path = "/addTodo.json", method = RequestMethod.POST)
    public ArrayList<ToDoItem> addTodo(HttpSession session, @RequestBody ToDoItem todo) throws Exception {
        User user = (User)session.getAttribute("user");

        if (user == null) {
            throw new Exception("Unable to add a Todo without an active user in the session");
        }

        todoDatabase.insertToDo(conn, todo.getText(), user.getId());

        return getTodos();
    }

    @RequestMapping(path = "/login.json", method = RequestMethod.GET)
    public User login(HttpSession session, String username) throws SQLException {
        User user = todoDatabase.findUser(conn, username);
        if (user != null) {
            session.setAttribute("user", user);
            return user;
        }
        return null;
    }

    @RequestMapping(path = "/todos.json", method = RequestMethod.GET)
    public ArrayList<ToDoItem> getTodos() throws SQLException {
        return getAllTodos();
    }

    ArrayList<ToDoItem> getAllTodos() throws SQLException {
        ArrayList<ToDoItem> todos = todoDatabase.selectToDos(conn);
        return todos;
    }

}
