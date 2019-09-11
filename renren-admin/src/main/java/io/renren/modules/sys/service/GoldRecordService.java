package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.GoldRecordEntity;

import java.util.Map;

/**
 * 金币记录
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
public interface GoldRecordService extends IService<GoldRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

