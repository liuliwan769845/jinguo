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

import io.renren.modules.sys.entity.ProductClassifyEntity;
import io.renren.modules.sys.service.ProductClassifyService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品分类
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/productclassify")
public class ProductClassifyController {
    @Autowired
    private ProductClassifyService productClassifyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:productclassify:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productClassifyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:productclassify:info")
    public R info(@PathVariable("id") Integer id){
        ProductClassifyEntity productClassify = productClassifyService.getById(id);

        return R.ok().put("productClassify", productClassify);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:productclassify:save")
    public R save(@RequestBody ProductClassifyEntity productClassify){
    	productClassify.setCreateDate(new Date());
        productClassifyService.save(productClassify);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:productclassify:update")
    public R update(@RequestBody ProductClassifyEntity productClassify){
        ValidatorUtils.validateEntity(productClassify);
        productClassifyService.updateById(productClassify);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:productclassify:delete")
    public R delete(@RequestBody Integer[] ids){
        productClassifyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 查询所有分类标题信息
     */
	  @RequestMapping("/alllist")
	  public R alllist(){
	  List<ProductClassifyEntity> alllist = productClassifyService.list(new
	  QueryWrapper<ProductClassifyEntity>()); return R.ok().put("alllist", alllist); 
	  }

}
