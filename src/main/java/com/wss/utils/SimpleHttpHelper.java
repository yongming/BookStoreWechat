package com.wss.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.wechat.common.Config;

public class SimpleHttpHelper {

	private ArrayList<String> paraList = new ArrayList<String>();
	private String httpUrl = "";

	public SimpleHttpHelper() {

	}

	public SimpleHttpHelper(String URL) {
		setUrl(URL);
	}

	public void setPara(String name, Object value) {
		try {
			paraList.add(name + "=" + URLEncoder.encode(value + "", "UTF-8"));
		} catch (UnsupportedEncodingException e) {}
	}

	public void clearPara() {
		paraList.clear();
	}

	public void setUrl(String Url) {
		if (Url.indexOf("http") != 0) {
			Url = "http://" + Url;
		}
		httpUrl = Url;
	}

	private String getParaStr() {
		StringBuilder str = new StringBuilder();
		for (int i = paraList.size() - 1; i >= 0; i--) {
			str.append(paraList.get(i));
			if (i > 0) str.append("&");
		}
		return str.toString();
	}

	public String sendGet() {
		String url = httpUrl;
		String ParaStr = getParaStr();
		if (!"".equals(ParaStr)) {
			url += httpUrl.indexOf("?") >= 0 ? "&" : "?";
			url += ParaStr;
		}
		return sendRequest(url, null);
	}

	public String sendPost() {
		return sendRequest(httpUrl, getParaStr());
	}

	private String sendRequest(String reqUrl, String post) {
		if (Config.MyConfig.DevMode) {
			System.out.println("-发送HTTP请求-------------------------------------------------------------------");
			System.out.println(System.currentTimeMillis());
			System.out.println(reqUrl);
			System.out.println("Loading...");
		}
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod(post == null ? "GET" : "POST");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(false);
			urlConnection.setConnectTimeout(4500);
			if (post != null) {
				OutputStream outputStream = urlConnection.getOutputStream();
				outputStream.write(post.toString().getBytes());
				outputStream.flush();
				outputStream.close();
			}
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			StringBuffer respStr = new StringBuffer();
			String curLine;
			while ((curLine = bufferedReader.readLine()) != null) {
				respStr.append(curLine);
				respStr.append("\r\n");
			}
			return respStr.toString();
		} catch (Exception e) {
			if (Config.MyConfig.DevMode) {
				System.out.println("Http Error:" + e.getMessage());
			}
			return null;
		} finally {
			// 省略垃圾回收语句...
			if (Config.MyConfig.DevMode) {
				System.out.println(System.currentTimeMillis());
				System.out.println("--------------------------------------------------------------------------------");
			}
		}
	}
}
