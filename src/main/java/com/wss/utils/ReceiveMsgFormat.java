package com.wss.utils;

public class ReceiveMsgFormat {
	public enum ReceiveType {
		TEXT, IMAGE, VOICE, VIDEO, LOCATION, LINK, EVENT
	}

	public enum EventType {
		SUBSCRIBE, UNSUBSCRIBE,CLICK
	}

	private EventType Event;

	public EventType getEvent() {
		return Event;
	}

	public void setEvent(EventType value) {
		Event = value;
	}
	
	private String Click_EventKey;
	public void setCick_EventKey(String value){
		Click_EventKey=value;
	}
	public String getCick_EventKey(){
		return Click_EventKey;
	}

	private String Text_Content;

	public String getText_Content() {
		return Text_Content;
	}

	public void setText_Content(String value) {
		Text_Content = value;
	}

	private String Image_PicUrl;

	public String getImage_PicUrl() {
		return Image_PicUrl;
	}

	public void setImage_PicUrl(String value) {
		Image_PicUrl = value;
	}

	private String Image_MediaId;

	public String getImage_MediaId() {
		return Image_MediaId;
	}

	public void setImage_MediaId(String value) {
		Image_MediaId = value;
	}

	private String Voice_MediaId;

	public String getVoice_MediaId() {
		return Voice_MediaId;
	}

	public void setVoice_MediaId(String value) {
		Voice_MediaId = value;
	}

	private String Voice_Format;

	public String getVoice_Format() {
		return Voice_Format;
	}

	public void setVoice_Format(String value) {
		Voice_Format = value;
	}

	private String Video_MediaId;

	public String getVideo_MediaId() {
		return Video_MediaId;
	}

	public void setVideo_MediaId(String value) {
		Video_MediaId = value;
	}

	private String Video_ThumbMediaId;

	public String getVideo_ThumbMediaId() {
		return Video_ThumbMediaId;
	}

	public void setVideo_ThumbMediaId(String value) {
		Video_ThumbMediaId = value;
	}

	private Float Location_Location_X;

	public Float getLocation_Location_X() {
		return Location_Location_X;
	}

	public void setLocation_Location_X(Float value) {
		Location_Location_X = value;
	}

	private Float Location_Location_Y;

	public Float getLocation_Location_Y() {
		return Location_Location_Y;
	}

	public void setLocation_Location_Y(Float value) {
		Location_Location_Y = value;
	}

	private Integer Location_Scale;

	public Integer getLocation_Scale() {
		return Location_Scale;
	}

	public void setLocation_Scale(Integer value) {
		Location_Scale = value;
	}

	private String Location_Label;

	public String getLocation_Label() {
		return Location_Label;
	}

	public void setLocation_Label(String value) {
		Location_Label = value;
	}

	private String Link_Title;

	public String getLink_Title() {
		return Link_Title;
	}

	public void setLink_Title(String value) {
		Link_Title = value;
	}

	private String Link_Description;

	public String getLink_Description() {
		return Link_Description;
	}

	public void setLink_Description(String value) {
		Link_Description = value;
	}

	private String Link_Url;

	public String getLink_Url() {
		return Link_Url;
	}

	public void setLink_Url(String value) {
		Link_Url = value;
	}

	private String ToUserName;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String value) {
		ToUserName = value;
	}

	private String FromUserName;

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String value) {
		FromUserName = value;
	}

	private String CreateTime;

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String value) {
		CreateTime = value;
	}

	private ReceiveType MsgType;

	public ReceiveType getMsgType() {
		return MsgType;
	}

	public void setMsgType(ReceiveType value) {
		MsgType = value;
	}

	private String MsgId;

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String value) {
		MsgId = value;
	}

}
