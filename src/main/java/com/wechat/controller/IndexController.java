package com.wechat.controller;

import com.jfinal.core.Controller;

public class IndexController extends Controller {
	public void index() {
		StringBuilder sb = new StringBuilder();
		sb.append("<center>");
		sb.append("<h1>Wechat Bookstore Business & Operation Support System</h1>");
		sb.append("</center>");
		renderHtml(sb.toString());
	}
}
