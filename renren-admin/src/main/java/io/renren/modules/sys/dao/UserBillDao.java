package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.UserBillEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户账单记录表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@Mapper
public interface UserBillDao extends BaseMapper<UserBillEntity> {
	
}
