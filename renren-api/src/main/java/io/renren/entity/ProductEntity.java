package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Data
@TableName("product")
public class ProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 分类ID
	 */
	private Integer classifyId;
	/**
	 * 品牌ID
	 */
	private Integer brandId;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品标题
	 */
	private String title;
	/**
	 * 商品主图
	 */
	private String imgUrl;
	/**
	 * 图片路径
	 */
	private String imgUrlLj;
	/**
	 * 商品介绍
	 */
	private String content;
	/**
	 * 商品购买单价
	 */
	private BigDecimal price;
	/**
	 * 商品购买金币数量
	 */
	private Double gold;
	/**
	 * 商品原价
	 */
	private BigDecimal originalPrice;
	/**
	 * 库存
	 */
	private Integer stock;
	/**
	 * 商品创建时间
	 */
	private Date createDate;

}
