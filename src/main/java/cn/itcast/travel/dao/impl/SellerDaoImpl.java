package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    /**
         根据商家id查询对象
     */
    @Override
    public Seller findSellerById(int id) {
        String sql="select * from tab_seller where sid= ? ";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),id);
    }
}

