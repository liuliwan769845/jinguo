package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;

import io.renren.dao.OrdersDao;
import io.renren.entity.OrdersEntity;
import io.renren.service.OrdersService;


@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, OrdersEntity> implements OrdersService {
	@Autowired
	private OrdersDao orderdao;
	
	//根据用户id查询当前用户的订单信息
	@Override
	public List<OrdersEntity> selectOrdersUserId(Integer userId) {
		return orderdao.selectOrdersUserId(userId);
	}
	
	
	//查询订单详细信息
	@Override
	public OrdersEntity selectOrdersId(Integer Id) {
		return orderdao.selectOrdersId(Id);
	}


}
