package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.RuleDao;
import io.renren.modules.sys.entity.RuleEntity;
import io.renren.modules.sys.service.RuleService;


@Service("ruleService")
public class RuleServiceImpl extends ServiceImpl<RuleDao, RuleEntity> implements RuleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RuleEntity> page = this.page(
                new Query<RuleEntity>().getPage(params),
                new QueryWrapper<RuleEntity>()
        );

        return new PageUtils(page);
    }

}
