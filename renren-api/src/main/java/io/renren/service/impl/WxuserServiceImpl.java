package io.renren.service.impl;

import io.renren.entity.TokenEntity;
import io.renren.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;


import io.renren.dao.WxuserDao;
import io.renren.entity.WxuserEntity;
import io.renren.service.WxuserService;


@Service("wxuserService")
public class WxuserServiceImpl extends ServiceImpl<WxuserDao, WxuserEntity> implements WxuserService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private WxuserDao wxuserdao;

    @Override
    public Map<String, Object> login(WxuserEntity wx) {
        //获取登录token
        TokenEntity tokenEntity = tokenService.createToken(wx.getId());
        Map<String, Object> map = new HashMap<>(2);
        map.put("token", tokenEntity.getToken());
        map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());
        return map;
    }
    
    //查询用户金果数量
	@Override
	public Integer selectUserId(Integer userId) {
		return wxuserdao.selectUserId(userId);
	}
}
