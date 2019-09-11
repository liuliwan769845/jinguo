package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品规格表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@Data
@TableName("product_sku")
public class ProductSkuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 规格
	 */
	private String sku;
	/**
	 * 库存
	 */
	private Integer stock;
	/**
	 * 金币
	 */
	private Double gold;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 原价
	 */
	private BigDecimal originalPrice;
	/**
	 * 
	 */
	private Date createDate;

}
