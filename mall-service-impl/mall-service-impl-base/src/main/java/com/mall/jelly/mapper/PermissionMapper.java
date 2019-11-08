package com.mall.jelly.mapper;

import com.mall.jelly.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper {

	/**
     *  查询所有权限
	 * @return
     */
	@Select(" select * from sys_permission")
	List<Permission> findAllPermission();

}
