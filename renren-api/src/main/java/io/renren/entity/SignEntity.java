package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 签到表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 15:12:01
 */
@Data
@TableName("sign")
public class SignEntity implements Serializable {
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
	 * 签到时间
	 */
	private Date createDate;
	/**
	 * Z
	 */
	private Integer state;
}
