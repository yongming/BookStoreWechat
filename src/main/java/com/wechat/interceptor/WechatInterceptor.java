package com.wechat.interceptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.wechat.bean.UserListBean;
import com.wechat.common.Config.MyConfig;
import com.wechat.model.UserListModel;
import com.wss.utils.ReceiveMsgFormat;
import com.wss.utils.WechatUtils;

public class WechatInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		ReceiveMsgFormat recMsg;
		UserListBean curUser;
		Controller con = ai.getController();
		try {
			Boolean veriy = WechatUtils.veriyMsg(MyConfig.WechatToken, con.getPara("signature", ""), con.getPara("timestamp", ""), con.getPara("nonce", ""));
			if (!veriy) {
				con.renderText("Error Veriy Token");
				return;
			}
			if (con.getPara("echostr") != null) {
				con.renderText(con.getPara("echostr"));
				return;
			}
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getRequest().getInputStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			if ("".equals(buffer.toString())) {
				con.renderText("Get wechat message empty");
				return;
			}
			recMsg = WechatUtils.decodeMsg(buffer.toString());
			if (recMsg == null) {
				con.renderText("decode wechat message error");
				return;
			}
			curUser = UserListModel.getInstance().getUserByWechat(recMsg.getFromUserName());
			if (curUser == null) {
				// 新用户或者系统处理关注信息失败的用户。因为关注通知不一定收到，所以在这里注册用户吧。
				UserListModel.getInstance().addUser(recMsg.getFromUserName());
				curUser = UserListModel.getInstance().getUserByWechat(recMsg.getFromUserName());
			}
			if (curUser == null) {
				con.renderText("Get user error");
				return;
			}
		} catch (Exception e) {
			con.renderText("error:" + e.getMessage());
			return;
		}
		ai.getController().setAttr("WechatMessage", recMsg);
		ai.getController().setAttr("curUser", curUser);
		ai.invoke();
	}
}
