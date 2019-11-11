package com.mall.jelly;

import com.alibaba.fastjson.JSONObject;
import com.mall.jelly.base.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 秒杀商品服务接口
 */
public interface SpikeCommodityService {

	/**
	 * 用户秒杀接口 phone和userid都可以的
	 * 
	 * @phone 手机号码<br>
	 * @seckillId 库存id
	 * @return
	 */
	@RequestMapping("/spike")
	 BaseResponse<JSONObject> spike(String phone, Long seckillId);

}
