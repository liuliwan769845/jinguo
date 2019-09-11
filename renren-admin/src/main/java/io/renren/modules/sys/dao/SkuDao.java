package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.SkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 规格表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@Mapper
public interface SkuDao extends BaseMapper<SkuEntity> {
	
	List<SkuEntity> selectSku();//查询没有父id的规格
	
	List<SkuEntity> selectSkuId(Integer skuid);//根据父id查询其下所有信息
	
	
}
