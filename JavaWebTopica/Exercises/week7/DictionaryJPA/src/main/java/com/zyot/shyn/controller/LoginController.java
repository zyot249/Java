package com.zyot.shyn.controller;

import com.zyot.shyn.common.RoleConstant;
import com.zyot.shyn.common.SessionAttr;
import com.zyot.shyn.dao.DictDao;
import com.zyot.shyn.dao.UserDao;
import com.zyot.shyn.utils.PageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class LoginController {
    @Autowired
    private DictDao dictDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/authen")
    public String authen(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object role = session.getAttribute(SessionAttr.ROLE_ATTR);
        if (role != null)
            return PageRouter.VIEW_PAGE;
        return PageRouter.LOGIN_PAGE;
    }

    @GetMapping("/login")
    public String loginByGetMethod(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object role = session.getAttribute(SessionAttr.ROLE_ATTR);
        if (role == null)
            return PageRouter.LOGIN_PAGE;
        else {
            return PageRouter.VIEW_PAGE;
        }
    }

    @PostMapping("/login")
    public String loginByPostMethod(@RequestParam(name = "user") String username, @RequestParam(name = "pass") String password,
                                    HttpServletRequest request) {
        //read the provided form data
        HttpSession session = request.getSession();
        Object currentRole = session.getAttribute(SessionAttr.ROLE_ATTR);
        if (currentRole == null) {
            String role = userDao.getRoleOfUser(username, password);
            if (role == null) {
                String msgRole = new StringBuilder()
                        .append("Sorry ")
                        .append(username)
                        .append(". Incorrect password").toString();
                session.setAttribute(SessionAttr.MSG_ATTR, msgRole);
                return PageRouter.LOGIN_PAGE;
            } else {
                //add a message to the model
                session.setAttribute(SessionAttr.ROLE_ATTR, role);
                session.setAttribute(SessionAttr.NAME_ATTR, username);
                String msg;
                if (role.equalsIgnoreCase(RoleConstant.ADMIN))
                    msg = "You can edit all the words of dictionary";
                else msg = "Please choose the type and enter the word you want to search!";
                session.setAttribute(SessionAttr.MSG_ATTR, msg);
                List<String> transTypes = dictDao.getAllTranslationTypes();
                session.setAttribute(SessionAttr.TYPE_LIST_ATTR, transTypes);
                session.setAttribute(SessionAttr.DEFAULT_TYPE_ATTR, transTypes.get(0));
                return PageRouter.VIEW_PAGE;
            }
        }
        return PageRouter.VIEW_PAGE;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute(SessionAttr.MSG_ATTR);
        session.removeAttribute(SessionAttr.ROLE_ATTR);
        session.removeAttribute(SessionAttr.TYPE_LIST_ATTR);
        session.removeAttribute(SessionAttr.DEFAULT_TYPE_ATTR);
        return PageRouter.getRedirect("/", null);
    }
}
