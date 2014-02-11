package com.wechat.controller;

import com.jfinal.core.Controller;
import com.wechat.bean.UserListBean;

public class WebMemberController extends Controller {
	public void modify() {
		UserListBean curUser = getAttr("curUser");
		String actStr = "D";
		editUserAction: {
			if ("edit".equalsIgnoreCase(getPara("act"))) {
				String newName = getPara("name"), newPhone = getPara("phone");
				if (null == newName || "".equals(newName)) {
					actStr = "真实姓名不能呢个为空";
					break editUserAction;
				}
				if (null == newPhone || "".equals(newPhone)) {
					actStr = "手机号码不能呢个为空";
					break editUserAction;
				}
				if (curUser.set("userName", newName).set("userPhone", newPhone).update()) {
					actStr = "S";
				} else {
					actStr = "修改用户资料失败";
				}
			}
		}
		setAttr("resultStr", actStr);
		setAttr("userData", curUser);
		render("/template/editUser.html");
	}
}
