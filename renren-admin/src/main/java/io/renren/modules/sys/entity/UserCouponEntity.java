package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户优惠券表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@Data
@TableName("user_coupon")
public class UserCouponEntity implements Serializable {
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
	 * 优惠卷ID
	 */
	private Integer couponId;
	/**
	 * 0未使用 1已使用
	 */
	private Integer state;
	/**
	 * 过期时间
	 */
	private Date expirationTime;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
