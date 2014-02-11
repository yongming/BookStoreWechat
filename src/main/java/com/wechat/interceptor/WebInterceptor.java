package com.wechat.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.wechat.bean.UserListBean;
import com.wechat.model.UserListModel;

public class WebInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {
		Controller curCon = ai.getController();		
		if (curCon.getClass().getSimpleName().indexOf("Web") != 0) {
			ai.invoke();
			return;
		}
		String wechatId = curCon.getPara("wechatId");
		if (wechatId == null || "".equals(wechatId = wechatId.trim())) {
			curCon.renderText("Error wechatId para");
			return;
		}
		UserListBean curUser = UserListModel.getInstance().getUserByWechat(wechatId);
		if (curUser == null) {
			curCon.renderText("Unknow user");
			return;
		}
		curCon.setAttr("wechatId", wechatId);
		ai.getController().setAttr("curUser", curUser);
		ai.invoke();
	}

}
