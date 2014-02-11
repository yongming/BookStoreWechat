package com.wechat.controller;

import java.util.List;

import com.alibaba.fastjson.*;
import com.jfinal.core.Controller;
import com.wechat.bean.BookDataBean;
import com.wechat.bean.BookStockBean;
import com.wechat.bean.UserListBean;
import com.wechat.model.BookDataModel;
import com.wechat.model.BookStockModel;

public class WebBookSearchController extends Controller {
	public void isbn() {
		String isbn;
		if ((isbn = getPara(0)) == null) {
			renderText("Error Para");
			return;
		}
		isbn = isbn.trim();
		BookDataBean book = BookDataModel.getInstance().getByISBN(isbn);
		if (book == null) {
			renderText("No such book");
			return;
		}
		book.put("summary", "<p>" + book.getStr("summary").replace("\n", "</p><p>") + "</p>");
		book.put("catalog", "<p>" + book.getStr("catalog").replace("\n", "</p><p>") + "</p>");
		book.put("author_intro", "<p>" + book.getStr("author_intro").replace("\n", "</p><p>") + "</p>");
		setAttr("book", book);

		UserListBean curUser = getAttr("curUser");
		List<BookStockBean> BookStock = null;
		if (null != curUser.get("userLon") && null != curUser.get("userLat")) {
			Float lon, lat;
			lon = curUser.getBigDecimal("userLon").floatValue();
			lat = curUser.getBigDecimal("userLat").floatValue();
			if (lon > 0 && lat > 0) BookStock = BookStockModel.getInstance().getStockByIsbn(isbn, lon, lat);
		}
		setAttr("bookStock", BookStock);

		render("/template/bookDetail.html");
	}

	public void search() {
		if (getPara(0) == null) {
			renderText("Error Para");
			return;
		}
		setAttr("keyword", getPara(0).trim());
		render("/template/bookList.html");
	}

	public void search_ajax() {
		String keyword = getPara(0) == null ? "" : getPara(0).trim();
		Integer page = getPara(1) == null ? 1 : getParaToInt(1);
		if ("".equals(keyword) || page <= 0) {
			renderText("Error Para");
			return;
		}
		List<BookDataBean> list = BookDataModel.getInstance().getByKeyword(keyword, page, 10);
		if (list == null) {
			renderText("Error Result");
			return;
		}
		JSONObject jo = new JSONObject();
		JSONArray jr = new JSONArray();
		Boolean hasMore = true;
		Boolean needSet = true;
		for (BookDataBean cb : list) {
			JSONObject cjo = new JSONObject();
			cjo.put("isbn", cb.get("isbn13"));
			cjo.put("title", cb.get("title"));
			cjo.put("mediumImages", cb.get("mediumImages"));
			cjo.put("author", cb.get("author"));
			cjo.put("publisher", cb.get("publisher"));
			cjo.put("curPos", cb.getInt("curPos"));
			if (hasMore && cb.getInt("curPos") == cb.getInt("resultTotal")) {
				hasMore = false;
			}
			if (needSet) {
				jo.put("resultTotal", cb.getInt("resultTotal"));
				jo.put("curTotal", cb.getInt("curTotal"));
				needSet = false;
			}
			jr.add(cjo);
		}
		jo.put("more", hasMore);
		jo.put("data", jr);
		jo.put("ret", 0);
		renderText(jo.toJSONString());
	}
}
