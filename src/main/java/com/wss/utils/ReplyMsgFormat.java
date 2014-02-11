package com.wss.utils;

import java.util.ArrayList;
import java.util.List;


public class ReplyMsgFormat {
	public enum ReplyType {
		TEXT, IMAGE, VOICE, VIDEO, MUSIC, NEWS
	};

	public ReplyMsgFormat() {
		setCreateTime((System.currentTimeMillis() + "").substring(0, 10));

	}

	public ReplyMsgFormat(ReceiveMsgFormat receiveMsg) {
		setCreateTime((System.currentTimeMillis() + "").substring(0, 10));
		setFromUserName(receiveMsg.getToUserName());
		setToUserName(receiveMsg.getFromUserName());
	}

	private String ToUserName;

	public void setToUserName(String value) {
		ToUserName = value;
	}

	public String getToUserName() {
		return ToUserName;
	}

	private String FromUserName;

	public ReplyMsgFormat setFromUserName(String value) {
		FromUserName = value;
		return this;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	private String CreateTime;

	public ReplyMsgFormat setCreateTime(String value) {
		CreateTime = value;
		return this;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	private ReplyType MsgType;

	public ReplyMsgFormat setMsgType(ReplyType value) {
		MsgType = value;
		return this;
	}

	public ReplyType getMsgType() {
		return MsgType;
	}

	private String Text_Content;

	public ReplyMsgFormat setText_Content(String value) {
		Text_Content = value;
		return this;
	}

	public String getText_Content() {
		return Text_Content;
	}

	private String Image_MediaId;

	public ReplyMsgFormat setImage_MediaId(String value) {
		Image_MediaId = value;
		return this;
	}

	public String getImage_MediaId() {
		return Image_MediaId;
	}

	private String Voice_MediaId;

	public ReplyMsgFormat setVoice_MediaId(String value) {
		Voice_MediaId = value;
		return this;
	}

	public String getVoice_MediaId() {
		return Voice_MediaId;
	}

	private String Video_MediaId;

	public ReplyMsgFormat setVideo_MediaId(String value) {
		Video_MediaId = value;
		return this;
	}

	public String getVideo_MediaId() {
		return Video_MediaId;
	}

	private String Video_Title;

	public ReplyMsgFormat setVideo_Title(String value) {
		Video_Title = value;
		return this;
	}

	public String getVideo_Title() {
		return Video_Title;
	}

	private String Video_Description;

	public ReplyMsgFormat setVideo_Description(String value) {
		Video_Description = value;
		return this;
	}

	public String getVideo_Description() {
		return Video_Description;
	}

	private String Music_Title;

	public ReplyMsgFormat setMusic_Title(String value) {
		Music_Title = value;
		return this;
	}

	public String getMusic_Title() {
		return Music_Title;
	}

	private String Music_MusicUrl;

	public ReplyMsgFormat setMusic_MusicUrl(String value) {
		Music_MusicUrl = value;
		return this;
	}

	public String getMusic_MusicUrl() {
		return Music_MusicUrl;
	}

	private String Music_Description;

	public ReplyMsgFormat setMusic_Description(String value) {
		Music_Description = value;
		return this;
	}

	public String getMusic_Description() {
		return Music_Description;
	}

	private String Music_HQMusicUrl;

	public ReplyMsgFormat setMusic_HQMusicUrl(String value) {
		Music_HQMusicUrl = value;
		return this;
	}

	public String getMusic_HQMusicUrl() {
		return Music_HQMusicUrl;
	}

	private String Music_ThumbMediaId;

	public ReplyMsgFormat setMusic_ThumbMediaId(String value) {
		Music_ThumbMediaId = value;
		return this;
	}

	public String getMusic_ThumbMediaId() {
		return Music_ThumbMediaId;
	}

	private List<ArticleItem> News_Articles;

	public ReplyMsgFormat setNews_Articles(List<ArticleItem> value) {
		News_Articles = value;
		return this;
	}

	public List<ArticleItem> getNews_Articles() {
		return News_Articles;
	}

	public ReplyMsgFormat addNews_Articles(ArticleItem value) {
		if (News_Articles == null) News_Articles = new ArrayList<ArticleItem>();
		News_Articles.add(value);
		return this;
	}
}
