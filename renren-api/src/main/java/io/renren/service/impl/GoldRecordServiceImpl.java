package io.renren.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;


import io.renren.dao.GoldRecordDao;
import io.renren.entity.GoldRecordEntity;
import io.renren.service.GoldRecordService;


@Service("goldRecordService")
public class GoldRecordServiceImpl extends ServiceImpl<GoldRecordDao, GoldRecordEntity> implements GoldRecordService {



}
