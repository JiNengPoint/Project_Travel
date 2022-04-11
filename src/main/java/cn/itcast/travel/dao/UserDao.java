package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    //按照用户名查询
    public User findByUserName(String username);
    //保存
    public void save(User user);
    //查找用户名和密码
    User findUserNameAndPassword(String username, String password);
}
