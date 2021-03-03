package com.atguigu.service;

import com.atguigu.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService implements UserDetailsService {

    //模拟数据库中的用户数据
    static Map<String, User> map =   new HashMap<String, User>();

    static {
        com.atguigu.pojo.User user1 =  new com.atguigu.pojo.User();
        user1.setUsername("admin");
        user1.setPassword("admin");
//        user1.setPassword("$2a$10$IP3/o6kQ0DZxcPg87oCdw.k9CcFced9wHkvF8cB5xwwnH51UX5YY2");
        user1.setTelephone("123");

        com.atguigu.pojo.User user2 =  new com.atguigu.pojo.User();
        user2.setUsername("zhangsan");
        user2.setPassword("$2a$10$90obGBgo8YuF6GQyeveEhO/JI7ZQEplpDXocdhY4gG.y2dEXbGGHy");
        user2.setTelephone("321");

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }

    //框架帮我们认证时，调用这个方法，传递登录表单的用户名称，我们根据用户名称到数据库中查询用户信息，
    //将用户名称，密码，和权限集合返回给框架即可。
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库中查询用户信息
        com.atguigu.pojo.User userInDb = map.get(username);

        if (userInDb==null){
            //根据用户名没有查询到用户，抛出异常，表示登录名输入有误
            return null;
        }
        //模拟数据库中的密码，后期需要查询数据库
        String passwordInDb ="{noop}" + userInDb.getPassword();
        //授权，后期需要改为查询数据库动态获得用户拥有的权限和角色
        List<GrantedAuthority> lists = new ArrayList<>();
        lists.add(new SimpleGrantedAuthority("add"));
        lists.add(new SimpleGrantedAuthority("delete"));
        lists.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        //lists.add(new SimpleGrantedAuthority("ABC"));


        // User  implements UserDetails
        //return new org.springframework.security.core.userdetails.User(userInDb.getUsername(),passwordInDb,lists);
        return new org.springframework.security.core.userdetails.User(userInDb.getUsername(),passwordInDb,lists);
    }
}
