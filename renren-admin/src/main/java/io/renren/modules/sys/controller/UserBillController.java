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

import io.renren.modules.sys.entity.UserBillEntity;
import io.renren.modules.sys.service.UserBillService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 用户账单记录表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:10
 */
@RestController
@RequestMapping("sys/userbill")
public class UserBillController {
    @Autowired
    private UserBillService userBillService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:userbill:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userBillService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:userbill:info")
    public R info(@PathVariable("id") Integer id){
        UserBillEntity userBill = userBillService.getById(id);

        return R.ok().put("userBill", userBill);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:userbill:save")
    public R save(@RequestBody UserBillEntity userBill){
        userBillService.save(userBill);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:userbill:update")
    public R update(@RequestBody UserBillEntity userBill){
        ValidatorUtils.validateEntity(userBill);
        userBillService.updateById(userBill);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:userbill:delete")
    public R delete(@RequestBody Integer[] ids){
        userBillService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
