package com.wechat.controller;

import com.jfinal.core.Controller;

public class WebActivityController extends Controller {
	public void lottery() {
		render("/template/activity/lottery.html");
	}
}
