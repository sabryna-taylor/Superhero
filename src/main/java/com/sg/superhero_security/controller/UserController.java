/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_security.controller;

import com.sg.superhero_security.dao.UserDao;
import com.sg.superhero_security.model.User;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Sabryna
 */
@Controller
public class UserController {

    private UserDao dao;
    private PasswordEncoder encoder;

    @Inject
    public UserController(UserDao dao, PasswordEncoder encoder) {
        this.dao = dao;
        this.encoder = encoder;
    }

    @RequestMapping(value = "/displayUserList", method = RequestMethod.GET)
    public String displayUserList(Map<String, Object> model) {
        List users = dao.getAllUsers();
        model.put("users", users);
        return "displayUserList";
    }

    @RequestMapping(value = "/displayUserForm", method = RequestMethod.GET)
    public String displayUserForm(Map<String, Object> model) {
        return "addUserForm";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest req) {
        User newUser = new User();

        newUser.setUsername(req.getParameter("username"));
        String clearPw = req.getParameter("password");
        String hashPw = encoder.encode(clearPw);
        newUser.setPassword(hashPw);

        newUser.addAuthority("ROLE_USER");
        if (null != req.getParameter("isAdmin")) {
            newUser.addAuthority("ROLE_ADMIN");
        }

        dao.addUser(newUser);

        return "redirect:displayUserList";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deletUser(@RequestParam("username") String username,
            Map<String, Object> model) {
        dao.deleteUser(username);
        return "redirect:displayUserList";
    }
}
