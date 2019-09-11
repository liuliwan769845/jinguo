package io.renren.controller;

import com.alibaba.fastjson.JSON;
import io.renren.common.utils.R;
import io.renren.entity.OrdersEntity;
import io.renren.entity.WxuserEntity;
import io.renren.service.OrdersService;
import io.renren.service.WxuserService;
import io.renren.utils.model.JsonResult;
import io.renren.utils.model.ResponseData;
import io.renren.utils.wxpay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建时间：2016年11月2日 下午4:16:32
 *
 * @author andy
 * @version 2.2
 */
@RestController
@RequestMapping("/order")
public class PayController {
    @Autowired
    private WxuserService wxuserService;
    @Autowired
    private OrdersService ordersService;

    private static final String ORDER_PAY = "https://api.mch.weixin.qq.com/pay/unifiedorder"; // 统一下单

    private static final String ORDER_PAY_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery"; // 支付订单查询

    private static final String ORDER_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund"; // 申请退款

    private static final String ORDER_REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery"; // 申请退款

    private static final String APP_ID = ConfigUtil.getProperty("wx.appid");

    private static final String MCH_ID = ConfigUtil.getProperty("wx.mchid");

    private static final String API_SECRET = ConfigUtil.getProperty("wx.api.secret");
    private static final String URL ="http://wx.whimage.cn:8081";

    /**
     * 支付下订单
     * @param request
     * @param response
     * 支付金额
     * 商品id
     *
     */
    public R orderPay(HttpServletRequest request, HttpServletResponse response, OrdersEntity ordersEntity, WxuserEntity wxuserEntity) {

//        if (!"001".equals(id)) {
//            WebUtil.response(response, WebUtil.packJsonp(callback, JSON
//                    .toJSONString(new JsonResult(-1, "商品不存在", new ResponseData()), SerializerFeatureUtil.FEATURES)));
//        }
        Map<String, String> restmap = null;
        Map<String, String> body = new HashMap<>();
        body.put("id", String.valueOf(ordersEntity.getId()));
        body.put("userId", String.valueOf(wxuserEntity.getId()));
        boolean flag = true; // 是否订单创建成功
        try {
            System.out.println("***********************************");
            String total_fee =ordersEntity.getTotlePrice().multiply(BigDecimal.valueOf(100))
                    .setScale(0, BigDecimal.ROUND_HALF_UP).toString();
            System.out.println(total_fee);
            Map<String, String> parm = new HashMap<String, String>();
            parm.put("appid", APP_ID);
            parm.put("mch_id", MCH_ID);
            parm.put("device_info", "WEB");
            parm.put("nonce_str", PayUtil.getNonceStr());
            parm.put("body","金果-商品兑换");
            parm.put("attach", URLEncoder.encode((String) body.toString()));
            parm.put("out_trade_no", PayUtil.getTradeNo());
            parm.put("total_fee", total_fee);
            parm.put("spbill_create_ip", PayUtil.getRemoteAddrIp(request));
            parm.put("notify_url", URL+"/order/pay/notify");
            parm.put("trade_type", "JSAPI");
            parm.put("sign_type", "MD5");
            parm.put("openid", wxuserEntity.getWxOpenId());
            parm.put("sign", PayUtil.getSign(parm, API_SECRET));
            String restxml = HttpUtils.post(ORDER_PAY, XmlUtil.xmlFormat(parm, false));
            System.out.println("************************************************");
            System.out.println(restxml);
            restmap = XmlUtil.doXMLParse(restxml);
        } catch (Exception e) {
            e.getMessage();
        }
        Map<String, String> payMap = new HashMap<String, String>();
        if (CollectionUtil.isNotEmpty(restmap) && "SUCCESS".equals(restmap.get("result_code"))) {
            System.out.println("************************************************");
            System.out.println(restmap.get("result_code"));
            payMap.put("appId", APP_ID);
//            payMap.put("partnerid", MCH_ID);
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id="+restmap.get("prepay_id"));
            System.out.println(payMap.get("package"));
            payMap.put("nonceStr", PayUtil.getNonceStr());
            System.out.println(payMap.get("nonceStr"));
            payMap.put("timeStamp", PayUtil.payTimestamp());
            System.out.println(payMap.get("timeStamp"));
            System.out.println(restmap.toString());
            try {
                payMap.put("sign", PayUtil.getSign(payMap, API_SECRET));
            } catch (Exception e) {
                flag = false;
            }
        }
        if (flag) {
            System.out.println("************************************************");
            System.out.println(payMap);
                    return R.ok("订单获取成功").put("payMap",payMap);
        } else {
            if (CollectionUtil.isNotEmpty(restmap)) {
                System.out.println("订单创建失败：" + restmap.get("err_code") + ":" + restmap.get("err_code_des"));
                return R.error(210,"订单创建失败：" + restmap.get("err_code") + ":" + restmap.get("err_code_des"));
            }
//            WebUtil.response(response, WebUtil.packJsonp("", JSON
//                    .toJSONString(new JsonResult(-1, "订单获取失败", new ResponseData()), SerializerFeatureUtil.FEATURES)));
            return R.error(-1,"订单获取失败：");
        }
    }


    /**
     * 查询支付结果
     *
     * @param request
     * @param response
     * @param tradeid 微信交易订单号
     * @param tradeno 商品订单号
     * @param callback
     */
    @RequestMapping(value = "/pay/query", method = RequestMethod.POST)
    public void orderPayQuery(HttpServletRequest request, HttpServletResponse response, String tradeid, String tradeno,
                              String callback) {
        System.out.println("[/order/pay/query]");
        if (StringUtil.isEmpty(tradeno) && StringUtil.isEmpty(tradeid)) {
            WebUtil.response(response, WebUtil.packJsonp(callback, JSON
                    .toJSONString(new JsonResult(-1, "订单号不能为空", new ResponseData()), SerializerFeatureUtil.FEATURES)));
        }

        Map<String, String> restmap = null;
        try {
            Map<String, String> parm = new HashMap<String, String>();
            parm.put("appid", APP_ID);
            parm.put("mch_id", MCH_ID);
            parm.put("transaction_id", tradeid);
            parm.put("out_trade_no", tradeno);
            parm.put("nonce_str", PayUtil.getNonceStr());
            parm.put("sign", PayUtil.getSign(parm, API_SECRET));

            String restxml = HttpUtils.post(ORDER_PAY_QUERY, XmlUtil.xmlFormat(parm, false));
            restmap = XmlUtil.xmlParse(restxml);
        } catch (Exception e) {
            System.out.println(e.getMessage()+ e);
        }

        if (CollectionUtil.isNotEmpty(restmap) && "SUCCESS".equals(restmap.get("result_code"))) {
            // 订单查询成功 处理业务逻辑
            System.out.println("订单查询：订单" + restmap.get("out_trade_no") + "支付成功");
            WebUtil.response(response, WebUtil.packJsonp(callback, JSON
                    .toJSONString(new JsonResult(1, "订单支付成功", new ResponseData()), SerializerFeatureUtil.FEATURES)));
        } else {
            if (CollectionUtil.isNotEmpty(restmap)) {
                System.out.println("订单支付失败：" + restmap.get("err_code") + ":" + restmap.get("err_code_des"));
            }
            WebUtil.response(response, WebUtil.packJsonp(callback, JSON
                    .toJSONString(new JsonResult(-1, "订单支付失败", new ResponseData()), SerializerFeatureUtil.FEATURES)));
        }
    }


    /**
     * 订单支付微信服务器异步通知
     *
     * @param request
     * @param response
     */
    @RequestMapping("/pay/notify")
    public void orderPayNotify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[/order/pay/notify]");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        try {
            ServletInputStream in = request.getInputStream();
            String resxml = FileUtil.readInputStream2String(in);
            Map<String, String> restmap = XmlUtil.doXMLParse(resxml);
            System.out.println("支付结果通知：" + restmap);
            if ("SUCCESS".equals(restmap.get("result_code"))) {
                // 订单支付成功 业务处理
                String out_trade_no = restmap.get("out_trade_no"); // 商户订单号
                // 通过商户订单判断是否该订单已经处理 如果处理跳过 如果未处理先校验sign签名 再进行订单业务相关的处理
                String sing = restmap.get("sign"); // 返回的签名
                restmap.remove("sign");
                String signnow = PayUtil.getSign(restmap, API_SECRET);
                if (signnow.equals(sing)) {
                    // 进行业务处理
                    System.out.println("订单支付通知： 支付成功，订单号" + out_trade_no);
                    System.out.println("********************************************************************");
                    System.out.println("********************************************************************");
                    // 处理成功后相应给响应xml
                    Map<String, String> respMap = new HashMap<>();
                    respMap = new HashMap<String, String>();
                    respMap.put("return_code", "SUCCESS"); //相应给微信服务器
                    respMap.put("return_msg", "OK");
                    String resXml = XmlUtil.xmlFormat(restmap, true);
                    response.getWriter().write(resXml);
                    System.out.println(restmap);
                    String keyWord = URLDecoder.decode(restmap.get("attach"), "UTF-8");
                    System.out.println(keyWord);
                    String s=keyWord.substring(1,keyWord.length()-1);
                    System.out.println(s);
                    String [] map=s.split(", ");
                    String userId = "";
                    String id = "";
                    for (int i = 0; i < map.length; i++) {
                        if(map[i].split("=")[0].equals("id")){
                            id =map[i].split("=")[1];
                        }
                        if(map[i].split("=")[0].equals("userId")){
                            userId=map[i].split("=")[1];
                        }
                    }
                    WxuserEntity wxuserEntity = wxuserService.getById(Integer.valueOf(userId));
                    OrdersEntity ordersEntity= ordersService.getById(Integer.valueOf(id));
                    ordersEntity.setState(1);
                    ordersEntity.setWxSn(out_trade_no);
                    ordersEntity.setPaymentDate(new Date());
                    wxuserEntity.setGoldNum(wxuserEntity.getGoldNum()-ordersEntity.getGold());
                    ordersService.updateById(ordersEntity);
                    wxuserService.updateById(wxuserEntity);
                  } else {
                    System.out.println("订单支付通知：签名错误");
                }
            } else {
                System.out.println("订单支付通知：支付失败，" + restmap.get("err_code") + ":" + restmap.get("err_code_des"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()+e);
        }
    }

}