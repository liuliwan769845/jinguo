package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.dao.ProductSkuDao;
import io.renren.entity.ProductSkuEntity;
import io.renren.service.ProductSkuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("productSkuService")
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuDao, ProductSkuEntity> implements ProductSkuService {
	
	@Autowired
	private ProductSkuDao productSkuDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductSkuEntity> page = this.page(
                new Query<ProductSkuEntity>().getPage(params),
                new QueryWrapper<ProductSkuEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<ProductSkuEntity> selectProductId(Integer pid) {
		return productSkuDao.selectProductId(pid);
	}
}
