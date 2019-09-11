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

import io.renren.modules.sys.entity.GoldRecordEntity;
import io.renren.modules.sys.service.GoldRecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 金币记录
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/goldrecord")
public class GoldRecordController {
    @Autowired
    private GoldRecordService goldRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:goldrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = goldRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:goldrecord:info")
    public R info(@PathVariable("id") Integer id){
        GoldRecordEntity goldRecord = goldRecordService.getById(id);

        return R.ok().put("goldRecord", goldRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:goldrecord:save")
    public R save(@RequestBody GoldRecordEntity goldRecord){
    	goldRecord.setCreateDate(new Date());
        goldRecordService.save(goldRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:goldrecord:update")
    public R update(@RequestBody GoldRecordEntity goldRecord){
        ValidatorUtils.validateEntity(goldRecord);
        goldRecordService.updateById(goldRecord);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:goldrecord:delete")
    public R delete(@RequestBody Integer[] ids){
        goldRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
