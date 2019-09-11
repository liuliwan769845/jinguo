package io.renren.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.LevelEntity;
import io.renren.service.LevelService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 等级表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@RestController
@RequestMapping("renren/level")
public class LevelController {
    @Autowired
    private LevelService levelService;



}
