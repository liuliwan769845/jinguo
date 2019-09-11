package io.renren.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;


import io.renren.dao.UserBillDao;
import io.renren.entity.UserBillEntity;
import io.renren.service.UserBillService;


@Service("userBillService")
public class UserBillServiceImpl extends ServiceImpl<UserBillDao, UserBillEntity> implements UserBillService {


}
