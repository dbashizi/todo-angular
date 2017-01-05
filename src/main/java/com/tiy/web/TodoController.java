package com.tiy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by dbashizi on 1/5/17.
 */
@Controller
public class TodoController {

    @RequestMapping(path = "/todos", method = RequestMethod.GET)
    public String todos(Model model, HttpSession session) {
        return "todos";
    }
}
