package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 规格表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
public interface SkuService extends IService<SkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<SkuEntity> selectSku();
    
    List<SkuEntity> selectSkuId(Integer skuid);
}

