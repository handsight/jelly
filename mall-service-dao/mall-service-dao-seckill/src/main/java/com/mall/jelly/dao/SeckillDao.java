package com.mall.jelly.dao;

import com.mall.jelly.entity.SeckillEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SeckillDao {
    @Select("SELECT seckillId,name as name,inventory as inventory, startTime, endTime, createTime,version as version from m_seckill_stock where seckillId=#{seckillId}")
    SeckillEntity findBySeckillId(Long seckillId);


    @Update("update m_seckill_stock set inventory=inventory-1, version=version+1 where  seckillId=#{seckillId} and inventory>0  and version=#{version}")
    int inventoryDeduction(@Param("seckillId") Long seckillId, @Param("version") Long version);


    @Select("select * from m_seckill_stock")
    List<SeckillEntity> findList();
}
