package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.dao.RuleDao;
import io.renren.entity.RuleEntity;
import io.renren.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("ruleService")
public class RuleServiceImpl extends ServiceImpl<RuleDao, RuleEntity> implements RuleService {

    @Override
    public List<RuleEntity> selectRule() {
         return null;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RuleEntity> page = this.page(
                new Query<RuleEntity>().getPage(params),
                new QueryWrapper<RuleEntity>()
        );

        return new PageUtils(page);
    }

}
