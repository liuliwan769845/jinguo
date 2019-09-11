package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.LevelEntity;
import io.renren.modules.sys.service.LevelService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 等级表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/level")
public class LevelController {
    @Autowired
    private LevelService levelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:level:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = levelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:level:info")
    public R info(@PathVariable("id") Integer id){
        LevelEntity level = levelService.getById(id);

        return R.ok().put("level", level);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:level:save")
    public R save(@RequestBody LevelEntity level){
        levelService.save(level);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:level:update")
    public R update(@RequestBody LevelEntity level){
        ValidatorUtils.validateEntity(level);
        levelService.updateById(level);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:level:delete")
    public R delete(@RequestBody Integer[] ids){
        levelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
