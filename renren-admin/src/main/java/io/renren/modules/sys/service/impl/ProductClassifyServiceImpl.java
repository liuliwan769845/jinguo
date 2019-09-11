package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ProductClassifyDao;
import io.renren.modules.sys.entity.ProductClassifyEntity;
import io.renren.modules.sys.service.ProductClassifyService;


@Service("productClassifyService")
public class ProductClassifyServiceImpl extends ServiceImpl<ProductClassifyDao, ProductClassifyEntity> implements ProductClassifyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductClassifyEntity> page = this.page(
                new Query<ProductClassifyEntity>().getPage(params),
                new QueryWrapper<ProductClassifyEntity>()
        );

        return new PageUtils(page);
    }

}
