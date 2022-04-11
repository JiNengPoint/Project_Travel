package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImageDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao=new RouteDaoImpl();
    private RouteImageDao imageDao=new RouteImageDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    /**
         根据cid类别进行分页查询
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        PageBean<Route> pageBean=new PageBean<Route>();
        //设置当前页码
        pageBean.setCurrentPage(currentPage);
        //设置每页的记录数量
        pageBean.setPageSize(pageSize);
        //设置总记录数
        int totalCount=routeDao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        //设置每页的记录集合
        int start=(currentPage-1)*pageSize;
        List<Route> lists=routeDao.findByPage(cid,start,pageSize,rname);
        pageBean.setList(lists);
        //设置总页数
        int totalPage=totalCount % pageSize==0 ? totalCount/pageSize : (totalCount/pageSize)+1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }
    /**
         查询一条线路
     */
    @Override
    public Route findOne(String ridStr) {
        int rid=Integer.parseInt(ridStr);
        //根据rid找去route集合
        Route route=routeDao.findOne(rid);
        //根据rid找去route图片
        List<RouteImg> listImage=imageDao.findImageByRid(rid);
        route.setRouteImgList(listImage);
        //根据商家id来查询商家信息
        Seller seller=sellerDao.findSellerById(route.getSid());
        route.setSeller(seller);
        //查找收藏次数
        int count=favoriteDao.findCountByRid(rid);
        route.setCount(count);
        return route;
    }
}
