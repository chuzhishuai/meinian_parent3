package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travelGroup")
public class TravelGroupController {

    @Reference
    TravelGroupService travelGroupService;

    @RequestMapping("/findAll")
    public Result findAll(){

        List<TravelGroup> listAll = travelGroupService.findAll();
        //return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,listAll);
        if(listAll != null && listAll.size() > 0){
            Result result = new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,listAll);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
    }

    @RequestMapping("/edit")
    public Result edit(Integer[] travelItemIds, @RequestBody TravelGroup travelGroup){
        try {
            travelGroupService.edit(travelItemIds,travelGroup);
            return new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/getTravelitemIdsByTravelGroupId")
    public Result getTravelitemIdsByTravelGroupId(Integer travelGroupId){
        try {
            List<Integer> travelitemIds = travelGroupService.getTravelitemIdsByTravelGroupId(travelGroupId);
            return new Result(true,MessageConstant.QUERY_TRAVELITEMBYTRAVELGROUP_SUCCESS,travelitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_TRAVELITEMBYTRAVELGROUP_FAIL);
        }
    }

    @RequestMapping("/getById")
    public Result getById(Integer id){
        try {
            TravelGroup travelGroup = travelGroupService.getById(id);
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelGroupService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(Integer[] travelItemIds, @RequestBody TravelGroup travelGroup){
        try {
            travelGroupService.add(travelItemIds,travelGroup);
            return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        }
    }

}
