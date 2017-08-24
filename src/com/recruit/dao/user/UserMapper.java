package com.recruit.dao.user;

import com.recruit.model.Column;
import com.recruit.model.User;

import java.util.List;

/**
 * @author 郭宏禧
 * @createTime 2017/8/15
 * @context **
 */
public interface UserMapper {

    //添加User
    public int addUser(User user);

    //通过表名获得所有用户
    public List<User> getAO(String name);

}
