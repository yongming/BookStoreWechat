package com.wss.utils;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.jfinal.core.Controller;
import com.wechat.common.Config;
import com.wss.utils.Encryption.EncryptionType;
import com.wss.utils.ReceiveMsgFormat.EventType;
import com.wss.utils.ReceiveMsgFormat.ReceiveType;
import com.wss.utils.ReplyMsgFormat.ReplyType;

public class WechatUtils {
	public static ReceiveMsgFormat decodeMsg(String msg) {
		if (Config.MyConfig.DevMode) {
			System.out.println("-收到一条微信消息---------------------------------------------------------------");
			System.out.println(System.currentTimeMillis());
			System.out.println(msg);
			System.out.println("--------------------------------------------------------------------------------");
		}
		ReceiveMsgFormat resu = new ReceiveMsgFormat();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(msg.getBytes()));
			NodeList nodes = doc.getChildNodes().item(0).getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				String name = nodes.item(i).getNodeName();
				String value = nodes.item(i).getTextContent();
				if ("ToUserName".equalsIgnoreCase(name)) resu.setToUserName(value);
				else if ("FromUserName".equalsIgnoreCase(name)) resu.setFromUserName(value);
				else if ("CreateTime".equalsIgnoreCase(name)) resu.setCreateTime(value);
				else if ("MsgId".equalsIgnoreCase(name)) resu.setMsgId(value);
				else if ("Event".equalsIgnoreCase(name)) {
					if ("subscribe".equalsIgnoreCase(value)) resu.setEvent(EventType.SUBSCRIBE);
					else if ("unsubscribe".equalsIgnoreCase(value)) resu.setEvent(EventType.UNSUBSCRIBE);
					else if ("click".equalsIgnoreCase(value)) resu.setEvent(EventType.CLICK);
				} else if ("MsgType".equalsIgnoreCase(name)) {
					if ("TEXT".equalsIgnoreCase(value)) resu.setMsgType(ReceiveType.TEXT);
					else if ("TEXT".equalsIgnoreCase(value)) resu.setMsgType(ReceiveType.TEXT);
					else if ("IMAGE".equalsIgnoreCase(value)) resu.setMsgType(ReceiveType.IMAGE);
					else if ("VOICE".equalsIgnoreCase(value)) resu.setMsgType(ReceiveType.VOICE);
					else if ("VIDEO".equalsIgnoreCase(value)) resu.setMsgType(ReceiveType.VIDEO);
					else if ("LOCATION".equalsIgnoreCase(value)) resu.setMsgType(ReceiveType.LOCATION);
					else if ("LINK".equalsIgnoreCase(value)) resu.setMsgType(ReceiveType.LINK);
					else if ("EVENT".equalsIgnoreCase(value)) resu.setMsgType(ReceiveType.EVENT);
				}else if(("EventKey").equalsIgnoreCase(name)) {
					resu.setCick_EventKey(value.toUpperCase());
				} else {
					try {
						// setType_NodeName
						StringBuilder sb = new StringBuilder(name);
						sb.insert(0, resu.getMsgType().toString().toLowerCase() + "_");
						sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
						sb.insert(0, "set");
						if ("Location_X".equalsIgnoreCase(name) || "Location_Y".equalsIgnoreCase(name)) {
							ReceiveMsgFormat.class.getMethod(sb.toString(), Float.class).invoke(resu, Float.parseFloat(value));
						} else if ("Scale".equalsIgnoreCase(name)) {
							ReceiveMsgFormat.class.getMethod(sb.toString(), Integer.class).invoke(resu, Integer.parseInt(value));
						} else {
							ReceiveMsgFormat.class.getMethod(sb.toString(), new Class[] { String.class }).invoke(resu, value);
						}
					} catch (Exception ex) {}
				}
			}
		} catch (Exception ex) {
			return null;
		}
		return resu;
	}

	public static Boolean veriyMsg(String token, String signature, String timestamp, String nonce) {
		String[] str = { token, timestamp, nonce };
		java.util.Arrays.sort(str);
		String bigStr = str[0] + str[1] + str[2];
		String digest = Encryption.encode(bigStr, EncryptionType.SHA1);
		return digest.equalsIgnoreCase(signature);
	}

	public static String encodeMsg(ReplyMsgFormat msg) {
		String message = "";
		String format = "";
		format += "<xml>";
		format += "<ToUserName><![CDATA[%s]]></ToUserName>";
		format += "<FromUserName><![CDATA[%s]]></FromUserName>";
		format += "<CreateTime>%s</CreateTime>";
		format += "<MsgType><![CDATA[%s]]></MsgType>";
		message += String.format(format, msg.getToUserName(), msg.getFromUserName(), msg.getCreateTime(), msg.getMsgType().toString().toLowerCase());

		if (msg.getMsgType() == ReplyType.TEXT) {
			format = "<Content><![CDATA[%s]]></Content>";
			message += String.format(format, msg.getText_Content());
		} else if (msg.getMsgType() == ReplyType.IMAGE) {
			format = "<Image><MediaId><![CDATA[%s]]></MediaId></Image>";
			message += String.format(format, msg.getImage_MediaId());
		} else if (msg.getMsgType() == ReplyType.VOICE) {
			format = "<Voice><MediaId><![CDATA[%s]]></MediaId></Voice>";
			message += String.format(format, msg.getVoice_MediaId());
		} else if (msg.getMsgType() == ReplyType.VIDEO) {
			format = "<Video>";
			format += "<MediaId><![CDATA[%s]]></MediaId>";
			format += "<Title><![CDATA[%s]]></Title>";
			format += "<Description><![CDATA[%s]]></Description>";
			format += "</Video> ";
			message += String.format(format, msg.getVideo_MediaId(), msg.getVideo_Title(), msg.getVideo_Description());
		} else if (msg.getMsgType() == ReplyType.MUSIC) {
			format = "<Music>";
			format += "<Title><![CDATA[%s]]></Title>";
			format += "<Description><![CDATA[%s]]></Description>";
			format += "<MusicUrl><![CDATA[%s]]></MusicUrl>";
			format += "<HQMusicUrl><![CDATA[%s]]></HQMusicUrl>";
			format += "<ThumbMediaId><![CDATA[%s]]></ThumbMediaId>";
			format += "</Music> ";
			message += String.format(format, msg.getMusic_Title(), msg.getMusic_Description(), msg.getMusic_MusicUrl(), msg.getMusic_HQMusicUrl(), msg.getMusic_ThumbMediaId());
		} else if (msg.getMsgType() == ReplyType.NEWS) {
			message += "<ArticleCount>" + msg.getNews_Articles().size() + "</ArticleCount>";
			message += "<Articles>";
			for (ArticleItem item : msg.getNews_Articles()) {
				format = "<item>";
				format += "<Title><![CDATA[%s]]></Title>";
				format += "<Description><![CDATA[%s]]></Description>";
				format += "<PicUrl><![CDATA[%s]]></PicUrl>";
				format += "<Url><![CDATA[%s]]></Url>";
				format += "</item>";
				message += String.format(format, item.getTitle(), item.getDescription(), item.getPicUrl(), item.getUrl());
			}
			message += "</Articles>";
		}
		message += "</xml>";
		if (Config.MyConfig.DevMode) {
			System.out.println("-生成一条微信消息---------------------------------------------------------------");
			System.out.println(System.currentTimeMillis());
			System.out.println(message);
			System.out.println("--------------------------------------------------------------------------------");
		}
		return message;
	}

	public static void showTextMsg(Controller controller, String textMsg) {
		ReplyMsgFormat rep = new ReplyMsgFormat((ReceiveMsgFormat) controller.getAttr("WechatMessage"));
		rep.setMsgType(ReplyType.TEXT);
		rep.setText_Content(textMsg);
		controller.renderText(encodeMsg(rep));
	}
}
