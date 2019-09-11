package io.renren.controller;

import io.renren.annotation.Login;
import io.renren.annotation.LoginUser;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.entity.ProductClassifyEntity;
import io.renren.entity.ProductEntity;
import io.renren.entity.ProductSkuEntity;
import io.renren.entity.WxuserEntity;
import io.renren.service.ProductClassifyService;
import io.renren.service.ProductService;
import io.renren.service.ProductSkuService;
import io.renren.service.WxuserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品
 */
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductClassifyService productClassifyService;//商品分类
    @Autowired
    private ProductSkuService productSkuService;//商品规格表
    @Autowired
    private ProductService productService;//商品表
    @Autowired
    private WxuserService wxuserService;//用户表

    //商品分类信息
    @Login
    @RequestMapping("/productcf")
    @ApiOperation(value = "商城首页获取商品分类信息和主推信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "用户授权code", required = true)
    })
    public R list(@RequestParam Map<String, Object> params,@ApiIgnore @LoginUser WxuserEntity user){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer userid =user.getId();
//       user.getGoldNum();
        Integer goldNum = wxuserService.selectUserId(userid);
        map.put("goldNum",goldNum);
        PageUtils page = productClassifyService.queryPage(params);
        for (int i = 0; i < (page.getList()).size(); i++) {
            ProductClassifyEntity coupon = (ProductClassifyEntity)(page.getList()).get(i);
            Integer id = coupon.getId();
            List<ProductEntity> list = productService.selectProduct(id);
            coupon.setList(list);
        }
        map.put("page", page);
     return R.ok().put("map",map);
    }
    
    //该分类下商品信息
    @RequestMapping("/product")
    @ApiOperation(value = "获取该分类商品 信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "用户授权code", required = true)
    })
    public R list1(@RequestParam Map<String, Object> params,Integer pcfid){
    	List<ProductEntity> list = productService.selectProduct(pcfid);
		return R.ok().put("list",list);
    	
    }
    //查询该商品详细信息
    @RequestMapping("/productsku")
    @ApiOperation(value = "获取该商品的详细信息", httpMethod = "POST")
    public R ProductPay(Integer pid) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	ProductEntity product = productService.selectProductpid(pid);
    	map.put("product",product);
    	List<ProductSkuEntity> list = productSkuService.selectProductId(pid);
    	map.put("list",list);
		return R.ok().put("map", map);
    	
    }
}
