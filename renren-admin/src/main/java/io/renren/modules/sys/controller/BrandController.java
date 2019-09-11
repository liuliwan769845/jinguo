package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.renren.modules.sys.entity.BrandEntity;
import io.renren.modules.sys.service.BrandService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 品牌
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:brand:info")
    public R info(@PathVariable("id") Integer id){
        BrandEntity brand = brandService.getById(id);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:brand:save")
    public R save(@RequestBody BrandEntity brand){
    	Date date = new Date();
    	brand.setCreateDate(date);
        brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:brand:update")
    public R update(@RequestBody BrandEntity brand){
        ValidatorUtils.validateEntity(brand);
        brandService.updateById(brand);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:brand:delete")
    public R delete(@RequestBody Integer[] ids){
        brandService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 查询所有品牌信息
     */
	  @RequestMapping("/alllist")
	  public R alllist(){
	  List<BrandEntity> alllist = brandService.list(new
	  QueryWrapper<BrandEntity>()); return R.ok().put("alllist", alllist); 
	  }

}
