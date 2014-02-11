package com.wechat.controller;

import com.jfinal.core.Controller;
import com.wechat.bean.UserListBean;
import com.wechat.common.Config.MyConfig;
import com.wss.utils.ReceiveMsgFormat;
import com.wss.utils.WechatUtils;

public class WcEventController extends Controller {
	public void subscribe() {
		UserListBean curUser = getAttr("curUser");
		ReceiveMsgFormat curMsg = getAttr("WechatMessage");
		String reply = "", name;
		if ((name = curUser.get("userName")) == null || "".equals("userName")) {
			reply = "感谢您关注我们微信，您可以<a href=\"" + MyConfig.WebHost + "member/modify?wechatId=" + curMsg.getFromUserName() + "\">点击此处</a>注册成为我们会员，享受全球五千家门店的会员特惠。";
		} else {
			reply = "亲爱的" + name + "，感谢您重新关注我们微信。";
		}
		reply += "如需要帮助，请回复help。";
		WechatUtils.showTextMsg(this, reply);
	}

	public void unsubscribe() {
		renderText("");
	}

}
