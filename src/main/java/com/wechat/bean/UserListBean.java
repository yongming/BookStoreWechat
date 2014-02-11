package com.wechat.bean;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class UserListBean extends Model<UserListBean> {
	private static UserListBean daoInstance;

	public static UserListBean getDao() {
		if (daoInstance == null) {
			daoInstance = new UserListBean();
		}
		return daoInstance;
	}
}
