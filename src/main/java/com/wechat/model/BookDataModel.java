package com.wechat.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wechat.bean.BookDataBean;
import com.wss.utils.SimpleHttpHelper;
import com.wss.utils.Utils;

public class BookDataModel {
	private static BookDataModel instance;

	private BookDataModel() {}

	public static BookDataModel getInstance() {
		if (instance == null) instance = new BookDataModel();
		return instance;
	}

	public BookDataBean getByISBN(String isbn) {
		String bookJsonStr = new SimpleHttpHelper("http://api.douban.com/v2/book/isbn/" + isbn).sendGet();
		if (bookJsonStr == null || "".equals(bookJsonStr)) return null;
		try {
			return parseBookJson(JSON.parseObject(bookJsonStr));
		} catch (Exception ex) {
			return null;
		}
	}

	public List<BookDataBean> getByKeyword(String keyword, Integer page, Integer pageSize) {
		SimpleHttpHelper httpHelper = new SimpleHttpHelper("https://api.douban.com/v2/book/search");
		httpHelper.setPara("q", keyword);
		httpHelper.setPara("start", (page - 1) * pageSize);
		httpHelper.setPara("count", pageSize);
		String bookJsonStr = httpHelper.sendGet();
		if (bookJsonStr == null || "".equals(bookJsonStr)) return null;
		try {
			List<BookDataBean> dat = new ArrayList<BookDataBean>();
			JSONObject retDat = JSON.parseObject(bookJsonStr);
			int resultTotal = retDat.getInteger("total");
			int curTotal = retDat.getInteger("count");
			int curPos = retDat.getInteger("start");
			JSONArray bookList = retDat.getJSONArray("books");
			for (int i = 0; i < bookList.size(); i++) {
				BookDataBean curBean = parseBookJson(bookList.getJSONObject(i));
				curBean.put("resultTotal", resultTotal);
				curBean.put("curTotal", curTotal);
				curBean.put("curPos", ++curPos);
				dat.add(curBean);
			}
			return dat;
		} catch (Exception ex) {
			return null;
		}
	}

	private BookDataBean parseBookJson(JSONObject bookJson) {
		try {
			BookDataBean bookData = new BookDataBean();
			bookData.put("id", bookJson.getString("id"));
			bookData.put("isbn10", bookJson.getString("isbn10"));
			bookData.put("isbn13", bookJson.getString("isbn13"));
			bookData.put("title", bookJson.getString("title"));
			bookData.put("largeImages", bookJson.getJSONObject("images").getString("large"));
			bookData.put("mediumImages", bookJson.getJSONObject("images").getString("medium"));
			bookData.put("author", Utils.implodeArray(bookJson.getJSONArray("author").toArray(), ","));
			bookData.put("translator", Utils.implodeArray(bookJson.getJSONArray("translator").toArray(), ","));
			bookData.put("publisher", bookJson.getString("publisher"));
			bookData.put("pubdate", bookJson.getString("pubdate"));
			bookData.put("ratingAvg", bookJson.getJSONObject("rating").getString("average"));
			bookData.put("binding", bookJson.getString("binding"));
			String prices = bookJson.getString("price");
			Integer pricesIndex = prices.indexOf("元");
			bookData.put("price", pricesIndex >= 0 ? prices.substring(0, pricesIndex) : prices);
			bookData.put("pages", bookJson.getString("pages"));
			bookData.put("author_intro", bookJson.getString("author_intro"));
			bookData.put("summary", bookJson.getString("summary"));
			bookData.put("summaryLite", (bookJson.getString("summary").split("\n"))[0]);
			bookData.put("catalog", bookJson.getString("catalog"));
			bookData.put("id", bookJson.getString("id"));
			return bookData;
		} catch (Exception ex) {
			return null;
		}

	}
}
