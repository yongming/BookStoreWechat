package com.wechat.controller;

import java.net.URLEncoder;

import com.jfinal.core.Controller;
import com.wechat.bean.UserListBean;
import com.wechat.common.Config.MyConfig;
import com.wss.utils.ArticleItem;
import com.wss.utils.ReceiveMsgFormat;
import com.wss.utils.ReplyMsgFormat;
import com.wss.utils.WechatUtils;
import com.wss.utils.ReplyMsgFormat.ReplyType;

public class WcVipController extends Controller {
	public void queryVipCard() throws Exception {
		ReceiveMsgFormat curMsg = getAttr("WechatMessage");
		UserListBean curUser = getAttr("curUser");
		String userName = curUser.get("userName");
		if (null == userName || "".equals(userName)) {
			WechatUtils.showTextMsg(this, "您还没有注册会员，请<a href=\"" + MyConfig.WebHost + "member/modify?wechatId=" + curMsg.getFromUserName() + "\">点击此处</a>注册成为我们会员，享受全球五千家门店的会员特惠。");
			return;
		}
		ReplyMsgFormat repMsg = new ReplyMsgFormat(curMsg);
		repMsg.setMsgType(ReplyType.NEWS);
		ArticleItem item = new ArticleItem();
		item.setDescription("凭本会员卡可在全球五千家门店享受会员特惠。\n点击本消息可修改您的会员信息。\n本卡最终解释权归顺顺连锁书店所有。");
		item.setPicUrl(MyConfig.WebHost + "showcard/" + URLEncoder.encode(userName, "UTF-8"));
		item.setTitle("您的贵宾卡 ");
		item.setUrl(MyConfig.WebHost + "member/modify?wechatId=" + curMsg.getFromUserName());
		repMsg.addNews_Articles(item);
		renderText(WechatUtils.encodeMsg(repMsg));
	}
}
