package io.renren.dao;

import io.renren.entity.SignEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 签到表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 15:12:01
 */
@Mapper
public interface SignDao extends BaseMapper<SignEntity> {
	
}
