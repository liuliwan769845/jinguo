package io.renren.dao;

import io.renren.entity.ProductSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

/**
 * 商品规格表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Mapper
public interface ProductSkuDao extends BaseMapper<ProductSkuEntity> {
	List<ProductSkuEntity> selectProductId(@Param("pid")Integer pid); //所属该商品所有规格
	
}
