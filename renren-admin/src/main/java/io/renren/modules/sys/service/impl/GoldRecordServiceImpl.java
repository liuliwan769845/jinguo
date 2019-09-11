package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.GoldRecordDao;
import io.renren.modules.sys.entity.GoldRecordEntity;
import io.renren.modules.sys.service.GoldRecordService;


@Service("goldRecordService")
public class GoldRecordServiceImpl extends ServiceImpl<GoldRecordDao, GoldRecordEntity> implements GoldRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoldRecordEntity> page = this.page(
                new Query<GoldRecordEntity>().getPage(params),
                new QueryWrapper<GoldRecordEntity>()
        );

        return new PageUtils(page);
    }

}
