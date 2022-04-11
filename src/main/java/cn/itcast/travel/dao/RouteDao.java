package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
         根据cid查总中记录数
     */
    public int findTotalCount(int cid,String rname);
    /**
         查询一页中的数据集合
     */
    public List<Route> findByPage(int cid,int start,int pageSize,String rname);
    /**
         根据线路id查询一个线路
     */
    public Route findOne(int rid);

}
