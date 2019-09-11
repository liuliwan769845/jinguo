package io.renren.dao;

import io.renren.entity.ProductClassifyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品分类
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Mapper
public interface ProductClassifyDao extends BaseMapper<ProductClassifyEntity> {
	
}
