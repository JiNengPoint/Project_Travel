package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImageDaoImpl implements RouteImageDao {

    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     根据rid来查找图片
     */
    @Override
    public List<RouteImg> findImageByRid(int rid) {
        String sql="select * from tab_route_img where rid= ? ";
        return template.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }
}
