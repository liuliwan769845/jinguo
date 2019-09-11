package io.renren.dao;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.utils.HttpClientConnectionManager;
import io.renren.utils.OAuthAccessToken;
import io.renren.utils.UserEntity;
import io.renren.utils.useValue;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.logging.Logger;

//import org.apache.log4j.Logger;


public class WeiXinDaoImpl implements WeiXinDao{
	public static DefaultHttpClient httpclient;
	private static Logger log= Logger.getLogger(WeiXinDaoImpl.class.getName());
	static {
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端
	}
	/**
	 * 微信OAuth2.0授权（目前微信只支持在微信客户端发送连接，实现授权）
	 * */
	public String getCodeUrl(String appid, String redirect_uri,String scope,String state) throws Exception {
		redirect_uri = URLEncoder.encode(redirect_uri, "utf-8");
		String getCodeUrl= useValue.getCodeUrl.replace("APPID", appid).replace("REDIRECT_URI", redirect_uri).replace("SCOPE", scope).replace("STATE", state);
		log.info("getCodeUrl Value:"+getCodeUrl);
		return getCodeUrl;
	}
	/**
	 * 微信OAuth2.0授权（目前微信只支持在微信客户端发送连接，实现授权）
	 * */
	public OAuthAccessToken getTokenUrl(String appid, String appsecret) throws Exception {
		appsecret = URLEncoder.encode(appsecret, "utf-8");
		String getTokenUrl= useValue.getTokenUrl.replace("APPID", appid).replace("APPSECRET", appsecret);
		log.info("getTokenUrl Value:"+getTokenUrl);
		HttpPost get = HttpClientConnectionManager.getPostMethod(getTokenUrl);
		HttpResponse response = httpclient.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		log.info("jsonObject Value:"+jsonObject);
		OAuthAccessToken accessToken=new OAuthAccessToken();
		accessToken.setAccessToken(jsonObject.getString("access_token"));
		accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
		accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
		accessToken.setOpenid(jsonObject.getString("openid"));
		accessToken.setScope(jsonObject.getString("scope"));
		return accessToken;
	}
	/**
	 * 微信OAuth2.0授权-获取accessToken(这里通过code换取的网页授权access_token,与基础支持中的access_token不同）
	 */
	public OAuthAccessToken getOAuthAccessToken(String appid, String secret, String code) throws Exception {
		String getOAuthAccessToken= useValue.getOAuthAccessToken.replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);
		log.info("getOAuthAccessToken Value:"+getOAuthAccessToken);
		HttpGet get = HttpClientConnectionManager.getGetMethod(getOAuthAccessToken);
		HttpResponse response = httpclient.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		log.info("jsonObject Value:"+jsonObject);
		OAuthAccessToken accessToken=new OAuthAccessToken();
		accessToken.setAccessToken(jsonObject.getString("access_token"));
		accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
		accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
		accessToken.setOpenid(jsonObject.getString("openid"));
		accessToken.setScope(jsonObject.getString("scope"));
		return accessToken;
	}
	/**
	 * 微信OAuth2.0授权-刷新access_token（如果需要）
	 */
	public OAuthAccessToken refershOAuthAccessToken(String appid, String refresh_token) throws Exception {
		String getreferAccessUrl= useValue.getreferAccessUrl.replace("APPID", appid).replace("REFRESH_TOKEN", refresh_token);
		log.info("getreferAccessUrl Value:"+getreferAccessUrl);
		HttpGet get = HttpClientConnectionManager.getGetMethod(getreferAccessUrl);
		HttpResponse response = httpclient.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		OAuthAccessToken accessToken=new OAuthAccessToken();
		accessToken.setAccessToken(jsonObject.getString("access_token"));
		accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
		accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
		accessToken.setOpenid(jsonObject.getString("openid"));
		accessToken.setScope(jsonObject.getString("scope"));
		return accessToken;
	}
	/**
	 * 微信OAuth2.0授权-检验授权凭证（access_token）是否有效
	 */
	public boolean isAccessTokenValid(String accessToken, String openId) throws Exception {
		String isOAuthAccessToken= useValue.isOAuthAccessToken.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		log.info("isOAuthAccessToken Value:"+isOAuthAccessToken);
		HttpGet get = HttpClientConnectionManager.getGetMethod(isOAuthAccessToken);
		HttpResponse response = httpclient.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		int returnNum=jsonObject.getIntValue("errcode");
		if(returnNum==0){
			return true;
		}
		return false;
	}
	/**
	 * 微信OAuth2.0授权-获取用户信息（网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息）
	 */
	public UserEntity acceptOAuthUserNews(String accessToken, String openId) throws Exception {
		String getOAuthUserNews= useValue.getOAuthUserNews.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		log.info("getOAuthUserNews Value:"+getOAuthUserNews);
		HttpGet get = HttpClientConnectionManager.getGetMethod(getOAuthUserNews);
		HttpResponse response = httpclient.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		UserEntity entity=new UserEntity();
		entity.setOpenid(jsonObject.getString("openid"));
		entity.setNickname(jsonObject.getString("nickname"));
		entity.setSex(jsonObject.getIntValue("sex"));
		entity.setProvince(jsonObject.getString("province"));
		entity.setCity(jsonObject.getString("city"));
		entity.setCountry(jsonObject.getString("country"));
		entity.setHeadimgurl(jsonObject.getString("headimgurl"));
		return entity;
	}
	
	public JSONObject getJsapiUrl(String accessToken) throws Exception {
		String getJsapiUrl= useValue.getJsapiUrl.replace("ACCESS_TOKEN", accessToken);
		log.info("getOAuthUserNews Value:"+getJsapiUrl);
		HttpGet get = HttpClientConnectionManager.getGetMethod(getJsapiUrl);
		HttpResponse response = httpclient.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		return jsonObject;
	}
	public JSONObject getSMSiUrl(String accessToken) throws Exception {
		String getJsapiUrl= useValue.getSMSiUrl.replace("ACCESS_TOKEN", accessToken);
		log.info("getOAuthUserNews Value:"+getJsapiUrl);
		HttpGet get = HttpClientConnectionManager.getGetMethod(getJsapiUrl);
		HttpResponse response = httpclient.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		return jsonObject;
	}
	
	public JSONObject getCoordinate(String locations) throws Exception {
		log.info("getCoordinate Value:-----------");
		String getJsapiUrl="http://restapi.amap.com/v3/assistant/coordinate/convert?locations="+locations+"&coordsys=gps&output=JSON&key=0a6f72aa6a42c0378d6178160385aec3";
		HttpGet get = HttpClientConnectionManager.getGetMethod(getJsapiUrl);
		HttpResponse response = httpclient.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		return jsonObject;
	}
	
	
	public JSONObject getOAuthMin(String appid, String appsecret,
                                  String code) throws ClientProtocolException, IOException {
		String getOAuthMinURL= useValue.getMinUrl.replace("APPID", appid).replace("SECRET", appsecret).replace("JSCODE", code);
		log.info("getOAuthMinURL Value:"+getOAuthMinURL);
		HttpGet get = HttpClientConnectionManager.getGetMethod(getOAuthMinURL);
		HttpResponse response = httpclient.execute(get);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		log.info("jsonObject Value:"+jsonObject);
		return jsonObject;
	}
	 
	
	
	
}
