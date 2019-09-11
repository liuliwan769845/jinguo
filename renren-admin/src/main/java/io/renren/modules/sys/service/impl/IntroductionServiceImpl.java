package io.renren.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.IntroductionDao;
import io.renren.modules.sys.entity.IntroductionEntity;
import io.renren.modules.sys.service.IntroductionService;


@Service("introductionService")
public class IntroductionServiceImpl extends ServiceImpl<IntroductionDao, IntroductionEntity> implements IntroductionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {  
    	String tt =(String)params.get("title");//获取攻略标题
    	IPage<IntroductionEntity> page=null;
    	if(tt!=null && !"".equals(tt)) {
    		page = this.page(
                    new Query<IntroductionEntity>().getPage(params),
                    new QueryWrapper<IntroductionEntity>()
                    .eq(StringUtils.isNotBlank(tt),"title",tt)
            );
    	}else {
    		page = this.page(
                    new Query<IntroductionEntity>().getPage(params),
                    new QueryWrapper<IntroductionEntity>()
            );
    	}
        return new PageUtils(page);
    }

}
