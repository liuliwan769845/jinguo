package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.WorkerEntity;

import java.util.Map;

/**
 * 工人表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:11
 */
public interface WorkerService extends IService<WorkerEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

