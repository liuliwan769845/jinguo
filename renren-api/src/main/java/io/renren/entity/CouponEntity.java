package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 优惠卷
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Data
@TableName("coupon")
public class CouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 优惠卷名称
	 */
	private String name;
	/**
	 * 优惠卷编号
	 */
	private String couponSn;
	/**
	 * 过期时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date expirationTime;
	/**
	 * 0折扣券 1代金券 2兑换券
	 */
	private Integer type;
	/**
	 * 所属品牌
	 */
	private Integer brandId;
	/**
	 * 折扣率(%)
	 */
	private Integer rebateNum;
	/**
	 * 金额
	 */
	private BigDecimal price;
	/**
	 * 兑换(1,2)商品集合 用字符串分割
	 */
	private String exchangeProduct;
	/**
	 * 详情介绍
	 */
	private String content;
	/**
	 * 优惠券数量
	 */
	private Integer count;
	/**
	 * 0正常 1禁用
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private Date createDate;
	@TableField(exist = false)
	private Integer Uid;
}
