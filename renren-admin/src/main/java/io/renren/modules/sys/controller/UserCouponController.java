package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.UserCouponEntity;
import io.renren.modules.sys.service.UserCouponService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 用户优惠券表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/usercoupon")
public class UserCouponController {
    @Autowired
    private UserCouponService userCouponService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:usercoupon:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userCouponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:usercoupon:info")
    public R info(@PathVariable("id") Integer id){
        UserCouponEntity userCoupon = userCouponService.getById(id);

        return R.ok().put("userCoupon", userCoupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:usercoupon:save")
    public R save(@RequestBody UserCouponEntity userCoupon){
    	userCoupon.setCreateDate(new Date());
        userCouponService.save(userCoupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:usercoupon:update")
    public R update(@RequestBody UserCouponEntity userCoupon){
        ValidatorUtils.validateEntity(userCoupon);
        userCouponService.updateById(userCoupon);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:usercoupon:delete")
    public R delete(@RequestBody Integer[] ids){
        userCouponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
