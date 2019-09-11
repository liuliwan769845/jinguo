package io.renren.dao;

import io.renren.entity.UserBillEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户账单记录表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Mapper
public interface UserBillDao extends BaseMapper<UserBillEntity> {
	
}
