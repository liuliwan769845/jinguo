package io.renren.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;


import io.renren.dao.GfeDao;
import io.renren.entity.GfeEntity;
import io.renren.service.GfeService;


@Service("gfeService")
public class GfeServiceImpl extends ServiceImpl<GfeDao, GfeEntity> implements GfeService {


}
