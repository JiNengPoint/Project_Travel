package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    /**
         判断是否收藏
     */
    @Override
    public boolean isFavorite(String ridStr, int uid) {
        Favorite favorite=favoriteDao.findByRidAndUid(Integer.parseInt(ridStr),uid);
        //判断favorite是否为null，不为null返回true
        return favorite!=null;
    }
    /**
         添加收藏
     */
    @Override
    public void add(String ridStr, int uid) {
        favoriteDao.add(Integer.parseInt(ridStr),uid);
    }
}
