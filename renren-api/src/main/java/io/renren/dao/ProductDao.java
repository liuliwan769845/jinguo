package io.renren.dao;

import io.renren.entity.ProductEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

/**
 * 商品表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Mapper
public interface ProductDao extends BaseMapper<ProductEntity> {
	
	List<ProductEntity> selectProduct(@Param("classifyId")Integer classifyId); //所属该分类的所有商品
	
	ProductEntity selectProductpid(@Param("pid")Integer pid);//商品详情
	
}
