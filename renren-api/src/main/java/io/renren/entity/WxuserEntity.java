package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Data
@TableName("wxuser")
public class WxuserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * openid
	 */
	private String wxOpenId;
	/**
	 * 头像      
	 */
	private String imgUrl;
	/**
	 * 金币数量
	 */
	private Double goldNum;
	/**
	 * 余额
	 */
	private BigDecimal balance;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 金果总数量
	 */
	private Integer goldenFruitCount;
	/**
	 * 剩余金果数量
	 */
	private Integer goldenFruitNum;
	/**
	 * 升级金果数量
	 */
	private Integer  goldenSjNum;
	/**
	 * 金果开始计算时间(时间戳/秒)
	 */
	private Integer goldenFruitTime;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 等級
	 */
	private Integer level;
}
