package io.renren.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.OrdersDao;
import io.renren.modules.sys.entity.OrdersEntity;
import io.renren.modules.sys.service.OrdersService;


@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, OrdersEntity> implements OrdersService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String SN =(String)params.get("sn");
    	IPage<OrdersEntity> page = null;
    	if(SN!=null&&!"".equals(SN)) {
    		page = this.page(
                    new Query<OrdersEntity>().getPage(params),
                    new QueryWrapper<OrdersEntity>()
                    .eq(StringUtils.isNotBlank(SN),"sn",SN)
            );
    	}else {
         page = this.page(
                new Query<OrdersEntity>().getPage(params),
                new QueryWrapper<OrdersEntity>()
        );
      }
        return new PageUtils(page);
    }

}
