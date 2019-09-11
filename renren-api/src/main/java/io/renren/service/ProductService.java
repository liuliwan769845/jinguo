package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.entity.ProductEntity;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

/**
 * 商品表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
public interface ProductService extends IService<ProductEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<ProductEntity> selectProduct(@Param("classifyId")Integer classifyId);
    
    ProductEntity selectProductpid(@Param("pid")Integer pid);

}

