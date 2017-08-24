package com.recruit.service.user.impl;

import com.recruit.model.Column;
import com.recruit.model.Table;
import com.recruit.model.User;
import com.recruit.model.UserRole;

import java.util.List;

/**
 * @author 郭宏禧
 * @createTime 2017/8/16
 * @context **
 */
public interface IUserManagerService {

    //添加User
    public int addUser(User user);
    //添加UserRole
    public int addUserRole(UserRole userRole);
}
