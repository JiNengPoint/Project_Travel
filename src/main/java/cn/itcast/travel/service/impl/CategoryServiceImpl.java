package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao=new CategoryDaoImpl();
    /**
         查询所有
     */
    @Override
    public List<Category> findAll() {
        //进行缓存优化,减少多次读取数据库中的相同数据
        Jedis jedis=JedisUtil.getJedis();
        //Set<String> sets=jedis.zrange("category",0,-1);
        Set<Tuple> sets=jedis.zrangeWithScores("category",0,-1);
        //判断是否为空
        List<Category> all=null;
        if(sets==null || sets.size()==0){
            //为空，代表第一次从数据库中读取，读取后存入jedis缓存
            all=categoryDao.findAll();
            for(int i=0;i<all.size();i++){
                jedis.zadd("category",all.get(i).getCid(),all.get(i).getCname());
            }
        }
        else {
            //不为空则直接返回原先存储数据
            all=new ArrayList<Category>();
            for (Tuple set : sets) {
                Category category=new Category();
                category.setCname(set.getElement());
                category.setCid((int)set.getScore());
                all.add(category);
            }
        }
        return all;
        //return categoryDao.findAll();
    }
}
