package com.wechat.controller;

import com.jfinal.core.Controller;
import com.wss.utils.DecodeBarcode;
import com.wss.utils.DecodeBarcode.BarcodeData;
import com.wss.utils.ReceiveMsgFormat;
import com.wss.utils.WechatUtils;

public class WcPictureController extends Controller {
	public void index() {
		ReceiveMsgFormat curMsg = getAttr("WechatMessage");
		String isbn;
		BarcodeData pic = new DecodeBarcode(curMsg.getImage_PicUrl()).parse();
		if (pic == null || (isbn = pic.getRawText()) == null || "".equals(isbn)) {
			WechatUtils.showTextMsg(this, "您拍摄的图片无法识别，请重新拍摄，谢谢。");
			return;
		}
		curMsg.setText_Content(isbn);
		setAttr("WechatMessage", curMsg);
		forwardAction("/wechat/action/findBook/findByIsbn");
	}

}
