package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.GoldRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 金币记录
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@Mapper
public interface GoldRecordDao extends BaseMapper<GoldRecordEntity> {
	
}
