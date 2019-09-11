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

import io.renren.modules.sys.entity.SkuEntity;
import io.renren.modules.sys.service.SkuService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 规格表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/sku")
public class SkuController {
    @Autowired
    private SkuService skuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sku:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sku:info")
    public R info(@PathVariable("id") Integer id){
        SkuEntity sku = skuService.getById(id);

        return R.ok().put("sku", sku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sku:save")
    public R save(@RequestBody SkuEntity sku){
    	sku.setCreateDate(new Date());
        skuService.save(sku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sku:update")
    public R update(@RequestBody SkuEntity sku){
        ValidatorUtils.validateEntity(sku);
        skuService.updateById(sku);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sku:delete")
    public R delete(@RequestBody Integer[] ids){
        skuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    
    //查村没有父id的规格
    @RequestMapping("/alllist")
	  public R alllist(){
    	
	  List<SkuEntity> alllist = skuService.selectSku(); 
	  return R.ok().put("alllist", alllist); 
	  
	  }
    @RequestMapping("/selectSkuId")
    public R List(Integer skuid) {
    	List<SkuEntity> list = skuService.selectSkuId(skuid); 
		return R.ok().put("list", list);
    	
    }
}
