package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.UserCouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户优惠券表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@Mapper
public interface UserCouponDao extends BaseMapper<UserCouponEntity> {
	
}
