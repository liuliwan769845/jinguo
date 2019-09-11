package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.WxuserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-23 10:14:51
 */
@Mapper
public interface WxuserDao extends BaseMapper<WxuserEntity> {
	
	Integer selectUserId(Integer userId);//通过用户id查询当前用户金果数量
	
}
