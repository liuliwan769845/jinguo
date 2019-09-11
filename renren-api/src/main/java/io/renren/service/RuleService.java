package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.entity.RuleEntity;
import java.util.Map;

import java.util.List;

/**
 * 规则表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
public interface RuleService extends IService<RuleEntity> {

    //获取规则说明
    List<RuleEntity> selectRule();

    PageUtils queryPage(Map<String, Object> params);
}

