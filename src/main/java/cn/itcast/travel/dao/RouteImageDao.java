package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImageDao {
    /**
         根据rid来查找图片
     */
    public List<RouteImg> findImageByRid(int rid);
}
