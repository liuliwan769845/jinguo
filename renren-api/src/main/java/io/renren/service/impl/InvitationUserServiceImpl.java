package io.renren.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.dao.InvitationUserDao;
import io.renren.entity.InvitationUserEntity;
import io.renren.service.InvitationUserService;


@Service("invitationUserService")
public class InvitationUserServiceImpl extends ServiceImpl<InvitationUserDao, InvitationUserEntity> implements InvitationUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InvitationUserEntity> page = this.page(
                new Query<InvitationUserEntity>().getPage(params),
                new QueryWrapper<InvitationUserEntity>()
        );

        return new PageUtils(page);
    }

}
