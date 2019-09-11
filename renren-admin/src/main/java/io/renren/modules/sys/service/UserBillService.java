package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.UserBillEntity;

import java.util.Map;

/**
 * 用户账单记录表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
public interface UserBillService extends IService<UserBillEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

