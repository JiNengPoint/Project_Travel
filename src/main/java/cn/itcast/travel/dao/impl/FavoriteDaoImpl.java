package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    /**
         判断是否收藏,并返回Favorite对象
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite=null;
        try{
            String sql="select * from tab_favorite where rid= ? and uid= ? ";
            favorite=template.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (DataAccessException e){
            e.printStackTrace();
        }
        return favorite;
    }
    /**
         查找收藏数量
     */
    @Override
    public int findCountByRid(int rid) {
        String sql="select count(*) from tab_favorite where rid= ? ";
        return template.queryForObject(sql,Integer.class,rid);
    }
    /**
         添加收藏
     */
    @Override
    public void add(int rid, int uid) {
        String sql="insert into tab_favorite values(?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }
}
