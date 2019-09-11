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

import io.renren.modules.sys.entity.IntroductionEntity;
import io.renren.modules.sys.service.IntroductionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 攻略
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/introduction")
public class IntroductionController {
    @Autowired
    private IntroductionService introductionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:introduction:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = introductionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:introduction:info")
    public R info(@PathVariable("id") Integer id){
        IntroductionEntity introduction = introductionService.getById(id);

        return R.ok().put("introduction", introduction);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:introduction:save")
    public R save(@RequestBody IntroductionEntity introduction){
        introduction.setCreateDate(new Date());
        introductionService.save(introduction);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:introduction:update")
    public R update(@RequestBody IntroductionEntity introduction){
        ValidatorUtils.validateEntity(introduction);
        introductionService.updateById(introduction);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:introduction:delete")
    public R delete(@RequestBody Integer[] ids){
        introductionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
