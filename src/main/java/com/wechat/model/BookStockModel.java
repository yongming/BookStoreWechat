package com.wechat.model;

import java.util.List;

import com.wechat.bean.BookStockBean;

public class BookStockModel {
	private static BookStockModel instance;

	public static BookStockModel getInstance() {
		if (instance == null) instance = new BookStockModel();
		return instance;
	}

	public List<BookStockBean> getStockByIsbn(String isbn, Float lon, Float lat) {
		String SQL = "";
		SQL += "select s.storeId,s.storeName,n.inStock,";
		SQL += "DistXY(?,?,s.storeLon,s.storeLat) as storeDist ";
		SQL += "from stocknum as n ";
		SQL += "LEFT JOIN storelist as s on n.storeId=s.storeId ";
		SQL += "WHERE inStock>0 and n.isbn=? ORDER BY storeDist limit 6";
		return BookStockBean.getDao().find(SQL, lon, lat, isbn);
	}
}