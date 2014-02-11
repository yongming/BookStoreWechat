package com.wechat.controller;

import com.jfinal.core.Controller;
import com.wechat.bean.UserListBean;
import com.wss.utils.ReceiveMsgFormat;
import com.wss.utils.WechatUtils;

public class WcLocationController extends Controller {
	public void index() {
		ReceiveMsgFormat curMsg = getAttr("WechatMessage");
		UserListBean curUser = getAttr("curUser");
		float x = curMsg.getLocation_Location_X(), y = curMsg.getLocation_Location_Y();
		if (y < 73.0F || y > 136.0F || x < 3.0F || x > 54.0F) {
			WechatUtils.showTextMsg(this, "对不起，我们只为中国境内用户提供服务。请确认您发送的地理位置在中国境内。");
			return;
		}
		curUser.set("userLon", x).set("userLat", y).set("userLocLabel", curMsg.getLocation_Label()).update();
		setAttr("curUser", curUser);
		forwardAction("/wechat/action/storeSearch/findByLocation");
	}
}
