package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.entity.OrdersEntity;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

/**
 * 订单表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
public interface OrdersService extends IService<OrdersEntity> {
	//根据用户id查询当前用户的订单信息
	List<OrdersEntity> selectOrdersUserId(@Param("userId")Integer userId);
	//查询订单详细信息
	OrdersEntity selectOrdersId(@Param("Id")Integer Id);


}

