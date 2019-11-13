package com.mall.jelly.es.entity;

import lombok.Data;
import org.elasticsearch.action.fieldstats.FieldStats.Date;
import org.elasticsearch.cluster.metadata.MappingMetaData.Timestamp;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * ES 索引和类型
 * 关系数据库       ⇒ 数据库         ⇒ 表          ⇒ 行             ⇒ 列(Columns)
 * Elasticsearch    ⇒ 索引(Index)   ⇒ 类型(type)  ⇒ 文档(Docments)  ⇒ 字段(Fields)
 */
@Document(indexName = "jelly", type = "product")
@Data
public class ProductEntity {
	/** 主键ID */
	private Integer id;
	/** 类型ID */
	private Integer categoryId;
	/** 名称 */
	private String name;
	/** 小标题 */
	private String subtitle;
	/** 主图像 */
	private String mainImage;
	/** 小标题图像 */
	private String subImages;
	/** 描述 */
	private String detail;
	/** 商品规格 */
	private String attributeList;
	/** 价格 */
	private Double price;
	/** 库存 */
	private Integer stock;
	/** 状态 */
	private Integer status;

	/** 创建人 */
	private String createdBy;
	/** 创建时间 */
	private Date createdTime;

	/** 更新时间 */
	private Timestamp updatedTime;
}
