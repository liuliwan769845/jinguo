package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.entity.IntroductionEntity;

import java.util.Map;

/**
 * 攻略
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
public interface IntroductionService extends IService<IntroductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

