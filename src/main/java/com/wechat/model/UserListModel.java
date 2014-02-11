package com.wechat.model;

import com.wechat.bean.UserListBean;
import com.wss.utils.Utils;

public class UserListModel {
	private static UserListModel instance;

	public static UserListModel getInstance() {
		if (instance == null) instance = new UserListModel();
		return instance;
	}

	public Boolean addUser(String wechatId) {
		UserListBean user = new UserListBean();
		user.set("userWechat", wechatId);
		user.set("userAddTime", Utils.getDateTime("yyyy-MM-dd HH:mm:ss"));
		return user.save();
	}

	public Boolean updateUser(UserListBean user) {
		return user.update();
	}

	public UserListBean getUserByWechat(String wechatId) {
		UserListBean user = UserListBean.getDao().findById(wechatId);
		return user;
	}
}
