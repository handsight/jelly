package com.mall.jelly;


import com.mall.jelly.base.BaseResponse;
import com.mall.jelly.response.ProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @description: 商品搜索服务接口
 */
public interface ProductSearchService {

	 @GetMapping("/api/search/searchProduct")
	 BaseResponse<List<ProductDto>> searchProduct(String name, @PageableDefault(page = 0, value = 10) Pageable pageable);

}
