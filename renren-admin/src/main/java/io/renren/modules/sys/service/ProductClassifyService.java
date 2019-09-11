package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.ProductClassifyEntity;

import java.util.Map;

/**
 * 商品分类
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
public interface ProductClassifyService extends IService<ProductClassifyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

