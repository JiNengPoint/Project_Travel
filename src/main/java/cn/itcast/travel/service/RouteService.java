package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    /**
         根据cid类别进行分页查询
     */
    public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);
    /**
         查询一条线路
     */
    public Route findOne(String ridStr);
}
