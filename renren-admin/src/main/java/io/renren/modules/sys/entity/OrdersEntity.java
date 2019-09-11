package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@Data
@TableName("orders")
public class OrdersEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品ID
	 */
	private Integer productId;
	/**
	 * 订单号
	 */
	private String sn;
	/**
	 * 微信订单号
	 */
	private String wxSn;
	/**
	 * 订单金额
	 */
	private BigDecimal price;
	/**
	 * 交易金币
	 */
	private Double gold;
	/**
	 * 商品实际价格
	 */
	private BigDecimal productPrice;
	/**
	 * 实际付款金额
	 */
	private BigDecimal totlePrice;
	/**
	 * 邮费
	 */
	private BigDecimal mailPrice;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 联系方式
	 */
	private String userMobile;
	/**
	 * 联系人
	 */
	private String username;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 发货时间
	 */
	private Date deliveryDate;
	/**
	 * 付款时间
	 */
	private Date paymentDate;
	/**
	 * 0待付款 1已付款 2已发货 3已完成
	 */
	private Integer state;
	/**
	 * 订单规格
	 */
	private String sku;
	/**
	 * 物流公司
	 */
	private String logistics;
	/**
	 * 物流单号
	 */
	private String logisticsOdd;

}
