package com.mall.jelly.dao;

import com.mall.jelly.entity.SeckillEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SeckillDao {
    @Select("SELECT seckill_id AS seckillId,name as name,inventory as inventory,start_time as startTime,end_time as endTime,create_time as createTime,version as version from m_seckill_stock where seckill_id=#{seckillId}")
    SeckillEntity findBySeckillId(Long seckillId);


    @Update("update m_seckill_stock set inventory=inventory-1, version=version+1 where  seckill_id=#{seckillId} and inventory>0  and version=#{version}")
    int inventoryDeduction(@Param("seckillId") Long seckillId, @Param("version") Long version);


    @Select("select * from m_seckill_stock")
    List<SeckillEntity> findList();
}
