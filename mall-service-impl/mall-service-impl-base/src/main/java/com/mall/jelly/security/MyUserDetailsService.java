package com.mall.jelly.security;


import com.mall.jelly.entity.Permission;
import com.mall.jelly.entity.User;
import com.mall.jelly.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 1.根据用户名称查询数据用户信息
		User user = userMapper.findByUsername(username);
		// 2. 给用户设置权限
		List<Permission> listPermission = userMapper.findPermissionByUsername(username);
		System.out.println("username:" + username + ",对应权限:" + listPermission.toString());
		if (listPermission != null && listPermission.size() > 0) {
			// 定义用户权限
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for (Permission permission : listPermission) {
				authorities.add(new SimpleGrantedAuthority(permission.getPermTag()));
			}
			user.setAuthorities(authorities);
		}
		return user;
	}

}
