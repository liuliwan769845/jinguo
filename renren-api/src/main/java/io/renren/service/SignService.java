package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.entity.SignEntity;

import java.util.Map;

/**
 * 签到表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-09-03 15:12:01
 */
public interface SignService extends IService<SignEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

