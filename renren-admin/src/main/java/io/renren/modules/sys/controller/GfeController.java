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

import io.renren.modules.sys.entity.GfeEntity;
import io.renren.modules.sys.service.GfeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 金果兑换表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/gfe")
public class GfeController {
    @Autowired
    private GfeService gfeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:gfe:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = gfeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:gfe:info")
    public R info(@PathVariable("id") Integer id){
        GfeEntity gfe = gfeService.getById(id);

        return R.ok().put("gfe", gfe);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:gfe:save")
    public R save(@RequestBody GfeEntity gfe){
    	gfe.setCreateDate(new Date());
        gfeService.save(gfe);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:gfe:update")
    public R update(@RequestBody GfeEntity gfe){
        ValidatorUtils.validateEntity(gfe);
        gfeService.updateById(gfe);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:gfe:delete")
    public R delete(@RequestBody Integer[] ids){
        gfeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
