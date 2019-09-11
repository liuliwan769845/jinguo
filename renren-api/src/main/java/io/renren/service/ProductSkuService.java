package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.entity.ProductSkuEntity;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

/**
 * 商品规格表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
public interface ProductSkuService extends IService<ProductSkuEntity> {
    PageUtils queryPage(Map<String, Object> params);
    
    List<ProductSkuEntity> selectProductId(@Param("pid")Integer pid);//查询该商品所有规格

}

