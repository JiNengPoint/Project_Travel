package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
         根据商家id查询对象
     */
    public Seller findSellerById(int id);
}
