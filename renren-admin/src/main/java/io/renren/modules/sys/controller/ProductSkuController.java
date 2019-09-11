package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.ProductSkuEntity;
import io.renren.modules.sys.service.ProductSkuService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商品规格表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/productsku")
public class ProductSkuController {
    @Autowired
    private ProductSkuService productSkuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:productsku:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:productsku:info")
    public R info(@PathVariable("id") Integer id){
        ProductSkuEntity productSku = productSkuService.getById(id);

        return R.ok().put("productSku", productSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:productsku:save")
    public R save(@RequestBody ProductSkuEntity productSku){
    	productSku.setCreateDate(new Date());
        productSkuService.save(productSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:productsku:update")
    public R update(@RequestBody ProductSkuEntity productSku){
        ValidatorUtils.validateEntity(productSku);
        productSkuService.updateById(productSku);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:productsku:delete")
    public R delete(@RequestBody Integer[] ids){
        productSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
