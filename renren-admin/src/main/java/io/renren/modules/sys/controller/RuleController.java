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

import io.renren.modules.sys.entity.RuleEntity;
import io.renren.modules.sys.service.RuleService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 规则表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/rule")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:rule:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ruleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:rule:info")
    public R info(@PathVariable("id") Integer id){
        RuleEntity rule = ruleService.getById(id);

        return R.ok().put("rule", rule);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:rule:save")
    public R save(@RequestBody RuleEntity rule){
        ruleService.save(rule);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:rule:update")
    public R update(@RequestBody RuleEntity rule){
        ValidatorUtils.validateEntity(rule);
        ruleService.updateById(rule);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:rule:delete")
    public R delete(@RequestBody Integer[] ids){
        ruleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
