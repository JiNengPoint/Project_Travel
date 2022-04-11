package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    /**
         判断是否收藏,并返回Favorite对象
     */
    public Favorite findByRidAndUid(int rid, int uid);
    /**
         查找收藏数量
     */
    public int findCountByRid(int rid);
    /**
         添加收藏
     */
    public void add(int rid, int uid);
}
