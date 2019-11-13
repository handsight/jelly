package com.mall.jelly.es.reposiory;

import com.mall.jelly.es.entity.ProductEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductReposiory extends ElasticsearchRepository<ProductEntity, Long> {

}
 