package io.renren.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.BrandDao;
import io.renren.modules.sys.entity.BrandEntity;
import io.renren.modules.sys.service.BrandService;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String bname =(String)params.get("name");//品牌名称
    	IPage<BrandEntity> page =null;
    	if(bname!=null && !"".equals(bname)) {
    		page = this.page(
                    new Query<BrandEntity>().getPage(params),
                    new QueryWrapper<BrandEntity>()
                    .eq(StringUtils.isNotBlank(bname),"name",bname)
              );
    	}else {
          page = this.page(
                new Query<BrandEntity>().getPage(params),
                new QueryWrapper<BrandEntity>()
          );
    	}
        return new PageUtils(page);
    }

}
