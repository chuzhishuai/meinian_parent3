package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public void addBatch(List<OrderSetting> listData) {
        for (OrderSetting orderSetting : listData) {
            //如果对应日期存在，就修改，否则就添加
            int count = orderSettingDao.findOrderSettingByOrderDate(orderSetting.getOrderDate());
            if(count>0){
                orderSettingDao.edit(orderSetting);
            }else {
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMouth(String date) {//2021-1
        String startDate = date + "-1";//2021-1-1
        String endDate = date + "-31";//2021-1-31
        Map<String,String> param = new HashMap<>();
        param.put("startDate",startDate);
        param.put("endDate",endDate);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMouth(param);
        List<Map> listData = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map map = new HashMap();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            listData.add(map);
        }
        return listData;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        int count = orderSettingDao.findOrderSettingByOrderDate(orderSetting.getOrderDate());
        if(count > 0){
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingDao.edit(orderSetting);
        }else{
            //当前日期没有进行预约设置，进行添加操作
            orderSettingDao.add(orderSetting);
        }
    }
}
