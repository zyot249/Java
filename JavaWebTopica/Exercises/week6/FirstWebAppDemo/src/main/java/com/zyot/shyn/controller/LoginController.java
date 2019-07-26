package com.zyot.shyn.controller;

import com.zyot.shyn.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/authen")
    public String authen() {
        return "login";
    }

    @PostMapping("/login")
    public String display(HttpServletRequest req, Model m) {
        //read the provided form data
        String name = req.getParameter("user");
        String pass = req.getParameter("pass");
        String role = userDao.getRoleOfUser(name, pass);
        if (role == null){
            String msg = new StringBuilder()
                    .append("Sorry ")
                    .append(name)
                    .append(". Incorrect password").toString();
            m.addAttribute("message", msg);
            return "login";
        } else {
            String msg = "Welcome " + name;
            //add a message to the model
            m.addAttribute("message", msg);
            return "viewpage";
        }
    }
}
