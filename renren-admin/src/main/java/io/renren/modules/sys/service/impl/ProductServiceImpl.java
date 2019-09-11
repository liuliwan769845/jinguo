package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ProductDao;
import io.renren.modules.sys.entity.ProductEntity;
import io.renren.modules.sys.service.ProductService;


@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String cid =(String)params.get("classifyId");
    	IPage<ProductEntity> page=null;
    	if(!"".equals(cid)&&cid!=null) {
    		 page = this.page(
                    new Query<ProductEntity>().getPage(params),
                    new QueryWrapper<ProductEntity>()
                    .eq(StringUtils.isNotBlank(cid),"classify_id",cid)
            );
    	}else {
    		page = this.page(
                    new Query<ProductEntity>().getPage(params),
                    new QueryWrapper<ProductEntity>()
            );
    	}
        return new PageUtils(page);
    }

}
