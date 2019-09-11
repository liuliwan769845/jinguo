package io.renren.dao;

import io.renren.entity.GoldRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 金币记录
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Mapper
public interface GoldRecordDao extends BaseMapper<GoldRecordEntity> {
	
}
