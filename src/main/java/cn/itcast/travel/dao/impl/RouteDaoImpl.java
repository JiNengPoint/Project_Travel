package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    /**
         根据cid查总中记录数
     */
    @Override
    public int findTotalCount(int cid,String rname) {
        //String sql="select count(*) from tab_route where cid=?";
        String sql="select count(*) from tab_route where 1=1 ";
        StringBuilder strB=new StringBuilder(sql);
        List params=new ArrayList<>();
        //sql字符串拼接
        if(cid!=0){
            strB.append(" and cid= ? ");
            params.add(cid);
        }
        if(rname!=null){
            strB.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql=strB.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    /**
         查询一页中的数据集合
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        //String sql="select * from tab_route where cid=? limit ? , ?";
        String sql="select * from tab_route where 1=1 ";
        StringBuilder strB=new StringBuilder(sql);
        List params=new ArrayList<>();
        if(cid!=0){
            strB.append(" and cid= ? ");
            params.add(cid);
        }
        if(rname!=null){
            strB.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        strB.append(" limit ? , ? ");
        sql=strB.toString();
        params.add(start);
        params.add(pageSize);
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }
    /**
         根据线路id查询一个线路
     */
    @Override
    public Route findOne(int rid) {
        String sql="select * from tab_route where rid= ? ";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
