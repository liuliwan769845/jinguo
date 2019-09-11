package io.renren.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.UserCouponDao;
import io.renren.modules.sys.entity.UserCouponEntity;
import io.renren.modules.sys.service.UserCouponService;


@Service("userCouponService")
public class UserCouponServiceImpl extends ServiceImpl<UserCouponDao, UserCouponEntity> implements UserCouponService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String uid = (String)params.get("userId");//用户优惠券表中用户id
    	IPage<UserCouponEntity> page = null;
    	if(uid!=null && !"".equals(uid)) {
    		page = this.page(
                    new Query<UserCouponEntity>().getPage(params),
                    new QueryWrapper<UserCouponEntity>()
                    .eq(StringUtils.isNotBlank(uid),"user_id",uid)
            );
    	}else {
    		page = this.page(
                    new Query<UserCouponEntity>().getPage(params),
                    new QueryWrapper<UserCouponEntity>()
            );
    	}
        return new PageUtils(page);
    }

}
