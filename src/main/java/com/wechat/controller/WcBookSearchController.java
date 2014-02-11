package com.wechat.controller;

import java.net.URLEncoder;
import java.util.List;

import com.jfinal.core.Controller;
import com.wechat.bean.BookDataBean;
import com.wechat.common.Config.MyConfig;
import com.wechat.model.BookDataModel;
import com.wss.utils.ArticleItem;
import com.wss.utils.ReceiveMsgFormat;
import com.wss.utils.ReplyMsgFormat;
import com.wss.utils.ReplyMsgFormat.ReplyType;
import com.wss.utils.WechatUtils;

public class WcBookSearchController extends Controller {
	public void findByKeyword() throws Exception {
		ReceiveMsgFormat curMsg = getAttr("WechatMessage");
		List<BookDataBean> bookList = BookDataModel.getInstance().getByKeyword(curMsg.getText_Content(), 1, 5);
		if (bookList == null || bookList.size() == 0) {
			WechatUtils.showTextMsg(this, "无法搜索到关键词为\"" + curMsg.getText_Content() + "\"的图书。");
			return;
		}
		ReplyMsgFormat repMsg = new ReplyMsgFormat(curMsg);
		repMsg.setMsgType(ReplyType.NEWS);
		ArticleItem item = new ArticleItem();
		item.setDescription("");
		item.setPicUrl(MyConfig.CDNHost + "wssbsw/images/booktop.jpg");
		int resultTotal = bookList.get(0).getInt("resultTotal");
		String tit = "共找到" + resultTotal + "本，";
		tit += resultTotal <= 5 ? "都在下面了~" : "点我查看全部~";
		item.setTitle(tit);
		item.setUrl(MyConfig.WebHost + "book/search/" + URLEncoder.encode(curMsg.getText_Content(), "UTF-8") + "?wechatId=" + curMsg.getFromUserName());
		repMsg.addNews_Articles(item);
		for (BookDataBean bookData : bookList) {
			item = new ArticleItem();
			item.setDescription(bookData.getStr("summaryLite"));
			item.setPicUrl(bookData.getStr("mediumImages"));
			item.setTitle(bookData.getStr("title"));
			item.setUrl(MyConfig.WebHost + "book/isbn/" + bookData.getStr("isbn13") + "?wechatId=" + curMsg.getFromUserName());
			repMsg.addNews_Articles(item);
		}
		renderText(WechatUtils.encodeMsg(repMsg));
	}

	public void findByIsbn() {
		ReceiveMsgFormat curMsg = getAttr("WechatMessage");
		BookDataBean bookData = BookDataModel.getInstance().getByISBN(curMsg.getText_Content());
		if (bookData == null) {
			WechatUtils.showTextMsg(this, "无法找到ISBN为\"" + curMsg.getText_Content() + "\"的图书。");
			return;
		}
		ArticleItem item = new ArticleItem();
		if (bookData.getStr("summaryLite").length() <= 50) {
			item.setDescription(bookData.getStr(bookData.getStr("summary").replace("\r", "").replace("\n", "").substring(0, 120) + "...."));
		} else {
			item.setDescription(bookData.getStr("summaryLite"));
		}
		item.setPicUrl(bookData.getStr("largeImages"));
		item.setTitle(bookData.getStr("title"));
		item.setUrl(MyConfig.WebHost + "book/isbn/" + bookData.getStr("isbn13") + "?wechatId=" + curMsg.getFromUserName());

		ReplyMsgFormat repMsg = new ReplyMsgFormat(curMsg);
		repMsg.setMsgType(ReplyType.NEWS);
		repMsg.addNews_Articles(item);
		renderText(WechatUtils.encodeMsg(repMsg));
	}
}
