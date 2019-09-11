package io.renren.controller;

import io.renren.annotation.Login;
import io.renren.annotation.LoginUser;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.entity.CouponEntity;
import io.renren.entity.UserAddressEntity;
import io.renren.entity.WxuserEntity;
import io.renren.service.CouponService;
import io.renren.service.UserAddressService;
import io.renren.service.WxuserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

/**
 * 背包
 */
@RestController
@RequestMapping("/api")
public class CouponController {
	@Autowired
    private WxuserService wxuserService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserAddressService userAddressService;

    //优惠卷信息
    @Login
    @RequestMapping("/coupon")
    @ApiOperation(value = "优惠卷信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "用户授权code", required = true)
    })
    public R List(@ApiIgnore @LoginUser WxuserEntity user){
    	List<CouponEntity> listcoupon = couponService.selectId(user.getId());
        return R.ok().put("listcoupon",listcoupon);
    }
    
    //个人中心  wxuser
    @Login
    @RequestMapping("/personal")
    @ApiOperation(value = "个人中心", httpMethod = "POST")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "code", value = "用户授权code", required = true)
    })
    public R personal(@ApiIgnore @LoginUser WxuserEntity user) {
    	UserAddressEntity address=userAddressService.getById(user.getId());
		return R.ok().put("personal", user).put("address", address);
    	
    }
    
//    @Login
//    @RequestMapping("/personalAddress")
//    @ApiOperation(value = "", httpMethod = "POST")
//    @ApiImplicitParams({
//        @ApiImplicitParam(paramType = "query", name = "code", value = "用户授权code", required = true)
//    })
//    public R personalAddress(@ApiIgnore @LoginUser WxuserEntity user) {
//    	UserAddressEntity address=userAddressService.getById(user.getId());
//		return R.ok().put("address", address);
//    	
//    }
    
    
    

}
