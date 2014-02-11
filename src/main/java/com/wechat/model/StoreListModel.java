package com.wechat.model;

import java.util.List;

import com.wechat.bean.StoreListBean;

public class StoreListModel {
	private static StoreListModel instance;

	public static StoreListModel getInstance() {
		if (instance == null) instance = new StoreListModel();
		return instance;
	}

	public List<StoreListBean> getByLocation(Float lon, Float lat, int page, int pageSize) {
		String SQL = "";
		SQL += "select storeId,storeName,storeCity,storePlace,storeLon,storeLat,storePhone,storePicture,";
		SQL += "storePeople,storeStock,DistXY(?,?,storeLon,storeLat) as storeDist ";
		SQL += "FROM storelist order by storeDist limit ?,?";
		return StoreListBean.getDao().find(SQL, lon, lat, (page - 1) * pageSize, pageSize);
	}

	public StoreListBean getById(Integer storeId) {
		String SQL = "";
		SQL += "select storeId,storeName,storeCity,storePlace,storeLon,storeLat,storePhone,storePicture,";
		SQL += "storePeople,storeStock FROM storelist where storeId=?";
		return StoreListBean.getDao().findFirst(SQL, storeId);
	}

	public Integer getStoreCount() {
		String SQL = "select count(*) as total from storelist";
		return ((Long) StoreListBean.getDao().findFirst(SQL).get("total", 0)).intValue();
	}
}