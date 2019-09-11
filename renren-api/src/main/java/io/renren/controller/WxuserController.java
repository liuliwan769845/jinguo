package io.renren.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.renren.annotation.Login;
import io.renren.annotation.LoginUser;
import io.renren.common.utils.EmptyUtils;
import io.renren.dao.WeiXinDaoImpl;
import io.renren.entity.*;
import io.renren.service.*;
import io.renren.utils.OAuthAccessToken;
import io.renren.utils.wxpay.PayUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.renren.common.utils.R;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 用户表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@RestController
@RequestMapping("/api")
public class WxuserController {
    @Autowired
    private WxuserService wxuserService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private GfeService gfeService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private SignService signService;
    @Autowired
    private GoldRecordService goldRecordService;
    @Autowired
    private InvitationUserService invitationUserService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private OrdersService ordersService;
    @RequestMapping("wxLogin")
    @ApiOperation(value = "获取用户微信", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "用户授权code", required = true),
            @ApiImplicitParam(paramType = "query", name = "name", value = "用户授权姓名", required = true),
            @ApiImplicitParam(paramType = "query", name = "imgUrl", value = "用户授权头像", required = true),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "分享用户ID", required = false)
    })
    public R userLogin(@RequestParam String code,@RequestParam String imgUrl,@RequestParam(required = false) Integer userId,@RequestParam String name)  {
        try {
            System.out.println("我进来了");
         Integer time=   Math.toIntExact((new Date().getTime() / 1000));
            WeiXinDaoImpl weiXinDao=new WeiXinDaoImpl();
            OAuthAccessToken accessToken=weiXinDao.getOAuthAccessToken("wxe95d5d49626acdaf","b4986b88556a5e0f1d26d4c408dd693c",code);
                WxuserEntity wx = wxuserService.getOne(new QueryWrapper<WxuserEntity>().eq("wx_open_id",accessToken.getOpenid()));
            Map<String, Object> map=new HashMap<>();
            if(EmptyUtils.isEmpty(wx)){
                    wx= new WxuserEntity();
                    wx.setWxOpenId( accessToken.getOpenid());
                    wx.setName(name);
                    wx.setImgUrl(imgUrl);
                    wx.setCreateDate(new Date());
                    wx.setGoldenFruitCount(0);
                    wx.setGoldenFruitNum(0);
                    wx.setGoldNum(0.00);
                    wx.setLevel(1);
                    wx.setGoldenFruitTime(time);
                    wxuserService.save(wx);
                    if(EmptyUtils.isNotEmpty(userId)){
                        List<InvitationUserEntity> list = invitationUserService.list(new QueryWrapper<InvitationUserEntity>().eq("DATE(create_date)", new Date()).eq("user_id", userId));
                        if(list.size()<5){
                            InvitationUserEntity invitationUserEntity=new InvitationUserEntity();
                            invitationUserEntity.setCreateDate(new Date());
                            invitationUserEntity.setUserId(wx.getId());
                            invitationUserEntity.setParentId(userId);
                            invitationUserService.save(invitationUserEntity);
                        }
                    }
                    map = wxuserService.login(wx);
                }else{
                    map = wxuserService.login(wx);
                }
            if(EmptyUtils.isNotEmpty(userId)){
                WxuserEntity user = wxuserService.getById(userId);
                List<LevelEntity> list = levelService.list(new QueryWrapper<LevelEntity>().le("level_num", user.getLevel()).eq("is_worker", 0));
                Integer kynum=0;//可邀请数量
                for(LevelEntity levelEntity:list){
                    kynum+=levelEntity.getWorkerCount();
                }
                List<WorkerEntity> WorkerList = workerService.list(new QueryWrapper<WorkerEntity>().eq("parent_Id", user.getId()).eq("state",0));
                Integer ycz=0;//可邀请数量
                for(WorkerEntity w:WorkerList){
                    if(time-w.getPartTime()<w.getSyTime()){
                        ycz++;
                    }
                }
                if(kynum>ycz){
                    WorkerEntity workerEntity=new WorkerEntity();
                    workerEntity.setState(0);
                    workerEntity.setParentId(userId);
                    workerEntity.setUserId(wx.getId());
                    workerEntity.setPartTime(Math.toIntExact(new Date().getTime() / 1000));
                    workerEntity.setSyTime(1800);
                    workerEntity.setCreateDate(new Date());
                    workerService.save(workerEntity);
                }
             }

            return R.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("我进来了2205");
            return R.error(205,"code不正确");
        }
//        WxuserEntity wx = wxuserService.getById(1);
//        Map<String, Object> map = wxuserService.login(wx);
//        return R.ok(map);
    }




    @Login
    @GetMapping("userInfo")
    @ApiOperation(value="获取用户信息", response= WxuserEntity.class)
    public R userInfo(@ApiIgnore @LoginUser WxuserEntity user){
        return R.ok().put("user", user);
    }
    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public R userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId){
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken(){
        return R.ok().put("msg", "无需token也能访问。。。");
    }
    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    public R logout(@ApiIgnore @RequestAttribute("userId") long userId){
        tokenService.expireToken(userId);
        return R.ok();
    }
    @Login
    @PostMapping("home")
    @ApiOperation("首页")
    public R logout(@ApiIgnore @LoginUser WxuserEntity user){
        Integer grbf=1;
        List<WorkerEntity> WorkerList = workerService.list(new QueryWrapper<WorkerEntity>().eq("parent_Id", user.getId()).gt("sy_time",0).eq("state",0));
        LevelEntity level = levelService.getOne(new QueryWrapper<LevelEntity>().eq("level_num", user.getLevel()));
        LevelEntity one = levelService.getOne(new QueryWrapper<LevelEntity>().le("level_num", user.getLevel() + 1));
        List<Map<String,Object>> wxuserEntities =new ArrayList<>();
        Integer bf=0;//升级百分比
        if(EmptyUtils.isEmpty(one)){
            bf=100;
        }else{
            bf= (user.getGoldenSjNum()*100)/one.getExp();
        }
        Integer time = Math.toIntExact(new Date().getTime() / 1000);
        Integer jgnum=(time-user.getGoldenFruitTime())*(level.getYield());//金果数量
            if (WorkerList!=null&&WorkerList.size()>0){
                for(WorkerEntity w:WorkerList){
                    Map<String,Object> map=new HashMap<>();
                    Integer  s1=time-w.getPartTime();

                    if(s1>=1800){
                        jgnum+=w.getSyTime()*level.getYield();
                        w.setState(1);
                        workerService.updateById(w);
                    }else {
                        if(s1>=w.getSyTime()){
                            jgnum+=w.getSyTime()*level.getYield();
                            w.setState(1);
                            workerService.updateById(w);
                        }else{
                            jgnum+=s1*level.getYield();
                            WxuserEntity byId = wxuserService.getById(w.getId());
                            map.put("syTime",w.getSyTime()-s1);
                            map.put("id",w.getId());
                            map.put("name",byId.getName());
                            map.put("imgUrl",byId.getImgUrl());
                            wxuserEntities.add(map);
                        }
                    }

                }
            }
        if(jgnum>=level.getYieldCount()){
            jgnum=level.getYieldCount();
        }
        List<LevelEntity> list = levelService.list(new QueryWrapper<LevelEntity>().le("level_num", user.getLevel()).eq("is_worker", 0));
        List<LevelEntity> lists = levelService.list(new QueryWrapper<LevelEntity>().gt("level_num", user.getLevel()).eq("is_worker", 0));//解锁等级
        Integer kynum=0;//可邀请数量
        for(LevelEntity levelEntity:list){
            kynum+=levelEntity.getWorkerCount();
          }
        Integer bsnum=WorkerList.size()*grbf+1;//倍数
        return R.ok().put("user",user).put("jd",bf).put("level",user.getLevel()).put("jgnum",jgnum).put("kynum",kynum).put("yynum",WorkerList.size()).put("wxuserList",wxuserEntities).put("levelList",lists).put("bsnum",bsnum).put("jgTop",level.getYieldCount());
    }



    @Login
    @PostMapping("MyGoldKl")
    @ApiOperation("我的金果")
    public R MyGoldKl(@ApiIgnore @LoginUser WxuserEntity user) throws ParseException {
        Date start =new Date(getStartTime());
        Date end =new Date(getEndTime());
        List<GfeEntity> shEntityList = gfeService.list(new QueryWrapper<GfeEntity>().le("create_date", end).ge("create_date", start).eq("type", 0));
        Integer sh=0;//收获总数量
        for(GfeEntity gfeEntity:shEntityList){
            sh+=gfeEntity.getNum();
        }
        List<GfeEntity> dhEntityList = gfeService.list(new QueryWrapper<GfeEntity>().le("create_date", end).ge("create_date", start).eq("type", 1));
        Integer dh=0;//兑换总数量
        for(GfeEntity gfeEntity:dhEntityList){
            dh+=gfeEntity.getNum();
        }
        Double s= Double.valueOf(dh)/Double.valueOf(sh);
        if(dh==0||sh==0){
            s=100.00;
        }
        DecimalFormat df   =new   DecimalFormat("#.00");
        s= Double.valueOf(df.format(s));
        Double jgzz=Double.valueOf(user.getGoldenFruitNum())/s;
        jgzz= Double.valueOf(df.format(jgzz));
         List<Map<String,Object>> list=new ArrayList<>();
        for(int i=7;i>0;i--){
            Map<String,Object> map=new HashMap<>();
            List<GfeEntity> shEntityList1 = gfeService.list(new QueryWrapper<GfeEntity>().eq("Date(create_date)", getStatetime(i)).eq("type", 0));
            Integer shs=0;//收获总数量
            for(GfeEntity gfeEntity:shEntityList){
                sh+=gfeEntity.getNum();
            }
            List<GfeEntity> dhEntityList1 = gfeService.list(new QueryWrapper<GfeEntity>().eq("Date(create_date)", getStatetime(i)).eq("type", 1));
            Integer dhs=0;//兑换总数量
            for(GfeEntity gfeEntity:dhEntityList){
                dh+=gfeEntity.getNum();
            }
            Double s1= Double.valueOf(dh)/Double.valueOf(sh);
            if(dh==0||sh==0){
                s1=100.00;
            }
            DecimalFormat df1  =new   DecimalFormat("#.00");
            s1= Double.valueOf(df1.format(s1));
            map.put("date",getStatetime(i));
            map.put("unit",s1);
            list.add(map);
        }
        return R.ok().put("jgzz",jgzz).put("dqunit",s).put("userMoney",user.getGoldNum()).put("dqgold",user.getGoldenFruitNum()).put("zxlist",list);
    }


    @Login
    @PostMapping("doWork")
    @ApiOperation("打工完成")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "wxuserList数组得id", required = true)
    })
    public R doWork(@ApiIgnore @LoginUser WxuserEntity user,@RequestParam String id){
        WorkerEntity byId = workerService.getById(id);
        byId.setState(1);
        workerService.updateById(byId);
        return R.ok();
    }


    @Login
    @PostMapping("pendingOrder")
    @ApiOperation("挂单交易记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "state", value = "0进行中 1已完成", required = true)
    })
    public R pendingOrder(@ApiIgnore @LoginUser WxuserEntity user,@RequestParam String state){
        List<GfeEntity> dhEntityList = gfeService.list(new QueryWrapper<GfeEntity>().eq("type", 1).eq("user_id",user.getId()).eq("state",state).orderByDesc("create_date"));
        return R.ok().put("dhEntityList",dhEntityList);
    }

    @Login
    @PostMapping("jzzx")
    @ApiOperation("价值走向")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "state", value = "0进行中 1已完成", required = true)
//    })
    public R jzzx(@ApiIgnore @LoginUser WxuserEntity user){
        List<GfeEntity> dhEntityList = gfeService.list(new QueryWrapper<GfeEntity>().eq("type", 1).eq("state",1).orderByDesc("create_date"));
        return R.ok().put("dhEntityList",dhEntityList);
    }

    @Login
    @PostMapping("sqjg")
    @ApiOperation("收取金果")
    public R sqjg(@ApiIgnore @LoginUser WxuserEntity user){
        List<WorkerEntity> WorkerList = workerService.list(new QueryWrapper<WorkerEntity>().eq("parent_Id", user.getId()));
        LevelEntity level = levelService.getOne(new QueryWrapper<LevelEntity>().eq("level_num", user.getLevel()));
        LevelEntity one = levelService.getOne(new QueryWrapper<LevelEntity>().le("level_num", user.getLevel() + 1));
        Integer time = Math.toIntExact(new Date().getTime() / 1000);
        Integer jgnum=(time-user.getGoldenFruitTime())*(level.getYield());//金果数量
        if (WorkerList.size()>0){
            for(WorkerEntity w:WorkerList){
                if(w.getState()==1){
                    jgnum+=1800*level.getYield();
                    workerService.removeById(w.getId());
                }else{
                    Integer  dgsj=time-w.getPartTime();
                    if(dgsj>=1800){
                        jgnum+=w.getSyTime()*level.getYield();
                        workerService.removeById(w.getId());
                    }else{
                        if(dgsj>=w.getSyTime()){
                            jgnum+=w.getSyTime()*level.getYield();
                            workerService.removeById(w.getId());
                        }else{
                            jgnum+=dgsj*level.getYield();
                            Integer sysj=w.getSyTime()-dgsj;
                            w.setSyTime(sysj);
                            w.setPartTime(time);
                            workerService.updateById(w);
                        }

                    }
                }
            }
        }
        if(jgnum>=level.getYieldCount()){
            jgnum=level.getYieldCount();
        }
        user.setGoldenFruitCount(user.getGoldenFruitCount()+jgnum);
        user.setGoldenFruitNum(user.getGoldenFruitNum()+jgnum);
        if(one!=null){
            if(user.getGoldenSjNum()+jgnum>=one.getExp()){
                user.setLevel(user.getLevel()+1);
                user.setGoldenSjNum(user.getGoldenSjNum()+jgnum-one.getExp());
                if(user.getLevel()+1==6){
                    InvitationUserEntity user_id = invitationUserService.getOne(new QueryWrapper<InvitationUserEntity>().eq("user_id", user.getId()));
                    if(user_id!=null){
                        WxuserEntity byId = wxuserService.getById(user_id.getParentId());
                        byId.setGoldNum(byId.getGoldNum()+60000);
                        wxuserService.updateById(byId);
                        user.setGoldNum(byId.getGoldNum()+60000);
                        GoldRecordEntity goldRecordEntity=new GoldRecordEntity();
                        goldRecordEntity.setCreateDate(new Date());
                        goldRecordEntity.setName("金币任务获取成功");
                        goldRecordEntity.setNum(60000.00);
                        goldRecordEntity.setUserId(byId.getId());
                        goldRecordEntity.setType(0);
                        goldRecordService.save(goldRecordEntity);
                        GoldRecordEntity goldRecordEntity1=new GoldRecordEntity();
                        goldRecordEntity1.setCreateDate(new Date());
                        goldRecordEntity1.setName("金币任务获取成功");
                        goldRecordEntity1.setNum(60000.00);
                        goldRecordEntity1.setUserId(user.getId());
                        goldRecordEntity1.setType(0);
                        goldRecordService.save(goldRecordEntity1);
                    }
                }
            }else{
                user.setGoldenSjNum(user.getGoldenSjNum()+jgnum);
            }
        }else{
            user.setGoldenSjNum(user.getGoldenSjNum()+jgnum);
        }
        user.setGoldenFruitTime(time);
        wxuserService.updateById(user);
        GfeEntity gfeEntity=new GfeEntity();
        gfeEntity.setCreateDate(new Date());
        gfeEntity.setName("收取金果");
        gfeEntity.setNum(jgnum);
        gfeEntity.setType(0);
        gfeEntity.setUserId(user.getId());
        gfeEntity.setCountPrice(BigDecimal.valueOf(0));
        gfeEntity.setUnitPrice(BigDecimal.valueOf(0));
        gfeService.save(gfeEntity);
        return R.ok();
    }


    @Login
    @PostMapping("savaPendingOrder")
    @ApiOperation("新增挂单交易")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "num", value = "数量", required = true)
    })
    public R pendingOrder(@ApiIgnore @LoginUser WxuserEntity user,@RequestParam Integer num){
        if(user.getGoldenFruitNum()<num){
            return R.error(208,"金果数量不足");
        }
        Date start =new Date(getStartTime());
        Date end =new Date(getEndTime());
        List<GfeEntity> shEntityList = gfeService.list(new QueryWrapper<GfeEntity>().le("create_date", end).ge("create_date", start).eq("type", 0));
        Integer sh=0;//收获总数量
        for(GfeEntity gfeEntity:shEntityList){
            sh+=gfeEntity.getNum();
        }
        List<GfeEntity> dhEntityList = gfeService.list(new QueryWrapper<GfeEntity>().le("create_date", end).ge("create_date", start).eq("type", 1));
        Integer dh=0;//兑换总数量
        for(GfeEntity gfeEntity:dhEntityList){
            dh+=gfeEntity.getNum();
        }
        Double s= Double.valueOf(dh)/Double.valueOf(sh);
        if(dh==0||sh==0){
            s=100.00;
        }
        DecimalFormat df   =new   DecimalFormat("#.00");
        s= Double.valueOf(df.format(s));
        GfeEntity gfeEntity=new GfeEntity();
        gfeEntity.setState(0);
        gfeEntity.setType(1);
        gfeEntity.setUnitPrice(BigDecimal.valueOf(s));
        gfeEntity.setNum(num);
        gfeEntity.setUserId(user.getId());
        gfeEntity.setCountPrice(BigDecimal.valueOf((num/s)));
        gfeEntity.setCreateDate(new Date());
        gfeEntity.setName("挂单交易");
        gfeService.save(gfeEntity);
        user.setGoldenFruitNum(user.getGoldenFruitNum()-num);
        wxuserService.updateById(user);
        return R.ok();
    }


    /**
     * 新增用户收货地址
     * @param map
     * @return
     */
    @Login
    @RequestMapping("/addUserAddress")
    @ApiOperation("新增收获地址")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "map", value = "对象", required = true)
    })
    public R addUserAddress(@ApiIgnore @LoginUser WxuserEntity user,@RequestParam  String  map){
        Map<String,Object> maps= (Map<String, Object>) JSONObject.parse(map);
        Object name = maps.get("name"); //收货人姓名
        Object province = maps.get("province"); //省
        if(!EmptyUtils.isNotEmpty(province)) return R.error(201,"没有填写省");
        Object city = maps.get("city"); //市
        if(!EmptyUtils.isNotEmpty(city)) return R.error(201,"没有填写市");
        Object address = maps.get("address"); //详细地址
        if(!EmptyUtils.isNotEmpty(address)) return R.error(201,"没有填写详细收货地址");
        Object mobile = maps.get("mobile"); //手机号码
        if(!EmptyUtils.isNotEmpty(mobile)) return R.error(201,"没有填写收货地址");
        Object is_default = maps.get("is_default");
        Object area = maps.get("area");
        Object id = maps.get("id");
        if(!EmptyUtils.isNotEmpty(area)) return R.error(201,"没有填写地区");
        if(EmptyUtils.isNotEmpty(is_default) && "1".equals(is_default.toString().trim())){
            //清空掉以前所有的默认地址
            List<UserAddressEntity> list = userAddressService.list(
                    new QueryWrapper<UserAddressEntity>()
                            .eq("user_id", user.getId())
                            .eq("type", 0)
            );
            if(list!=null && list.size()>0){
                for(UserAddressEntity r:list){
                    r.setType(1);
                    userAddressService.updateById(r);
                }
            }
        }
        UserAddressEntity resultsAddressEntity = new UserAddressEntity();
        if(id!=null&&!id.equals("")){
            resultsAddressEntity=userAddressService.getById((Integer)id);
        }
        resultsAddressEntity.setUserId(user.getId());
        resultsAddressEntity.setName(name.toString().trim());
        resultsAddressEntity.setProvince(province.toString().trim());
        resultsAddressEntity.setCity(city.toString().trim());
        resultsAddressEntity.setAddress(address.toString().trim());
        resultsAddressEntity.setMobile(mobile.toString().trim());
        resultsAddressEntity.setCreateDate(new Date());
        resultsAddressEntity.setArea(area.toString());
        if(EmptyUtils.isNotEmpty(is_default))
            resultsAddressEntity.setType(Integer.valueOf(is_default.toString().trim()));
        else
            resultsAddressEntity.setType(1);
        if(id!=null&&!id.equals("")){
            userAddressService.updateById(resultsAddressEntity);
        }else{
            userAddressService.save(resultsAddressEntity);
        }
        return R.ok();
    }

    @Login
    @PostMapping("isSign")
    @ApiOperation("是否签到(0 未签到  1待领取 2已领取)")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "id", value = "wxuserList数组得id", required = true)
//    })
    public R isSign(@ApiIgnore @LoginUser WxuserEntity user){
        //我要获取当前的日期
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取String类型的时间
        String createdate = sdf.format(date);
        SignEntity one = signService.getOne(new QueryWrapper<SignEntity>().eq("DATE(create_date)", createdate).eq("user_id",user.getId()));
        Integer state=0;
        if(one!=null){
            if(one.getState()==0){
                state=1;
            }else{
                state=2;
            }
        }
        return R.ok().put("state",state);
    }
    @Login
    @PostMapping("saveSign")
    @ApiOperation("签到")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "id", value = "wxuserList数组得id", required = true)
//    })
    public R saveSign(@ApiIgnore @LoginUser WxuserEntity user){
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取String类型的时间
        String createdate = sdf.format(date);
        SignEntity one = signService.getOne(new QueryWrapper<SignEntity>().eq("DATE(create_date)",createdate).eq("user_id",user.getId()));
        if(one!=null){
            return R.error(209,"今天已签到");
        }else{
             one=new SignEntity();
            one.setState(0);
            one.setCreateDate(new Date());
            one.setUserId(user.getId());
            signService.save(one);
        }
        return R.ok();
    }
    @Login
    @PostMapping("receiveSign")
    @ApiOperation("签到奖励领取")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "id", value = "wxuserList数组得id", required = true)
//    })
    public R receiveSign(@ApiIgnore @LoginUser WxuserEntity user){
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取String类型的时间
        String createdate = sdf.format(date);
        SignEntity one = signService.getOne(new QueryWrapper<SignEntity>().eq("DATE(create_date)",createdate).eq("user_id",user.getId()));
        if(one==null){
            return R.error(208,"今天还未签到");
        }else{
            if(one.getState()==1){
                return R.error(208,"请勿重复领取");
            }
            user.setGoldNum(user.getGoldNum()+300);
            GoldRecordEntity goldRecordEntity=new GoldRecordEntity();
            goldRecordEntity.setCreateDate(new Date());
            goldRecordEntity.setName("签到奖励金币领取成功");
            goldRecordEntity.setNum(300.00);
            goldRecordEntity.setUserId(user.getId());
            goldRecordEntity.setType(0);
            wxuserService.updateById(user);
            one.setState(1);
            signService.updateById(one);
            goldRecordService.save(goldRecordEntity);
        }
        return R.ok();
    }


    @Login
    @PostMapping("addressList")
    @ApiOperation("我的收获地址")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "id", value = "wxuserList数组得id", required = true)
//    })
    public R addressList(@ApiIgnore @LoginUser WxuserEntity user){
        List<UserAddressEntity> list = userAddressService.list(new QueryWrapper<UserAddressEntity>().eq("user_id", user.getId()));
        return R.ok();
    }
    @Login
    @PostMapping("couponProduct")
    @ApiOperation("商品可使用得优惠卷")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true)
    })
    public R couponProduct(@ApiIgnore @LoginUser WxuserEntity user,@RequestParam Integer productId){
        ProductEntity p = productService.getById(productId);
        if(EmptyUtils.isEmpty(p)){
            return R.error(207,"该商品已经下架");
        }
        List<UserCouponEntity> list = userCouponService.list(new QueryWrapper<UserCouponEntity>().eq("user_id", user.getId()).eq("state", 0).lt("expiration_time", new Date()));
        List<CouponEntity> list1=new ArrayList<>();
        for(UserCouponEntity u:list){
            CouponEntity couponEntity = couponService.getById(u.getCouponId());
             if(EmptyUtils.isEmpty(couponEntity.getBrandId())){
                 couponEntity.setUid(u.getId());
                 list1.add(couponEntity);
             }else{
                 if(p.getBrandId().equals(couponEntity.getBrandId())){
                     couponEntity.setUid(u.getId());
                     list1.add(couponEntity);
                 }
             }
        }
        return R.ok().put("couponList",list1);
    }
    @Login
    @PostMapping("saveOrders")
    @ApiOperation("生成订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true),
            @ApiImplicitParam(paramType = "query", name = "userCouponId", value = "用户优惠卷ID", required = true),
            @ApiImplicitParam(paramType = "query", name = "addressId", value = "用户收获地址ID", required = true),
    })
    public R saveOrders(HttpServletRequest request, HttpServletResponse response, @ApiIgnore @LoginUser WxuserEntity user, @RequestParam Integer productId, @RequestParam Integer userCouponId, @RequestParam Integer addressId){
        ProductEntity p = productService.getById(productId);
        UserAddressEntity userAddressEntity = userAddressService.getById(addressId);
        if(EmptyUtils.isEmpty(p)){
            return R.error(207,"该商品已经下架");
        }
        if(EmptyUtils.isEmpty(userAddressEntity)){
            return R.error(207,"收获地址不存在，请检查");
        }
        UserCouponEntity one = userCouponService.getById(userCouponId);
        if(EmptyUtils.isEmpty(one)){
            return R.error(207,"该优惠卷不存在，请检查");
        }else{
            if(one.getState().equals(1)){
                return R.error(207,"该优惠卷已使用");
            }
            if(new Date().getTime()>one.getExpirationTime().getTime()){
                return R.error(207,"该优惠卷已过期");
            }
        }
        CouponEntity couponEntity = couponService.getById(one.getCouponId());
        if(new Date().getTime()>couponEntity.getExpirationTime().getTime()){
            return R.error(207,"该优惠卷已过期");
        }
        if(EmptyUtils.isNotEmpty(couponEntity.getBrandId())){
            if(!couponEntity.getBrandId().equals(p.getBrandId())){
                return R.error(207,"该优惠卷对应商品品牌不同，无法使用");
            }
        }
        OrdersEntity ordersEntity=new OrdersEntity();
        ordersEntity.setSn(PayUtil.getTradeNo());
        ordersEntity.setUserId(user.getId());
        ordersEntity.setState(0);
        ordersEntity.setCreateDate(new Date());
        ordersEntity.setMailPrice(BigDecimal.valueOf(0));
        ordersEntity.setProductName(p.getName());
        ordersEntity.setProductId(productId);
        ordersEntity.setProductPrice(p.getOriginalPrice());
        ordersEntity.setPrice(p.getPrice());
        ordersEntity.setProductGold(p.getGold());
        ordersEntity.setProvince(userAddressEntity.getProvince());
        ordersEntity.setAddress(userAddressEntity.getAddress());
        ordersEntity.setArea(userAddressEntity.getArea());
        ordersEntity.setCity(userAddressEntity.getCity());
        ordersEntity.setUserMobile(userAddressEntity.getMobile());
        ordersEntity.setUsername(userAddressEntity.getName());
        ordersEntity.setCouponId(couponEntity.getId());
        ordersEntity.setTotlePrice(p.getPrice());
        ordersEntity.setGold(p.getGold());
        if(couponEntity.getType()==0){
            ordersEntity.setTotlePrice(p.getPrice().multiply(BigDecimal.valueOf(couponEntity.getRebateNum())).divide(BigDecimal.valueOf(100)));
            ordersEntity.setGold(p.getGold());
            if(user.getGoldNum()-p.getGold()<0){
                return R.error(207,"金币余额不足,无法兑换");
            }
//            user.setGoldNum(user.getGoldNum()-p.getGold());
        }
        if(couponEntity.getType()==1){
            ordersEntity.setTotlePrice(p.getPrice().subtract(couponEntity.getPrice()));
            ordersEntity.setGold(p.getGold());
            if(user.getGoldNum()-p.getGold()<0){
                return R.error(207,"金币余额不足,无法兑换");
            }
//            user.setGoldNum(user.getGoldNum()-p.getGold());
        }
        if(couponEntity.getType()==2){
            ordersEntity.setTotlePrice(BigDecimal.valueOf(0));
            ordersEntity.setGold(0.00);
            ordersEntity.setState(1);
            one.setState(1);
            userCouponService.updateById(one);
        }
        ordersService.save(ordersEntity);
        PayController payController=new PayController();
        if(couponEntity.getType()==2){
            return R.ok();
        }
        return payController.orderPay(request,response,ordersEntity,user);
    }






    /**
     * 获取今天开始时间
     */
    private Long getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    /**
     * 获取今天结束时间
     */
    private Long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    /**
     * 获取当前时间前7天的日期
     */
    public String getStatetime(Integer s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - s);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }


}
