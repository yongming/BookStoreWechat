package com.wss.utils;

public class ArticleItem {
	private String Title;

	public ArticleItem setTitle(String value) {
		Title = value;
		return this;
	}

	public String getTitle() {
		return Title;
	}

	private String Description;

	public ArticleItem setDescription(String value) {
		Description = value;
		return this;
	}

	public String getDescription() {
		return Description;
	}

	private String PicUrl;

	public ArticleItem setPicUrl(String value) {
		PicUrl = value;
		return this;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	private String Url;

	public ArticleItem setUrl(String value) {
		Url = value;
		return this;
	}

	public String getUrl() {
		return Url;
	}
}