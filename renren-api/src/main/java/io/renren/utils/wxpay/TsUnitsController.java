//package io.renren.utils.wxpay;
//
//import com.alibaba.fastjson.JSONException;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import io.renren.dao.WeiXinDaoImpl;
//import io.renren.entity.SysTokenInfoEntity;
//import io.renren.service.SysTokenInfoService;
//import io.renren.utils.OAuthAccessToken;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.text.ParseException;
//import java.util.Date;
//
//public class TsUnitsController {
//    private  Logger logger = LoggerFactory.getLogger(TsUnitsController.class);
//    // 获取access_token接口的地址（GET） 限200（次/天）
//    /**
//     * 获取access_token 字符串
//     * 微信服务器会返回json格式的数据：{"access_token":"ACCESS_TOKEN","expires_in":7200}
//     * @param appid 凭证
//     * @param appsecret 密钥
//     * @return
//     */
//    public  String getAccessTokenToStr(String appid, String appsecret,SysTokenInfoService sysTokenInfoService) {
//        logger.info("getAccessTokenToStr------------");
//        String token="";
//        //查询数据库里面是否有token
//        try {
//            QueryWrapper queryWrapper=new QueryWrapper<SysTokenInfoEntity>();
//            queryWrapper.eq("appid",appid);
//            SysTokenInfoEntity sysTokenInfoEntity=sysTokenInfoService.getOne(queryWrapper);
//            if(sysTokenInfoEntity==null){ //没有数据
//                 sysTokenInfoEntity=getAccessToken(appid, appsecret);
//                sysTokenInfoEntity.setAppid(appid);
//                sysTokenInfoEntity.setUpdateTime(new Date());
//                sysTokenInfoService.save(sysTokenInfoEntity);
//                token=sysTokenInfoEntity.getAccessToken();
//                logger.info("token-----第一次加入-------:"+token);
//            }else{ //有数据
//                long interval=getInterval(sysTokenInfoEntity.getUpdateTime());
//                if(interval>=Integer.valueOf(sysTokenInfoEntity.getExpires())){ //验证时间是否超过7200秒
//                    sysTokenInfoEntity=getAccessToken(appid, appsecret);
//                    sysTokenInfoEntity.setAppid(appid);
//                    sysTokenInfoEntity.setUpdateTime(new Date());
//                    sysTokenInfoService.updateById(sysTokenInfoEntity); //更新数据库
//                    token=sysTokenInfoEntity.getAccessToken();
//                    logger.info("token---过期重新获取---------:"+token);
//                }else{
//                    token=sysTokenInfoEntity.getAccessToken();
//                    logger.info("token------数据库------:"+token);
//                }
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return token;
//    }
//
//
//    public static SysTokenInfoEntity getAccessToken(String appid, String appsecret) throws Exception{
//        SysTokenInfoEntity accessToken = null;
////        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
//        WeiXinDaoImpl weiXinDao=new WeiXinDaoImpl();
////        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
//        OAuthAccessToken accessTokens=weiXinDao.getTokenUrl(appid,appsecret);
//        // 如果请求成功
//        if (null != accessTokens) {
//            try {
//                accessToken = new SysTokenInfoEntity();
//                accessToken.setAccessToken(accessTokens.getAccessToken());
//                accessToken.setExpires(String.valueOf(accessTokens.getExpiresIn()));
//            } catch (JSONException e) {
//                accessToken = null;
//                System.out.println(accessTokens.toString());
//                // 获取token失败
////                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
//            }
//        }
//        return accessToken;
//    }
//
//    /***
//     * 两个时间相隔多少秒
//     * @param
//     * @return interval 返回秒数
//     * @throws ParseException
//     */
//    public static long getInterval(Date beg_date) throws ParseException {
//        Date end_date=new Date();
//        long interval = (end_date.getTime() - beg_date.getTime())/1000;
//        System.out.println("两个时间相差"+interval+"秒");
//        return interval;
//    }
//}
