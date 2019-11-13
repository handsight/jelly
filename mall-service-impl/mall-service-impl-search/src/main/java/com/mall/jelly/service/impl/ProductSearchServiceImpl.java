package com.mall.jelly.service.impl;


import com.mall.jelly.ProductSearchService;
import com.mall.jelly.base.BaseApiService;
import com.mall.jelly.base.BaseResponse;
import com.mall.jelly.es.entity.ProductEntity;
import com.mall.jelly.es.reposiory.ProductReposiory;
import com.mall.jelly.response.ProductDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductSearchServiceImpl extends BaseApiService<List<ProductDto>> implements ProductSearchService {
	@Autowired
	private ProductReposiory productReposiory;

	@Override
	public BaseResponse<List<ProductDto>> searchProduct(String name, @PageableDefault(page = 0, value = 10) Pageable pageable) {
		// 1.创建查询对象
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		// 2.模糊查询 name、 subtitle、detail含有 搜索关键字
//		builder.must(QueryBuilders.multiMatchQuery(name, "name", "subtitle", "detail"));
		builder.must(QueryBuilders.multiMatchQuery(name, "name"));
		// 3.调用ES接口查询
		Page<ProductEntity> page = productReposiory.search(builder, pageable);
//		productReposiory.save()
//		productReposiory.findById()

		// 4.获取集合数据 记录总数 分页总数等
		List<ProductEntity> list = page.getContent();
		// 5.将entity转换dto
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		List<ProductDto> mapAsList = mapperFactory.getMapperFacade().mapAsList(list, ProductDto.class);
		return setResultSuccess(mapAsList);
	}
}
