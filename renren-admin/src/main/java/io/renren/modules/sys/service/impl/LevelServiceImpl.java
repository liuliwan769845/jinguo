package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.LevelDao;
import io.renren.modules.sys.entity.LevelEntity;
import io.renren.modules.sys.service.LevelService;


@Service("levelService")
public class LevelServiceImpl extends ServiceImpl<LevelDao, LevelEntity> implements LevelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LevelEntity> page = this.page(
                new Query<LevelEntity>().getPage(params),
                new QueryWrapper<LevelEntity>()
        );

        return new PageUtils(page);
    }

}
