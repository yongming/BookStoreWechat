package com.wechat.controller;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.wechat.bean.StoreListBean;
import com.wechat.bean.UserListBean;
import com.wechat.model.StoreListModel;

public class WebStoreSearchController extends Controller {
	public void list() {
		UserListBean curUser = getAttr("curUser");
		if (curUser.get("userLon") == null || curUser.get("userLat") == null) {
			renderText("获取用户地理位置失败，请重新发送位置信息给我们");
			return;
		}
		Float lon = curUser.getBigDecimal("userLon").floatValue(), lat = curUser.getBigDecimal("userLat").floatValue();
		if (lon == null || lat == null) {
			renderText("Error Para");
			return;
		}
		setAttr("total", StoreListModel.getInstance().getStoreCount());
		setAttr("local_lon", lon);
		setAttr("local_lat", lat);
		render("/template/storeList.html");
	}

	public void list_ajax() {
		Float lon, lat;
		Integer page;
		try {
			lon = Float.parseFloat(getPara("lon"));
			lat = Float.parseFloat(getPara("lat"));
			page = getParaToInt("page");
		} catch (Exception ex) {
			renderText("Error Para");
			return;
		}
		if (lon == null || lat == null) {
			renderText("Error Para");
			return;
		}
		if (null == page || page <= 0) {
			page = 1;
		}
		List<StoreListBean> list = StoreListModel.getInstance().getByLocation(lon, lat, page, 6);
		if (list == null) {
			renderText("Error Result");
			return;
		}
		JSONObject jo = new JSONObject();
		JSONArray jr = new JSONArray();
		for (StoreListBean cs : list) {
			JSONObject cjo = new JSONObject();
			cjo.put("storeId", cs.get("storeId"));
			cjo.put("storePlace", cs.get("storePlace"));
			cjo.put("storeName", cs.get("storeName"));
			cjo.put("storeDist", cs.get("storeDist"));
			cjo.put("storePicture", cs.get("storePicture"));
			jr.add(cjo);
		}
		jo.put("data", jr);
		jo.put("ret", 0);
		jo.put("more", list.size() >= 6);
		renderText(jo.toJSONString());
	}

	public void detail() {
		Integer storeId = getParaToInt(0);
		if (storeId <= 0) {
			renderText("Error Para");
			return;
		}
		StoreListBean store = StoreListModel.getInstance().getById(storeId);
		if (store == null) {
			renderText("No such store");
			return;
		}
		setAttr("store", store);
		render("/template/storeDetail.html");
	}
}
