package com.wechat.bean;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class BookStockBean extends Model<BookStockBean> {
	private static BookStockBean daoInstance;

	public static BookStockBean getDao() {
		if (daoInstance == null) {
			daoInstance = new BookStockBean();
		}
		return daoInstance;
	}
}
