package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 等级表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@Data
@TableName("level")
public class LevelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 11
	 */
	@TableId
	private Integer id;
	/**
	 * 等级数
	 */
	private Integer levelNum;
	/**
	 * 当前等级升级金果数量
	 */
	private Integer exp;
	/**
	 * 当前等级金果每秒产生个数
	 */
	private Integer yield;
	/**
	 * 当前等级金果所产数量量上限
	 */
	private Integer yieldCount;
	/**
	 * 当前是否解锁工人 0解锁 1不解锁
	 */
	private Integer isWorker;
	/**
	 * 当前等级解锁工人个数
	 */
	private Integer workerCount;
	/**
	 * 当前等级所产生优惠卷数量
	 */
	private Integer couponCount;
	/**
	 * 产生几率(%)
	 */
	private Integer couponShop;
	/**
	 * 
	 */
	private Date createDate;

}
