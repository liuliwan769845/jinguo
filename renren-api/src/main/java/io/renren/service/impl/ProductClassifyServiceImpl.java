package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.dao.ProductClassifyDao;
import io.renren.entity.ProductClassifyEntity;
import io.renren.service.ProductClassifyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("productClassifyService")
public class ProductClassifyServiceImpl extends ServiceImpl<ProductClassifyDao, ProductClassifyEntity> implements ProductClassifyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	Integer state = 1;
        IPage<ProductClassifyEntity> page = this.page(
                new Query<ProductClassifyEntity>().getPage(params),
                new QueryWrapper<ProductClassifyEntity>().eq("state", state)
        );
        return new PageUtils(page);
    }
}
