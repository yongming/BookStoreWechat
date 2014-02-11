package com.wechat.bean;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class StoreListBean extends Model<StoreListBean> {
	private static StoreListBean daoInstance;

	public static StoreListBean getDao() {
		if (daoInstance == null) {
			daoInstance = new StoreListBean();
		}
		return daoInstance;
	}
}
