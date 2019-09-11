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

import io.renren.modules.sys.entity.WorkerEntity;
import io.renren.modules.sys.service.WorkerService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 工人表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:11
 */
@RestController
@RequestMapping("sys/worker")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:worker:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = workerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:worker:info")
    public R info(@PathVariable("id") Integer id){
        WorkerEntity worker = workerService.getById(id);

        return R.ok().put("worker", worker);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:worker:save")
    public R save(@RequestBody WorkerEntity worker){
    	worker.setCreateDate(new Date());
        workerService.save(worker);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:worker:update")
    public R update(@RequestBody WorkerEntity worker){
        ValidatorUtils.validateEntity(worker);
        workerService.updateById(worker);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:worker:delete")
    public R delete(@RequestBody Integer[] ids){
        workerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
