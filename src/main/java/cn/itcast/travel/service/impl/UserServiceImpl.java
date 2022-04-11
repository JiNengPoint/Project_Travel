package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao=new UserDaoImpl();

    /**
         注册用户
     */
    @Override
    public boolean register(User user) {
        //根据用户名查询对象
        User newUser=userDao.findByUserName(user.getUsername());
        //判断用户是否存在
        if(newUser !=null){
            return false;
        }
        userDao.save(user);
        return true;
    }

    /**
         用户登录
     */
    @Override
    public User login(User user) {
        return userDao.findUserNameAndPassword(user.getUsername(),user.getPassword());
    }
}
