package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.IntroductionEntity;

import java.util.Map;

/**
 * 攻略
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
public interface IntroductionService extends IService<IntroductionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

