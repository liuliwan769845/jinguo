package io.renren.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.annotation.Login;
import io.renren.annotation.LoginUser;
import io.renren.common.utils.R;
import io.renren.entity.OrdersEntity;
import io.renren.entity.WxuserEntity;
import io.renren.service.OrdersService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/*
 * 订单信息
 */
@RestController
@RequestMapping("/api")
public class OrdersController {
	@Autowired
	private OrdersService orderService;
	
//	@Login
	@RequestMapping("/orderlist")
    @ApiOperation(value = "订单信息列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "用户授权code", required = true)
    })
	public R list(@ApiIgnore @LoginUser WxuserEntity user) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<OrdersEntity> list = orderService.selectOrdersUserId(user.getId());
			map.put("list",list);
			return R.ok().put("map", map);
	}
	//查看订单详情
	@RequestMapping("/order")
    @ApiOperation(value = "订单信息详情", httpMethod = "POST")
	public R selectOrder(Integer Id) {
//		Map<String, Object> map = new HashMap<String, Object>();
		OrdersEntity orders = orderService.selectOrdersId(Id);
//		map.put("", orders);
		return R.ok().put("orders", orders);
	}
	
	
	

}
