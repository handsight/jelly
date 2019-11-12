package com.mall.jelly.dao;

import com.mall.jelly.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OrderDao {
    @Insert("INSERT INTO `m_order` VALUES (#{seckillId},#{userPhone},#{state}, now());")
    int insertOrder(OrderEntity orderEntity);

    @Select("SELECT seckill_id AS seckillid,user_phone as userPhone , state as state FROM m_order WHERE USER_PHONE=#{phone}  and seckill_id=#{seckillId}  AND STATE='1';")
    OrderEntity findByOrder(@Param("phone") String phone, @Param("seckillId") Long seckillId);
}
