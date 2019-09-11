package io.renren.dao;

import io.renren.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

/**
 * 优惠卷
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
	List<CouponEntity> selectId(@Param("userId")Integer userId);//查询该用户的优惠卷信息
	
}
