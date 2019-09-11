package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 金币记录
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@Data
@TableName("gold_record")
public class GoldRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer userId;
	/**
	 * 数量
	 */
	private Double num;
	/**
	 * 0收入  1兑换
	 */
	private Integer type;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 订单ID
	 */
	private Integer orderId;
	/**
	 * 名称
	 */
	private String name;

}
