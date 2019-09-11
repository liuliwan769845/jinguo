package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.UserCouponEntity;

import java.util.Map;

/**
 * 用户优惠券表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
public interface UserCouponService extends IService<UserCouponEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

