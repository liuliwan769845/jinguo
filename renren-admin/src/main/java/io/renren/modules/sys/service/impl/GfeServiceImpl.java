package io.renren.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.GfeDao;
import io.renren.modules.sys.entity.GfeEntity;
import io.renren.modules.sys.service.GfeService;


@Service("gfeService")
public class GfeServiceImpl extends ServiceImpl<GfeDao, GfeEntity> implements GfeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String uid =(String)params.get("userId");//获取用户编号
    	IPage<GfeEntity> page = null;
    	if(uid!=null && !"".equals(uid)) {
    		page = this.page(
                    new Query<GfeEntity>().getPage(params),
                    new QueryWrapper<GfeEntity>()
                    .eq(StringUtils.isNotBlank(uid),"user_id",uid)
            );
    	}else {
    		page = this.page(
                    new Query<GfeEntity>().getPage(params),
                    new QueryWrapper<GfeEntity>()
            );	
    	}
        return new PageUtils(page);
    }

}
