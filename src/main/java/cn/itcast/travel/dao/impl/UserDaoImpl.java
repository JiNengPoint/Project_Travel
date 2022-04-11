package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import com.fasterxml.jackson.databind.BeanProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    //创建 JdbcTemplate 对象
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
         按照用户名查询
     */
    @Override
    public User findByUserName(String username) {
        User user=null;
        try{
            //定义sql语句
            String sql="select * from tab_user where username = ?";
            //执行sql语句
            user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username);
        }catch (DataAccessException e){}

        return user;
    }

    /**
         保存
     */
    @Override
    public void save(User user) {
        //定义sql语句
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email) values(?,?,?,?,?,?,?)";
        //执行sql语句
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail());
    }

    /**
         查找用户名和密码
     */
    @Override
    public User findUserNameAndPassword(String username, String password) {
        User user=null;
        try{
            //定义sql语句
            String sql="select * from tab_user where username = ? and password=?";
            //执行sql语句
            user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        }catch (DataAccessException e){}

        return user;
    }


}
