package com.wechat.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.wechat.bean.UserListBean;
import com.wechat.common.Config.MyConfig;
import com.wechat.interceptor.WechatInterceptor;
import com.wss.utils.ReceiveMsgFormat;
import com.wss.utils.ReceiveMsgFormat.EventType;
import com.wss.utils.Utils;
import com.wss.utils.WechatUtils;

public class WechatController extends Controller {

	@Before(WechatInterceptor.class)
	public void index() {
		ReceiveMsgFormat curMsg;
		curMsg = getAttr("WechatMessage");
		if (curMsg == null) {
			renderText("");
			return;
		}
		switch (curMsg.getMsgType()) {
		case EVENT:
			if (curMsg.getEvent() == EventType.SUBSCRIBE) {
				forwardAction("/wechat/message/event/subscribe");
			} else if (curMsg.getEvent() == EventType.UNSUBSCRIBE) {
				forwardAction("/wechat/message/event/unsubscribe");
			} else if (curMsg.getEvent() == EventType.CLICK) {
				parseClick(curMsg);
			}
			break;
		case IMAGE:
			forwardAction("/wechat/message/picture");
			break;
		case LOCATION:
			forwardAction("/wechat/message/location");
			break;
		case TEXT:
			parseText(curMsg);
			break;
		default:
			WechatUtils.showTextMsg(this, "您发送的消息类型无法识别。\r\n您可以发送help查看帮助。");
			return;
		}

	}

	private void parseClick(ReceiveMsgFormat curMsg) {
		String key = curMsg.getCick_EventKey();
		UserListBean curUser = getAttr("curUser");
		if ("VIEW_VIPCARD".equals(key)) { // 查看会员卡
			forwardAction("/wechat/action/member/queryVipCard");
		} else if ("MODIFY_MEMBER".equals(key)) { // 更新资料
			WechatUtils.showTextMsg(this, "请<a href=\"" + MyConfig.WebHost + "member/modify?wechatId=" + curMsg.getFromUserName() + "\">点击此处</a>更新会员数据。");
		} else if ("BOOK_SEARCH".equals(key)) { // 图书搜索
			String helpInfo = "您可以通过以下方式搜索图书:" + "\n";
			helpInfo += "1、发送书本名称搜索图书" + "\n";
			helpInfo += "2、发送ISBN可以查询该书信息" + "\n";
			helpInfo += "3、发送书本背后的条码照片亦可查询该书信息" + "\n";
			WechatUtils.showTextMsg(this, helpInfo);
		} else if ("STORE_SEARCH".equals(key)) { // 附近书店
			if (null != curUser.get("userLon") && null != curUser.get("userLat")) {
				Float lon, lat;
				lon = curUser.getBigDecimal("userLon").floatValue();
				lat = curUser.getBigDecimal("userLat").floatValue();
				if (lon > 0 && lat > 0) {
					forwardAction("/wechat/action/storeSearch/findByLocation");
					return;
				}
			}
			WechatUtils.showTextMsg(this, "请先发送地理位置给我们，我们才可以给您找到附近的书店。");
		} else if ("ERASE_LOCATION".equals(key)) { // 清除位置信息
			curUser.set("userLon", 0.0).set("userLat", 0.0).set("userLocLabel", "").update();
			WechatUtils.showTextMsg(this, "缓存的用户数据清理成功!");
		} else if ("HELP_MENU".equals(key)) { // 查看帮助
			sendHelpMsg(curMsg);
		} else if ("LOTTERY".equals(key)) { // LOTTERY
			WechatUtils.showTextMsg(this, "请<a href=\"" + MyConfig.WebHost + "activity/lottery?wechatId=" + curMsg.getFromUserName() + "\">点击此处</a>进入抽奖页。");
		}else{
			WechatUtils.showTextMsg(this, "读取消息菜单指令错误!");
		}
	}

	private void parseText(ReceiveMsgFormat curMsg) {
		String txt = curMsg.getText_Content();
		if (txt == null || "".equals(txt)) {
			WechatUtils.showTextMsg(this, "请回复发空白消息的秘籍。谢谢。");
			return;
		}
		if ("help".equalsIgnoreCase(txt) || "?".equalsIgnoreCase(txt) || "？".equalsIgnoreCase(txt)) {
			sendHelpMsg(curMsg);
			return;
		}
		if ("vip".equalsIgnoreCase(txt)) {
			forwardAction("/wechat/action/member/queryVipCard");
			return;
		}
		if ("刮刮卡".equalsIgnoreCase(txt)) {
			WechatUtils.showTextMsg(this, "请<a href=\"" + MyConfig.WebHost + "activity/lottery?wechatId=" + curMsg.getFromUserName() + "\">点击此处</a>进入抽奖页。");
			return;
		}
		if ((txt.length() == 13 || txt.length() == 10) & Utils.isNumeric(txt)) {
			forwardAction("/wechat/action/findBook/findByIsbn");
			return;
		}
		forwardAction("/wechat/action/findBook/findByKeyword");
		return;

	}

	private void sendHelpMsg(ReceiveMsgFormat curMsg) {
		String helpInfo = "感谢您使用顺顺书屋微信，以下是本微信的功能菜单:" + "\n";
		helpInfo += "1、发送书本名称搜索图书" + "\n";
		helpInfo += "2、发送ISBN可以查询该书信息" + "\n";
		helpInfo += "3、发送书本背后的条码照片亦可查询该书信息" + "\n";
		helpInfo += "4、发送位置信息查询您附近的书店" + "\n";
		helpInfo += "5、发送VIP查询您的会员卡或修改信息" + "\n";
		helpInfo += "6、点击返回的消息，可以获取更多信息哟" + "\n";
		helpInfo += "7、发送'刮刮卡'，可以参与刮奖活动" + "\n";
		helpInfo += "8、发送help或?调出本菜单" + "\n";
		WechatUtils.showTextMsg(this, helpInfo);
	}

}
