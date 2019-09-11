package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.dao.IntroductionDao;
import io.renren.entity.IntroductionEntity;
import io.renren.service.IntroductionService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("introductionService")
public class IntroductionServiceImpl extends ServiceImpl<IntroductionDao, IntroductionEntity> implements IntroductionService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<IntroductionEntity> page = this.page(
                new Query<IntroductionEntity>().getPage(params),
                new QueryWrapper<IntroductionEntity>()
        );
        return new PageUtils(page);
    }
}
