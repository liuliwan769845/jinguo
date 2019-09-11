package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 金果兑换表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Data
@TableName("gfe")
public class GfeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 数量
	 */
	private Integer num;
	/**
	 * 0收获 1兑换
	 */
	private Integer type;
	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 0进行中 1已完成
	 */
	private Integer state;
	/**
	 * 总价
	 */
	private BigDecimal countPrice;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;
	/**
	 * 名称
	 */
	private String name;

}
