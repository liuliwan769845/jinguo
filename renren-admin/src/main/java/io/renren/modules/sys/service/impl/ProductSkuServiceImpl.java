package io.renren.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.ProductSkuDao;
import io.renren.modules.sys.entity.ProductSkuEntity;
import io.renren.modules.sys.service.ProductSkuService;


@Service("productSkuService")
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuDao, ProductSkuEntity> implements ProductSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String pid =(String)params.get("productId");//获取商品编号
    	IPage<ProductSkuEntity> page=null;
    	if(pid!=null && !"".equals(pid)) {
    		page = this.page(
                    new Query<ProductSkuEntity>().getPage(params),
                    new QueryWrapper<ProductSkuEntity>()
                    .eq(StringUtils.isNotBlank(pid),"product_id",pid)
            );
    	}else {
    		page = this.page(
                    new Query<ProductSkuEntity>().getPage(params),
                    new QueryWrapper<ProductSkuEntity>()
            );
    	}
        return new PageUtils(page);
    }

}
