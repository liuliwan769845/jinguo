package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.WorkerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工人表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:11
 */
@Mapper
public interface WorkerDao extends BaseMapper<WorkerEntity> {
	
}
