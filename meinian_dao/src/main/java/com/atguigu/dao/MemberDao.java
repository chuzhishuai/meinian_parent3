package com.atguigu.dao;

import com.atguigu.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberDao {
    Member getMemberByTelephone(String telephone);

    void add(Member member);

    Integer findMemberCountBeforeDate(String regTime);

    List<Map<String, Object>> findSetmealCount();

    int getTodayNewMember(String today);

    int getTotalMember();

    int getThisWeekAndMonthNewMember(String weekMonday);
}
