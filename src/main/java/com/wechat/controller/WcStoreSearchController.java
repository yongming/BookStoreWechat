package com.wechat.controller;

import java.math.BigDecimal;
import java.util.List;

import com.jfinal.core.Controller;
import com.wechat.bean.StoreListBean;
import com.wechat.common.Config.MyConfig;
import com.wechat.model.StoreListModel;
import com.wss.utils.ArticleItem;
import com.wss.utils.ReceiveMsgFormat;
import com.wss.utils.ReplyMsgFormat;
import com.wss.utils.WechatUtils;
import com.wss.utils.ReplyMsgFormat.ReplyType;

public class WcStoreSearchController extends Controller {
	public void findByLocation() {
		ReceiveMsgFormat curMsg;
		curMsg = getAttr("WechatMessage");
		List<StoreListBean> StoreList = StoreListModel.getInstance().getByLocation(curMsg.getLocation_Location_X(), curMsg.getLocation_Location_Y(), 1, 5);
		if (StoreList == null || StoreList.size() == 0) {
			WechatUtils.showTextMsg(this, "对不起，找不到您要的数据，请重试。");
			return;
		}

		ReplyMsgFormat repMsg = new ReplyMsgFormat(curMsg);
		repMsg.setMsgType(ReplyType.NEWS);
		ArticleItem item = new ArticleItem();
		item.setDescription("");
		item.setPicUrl(MyConfig.CDNHost + "wssbsw/images/storetop.jpg");
		item.setTitle("以下是离你最近的5家书店~");
		item.setUrl(MyConfig.WebHost + "store/list?wechatId=" + curMsg.getFromUserName());
		repMsg.addNews_Articles(item);
		for (StoreListBean StoreData : StoreList) {
			item = new ArticleItem();
			item.setDescription("");
			item.setPicUrl(StoreData.getStr("storePicture"));
			BigDecimal jl = StoreData.get("storeDist");
			item.setTitle(StoreData.getStr("storeName") + "\r距离您" + jl.toString() + "米");
			item.setUrl(MyConfig.WebHost + "store/detail/" + StoreData.get("storeId") + "?wechatId=" + curMsg.getFromUserName());
			repMsg.addNews_Articles(item);
		}
		renderText(WechatUtils.encodeMsg(repMsg));

	}
}
