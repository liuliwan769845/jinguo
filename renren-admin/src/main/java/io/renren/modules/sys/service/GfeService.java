package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.GfeEntity;

import java.util.Map;

/**
 * 金果兑换表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
public interface GfeService extends IService<GfeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

