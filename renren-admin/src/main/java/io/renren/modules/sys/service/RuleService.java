package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.RuleEntity;

import java.util.Map;

/**
 * 规则表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
public interface RuleService extends IService<RuleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

