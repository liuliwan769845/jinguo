package io.renren.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.service.IntroductionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 攻略
 */
@RestController
@RequestMapping("/api")
public class IntroductionController {
    @Autowired
    private IntroductionService introductionService;

    //攻略信息
    @RequestMapping("/introduction")
    @ApiOperation(value = "攻略信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "用户授权code", required = true)
    })
    public R List(@RequestParam Map<String, Object> params){
        PageUtils page = introductionService.queryPage(params);
        return R.ok().put("page",page);
    }
}
