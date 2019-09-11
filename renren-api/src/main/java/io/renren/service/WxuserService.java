package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.entity.WxuserEntity;

import java.util.Map;

/**
 * 用户表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
public interface WxuserService extends IService<WxuserEntity> {

    Map<String, Object> login(WxuserEntity wx);
    
    Integer selectUserId(Integer userId);
}

