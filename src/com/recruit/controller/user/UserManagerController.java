package com.recruit.controller.user;


import com.recruit.model.User;
import com.recruit.model.UserRole;
import com.recruit.service.user.UserManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 郭宏禧
 * @createTime 2017/8/16
 * @context **
 */
@Controller
@RequestMapping("/user")
public class UserManagerController {

    private List list;

    @Resource
    private UserManagerService service;

    @RequestMapping("adduser.do")
    public String addUser(User user){
        System.out.println(user+"_"+service.addUser(user));
        return "done.jsp";
    }

    @RequestMapping("adduserrole.do")
    public String addUserRole(UserRole userRole){
        service.addUserRole(userRole);
        return "done.jsp";
    }

    //==================
    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public UserManagerService getService() {
        return service;
    }

    public void setService(UserManagerService service) {
        this.service = service;
    }
}
