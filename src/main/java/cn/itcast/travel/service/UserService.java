package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    //注册用户
    boolean register(User user);
    //用户登录
    User login(User user);
}
