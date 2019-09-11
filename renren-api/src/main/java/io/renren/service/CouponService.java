package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.entity.CouponEntity;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

/**
 * 优惠卷
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
public interface CouponService extends IService<CouponEntity> {
    PageUtils queryPage(Map<String, Object> params);
    
    List<CouponEntity> selectId(@Param("userId")Integer userId);//查询该用户的优惠卷信息
}

