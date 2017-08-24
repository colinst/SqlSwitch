package com.recruit.service.user;

import com.recruit.dao.user.UserMapper;
import com.recruit.dao.user.UserRoleMapper;
import com.recruit.model.Column;
import com.recruit.model.User;
import com.recruit.model.UserRole;
import com.recruit.service.user.impl.IUserManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 郭宏禧
 * @createTime 2017/8/16
 * @context **
 */
@Service
public class UserManagerService  implements IUserManagerService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    //添加用户
    public int addUser(User user) {
        int i = userMapper.addUser(user);
        return i;
    }

    //添加权限
    public int addUserRole(UserRole userRole) {
        return 0;
    }

    //Mapper调用
    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserRoleMapper getUserRoleMapper() {
        return userRoleMapper;
    }

    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }
}
