package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)//发布服务，注册到zookeeper中心（zk中心）
@Transactional//声明事务，所有方法都增加事务
public class TravelItemServiceImp implements TravelItemService {

    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //分页插件
        //开启分页功能
        //用limit就是limit（currentPage-1）*pageSize,pageSize
        PageHelper.startPage(currentPage,pageSize);//limit ?,? 第一个问号表示开始索引，第二个问好表示查询条数
        Page page = travelItemDao.findPage(queryString);//返回当前页数据
        return new PageResult(page.getTotal(),page.getResult());//1.总记录数，2分页数据集合
    }

    @Override
    public void delete(Integer id) {
        //查自由行关联表中是否存在关联数据，如果存在，就抛异常，不进行删除
        long count = travelItemDao.findCountByTravelitemId(id);
        if(count>0){ //存在关联数据
            throw new RuntimeException("删除自由行失败，因为存在关联数据。先解除关系，再删除!");
        }
        travelItemDao.delete(id);
    }

    @Override
    public TravelItem getById(Integer id) {
        TravelItem travelItem = travelItemDao.getById(id);
        return travelItem;
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public List<TravelItem> findAll() {
        List<TravelItem> list = travelItemDao.findAll();
        return list;
    }
}
