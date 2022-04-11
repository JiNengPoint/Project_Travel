package cn.itcast.travel.service;

public interface FavoriteService {
    /**
         判断是否收藏
     */
    public boolean isFavorite(String ridStr,int uid);
    /**
         添加收藏
     */
    public void add(String ridStr, int uid);
}
