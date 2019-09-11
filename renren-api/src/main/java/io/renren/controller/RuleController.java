package io.renren.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.entity.RuleEntity;
import io.renren.service.RuleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;



/**
 * 规则表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 17:24:26
 */
@RestController
@RequestMapping("/api")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    /**
     * 列表
     */
    @RequestMapping("/rule")
    @ApiOperation(value = "规则信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "用户授权code", required = true)
    })
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = ruleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        RuleEntity rule = ruleService.getById(id);

        return R.ok().put("rule", rule);
    }
}
