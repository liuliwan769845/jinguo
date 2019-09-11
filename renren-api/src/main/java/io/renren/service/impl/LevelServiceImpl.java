package io.renren.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;

import io.renren.dao.LevelDao;
import io.renren.entity.LevelEntity;
import io.renren.service.LevelService;


@Service("levelService")
public class LevelServiceImpl extends ServiceImpl<LevelDao, LevelEntity> implements LevelService {



}
