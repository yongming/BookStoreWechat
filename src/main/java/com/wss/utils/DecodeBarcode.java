package com.wss.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wechat.common.Config;

public class DecodeBarcode {
	private SimpleHttpHelper httpHelper;

	public DecodeBarcode() {
		this("");
	}

	public DecodeBarcode(String picUrl) {
		httpHelper = new SimpleHttpHelper("http://zxing.org/w/decode");
		setPicUrl(picUrl);
	}

	public void setPicUrl(String picUrl) {
		httpHelper.clearPara();
		httpHelper.setPara("u", picUrl);
	}

	public BarcodeData parse() {
		BarcodeData result = null;
		try {
			String parseHtml = httpHelper.sendGet();
			if (parseHtml == null || "".equals(parseHtml)) { return null; }
			parseHtml = (parseHtml.split("</table>",2))[0];
			String regx = "";
			regx += "<table style=\"width:100%\">";
			regx += "<tr><td>Raw text</td><td><pre style=\"margin:0\">([\\s\\S]*)</pre></td></tr>";
			regx += "<tr><td>Raw bytes</td><td><pre style=\"margin:0\">([\\s\\S]*)</pre></td></tr>";
			regx += "<tr><td>Barcode format</td><td>([\\s\\S]*)</td></tr>";
			regx += "<tr><td>Parsed Result Type</td><td>([\\s\\S]*)</td></tr>";
			regx += "<tr><td>Parsed Result</td><td><pre style=\"margin:0\">([\\s\\S]*)</pre></td></tr>";
			//regx += "</table>";
			Matcher m = Pattern.compile(regx).matcher(parseHtml);
			if (m.find() && m.groupCount() == 5) {
				result = new BarcodeData();
				result.RawText = m.group(1);
				result.BarcodeFormat = m.group(3);
				result.ParsedResultType = m.group(4);
				result.ParsedResult = m.group(5);
				return result;
			}
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			if (Config.MyConfig.DevMode) {
				System.out.println("-条码解析完成--------------------------------------------------------------------");
				System.out.println(System.currentTimeMillis());
				System.out.println(result == null ? "失败！" : result.getRawText());
				System.out.println("--------------------------------------------------------------------------------");
			}
		}
	}

	public class BarcodeData {
		private String RawText;
		private String BarcodeFormat;
		private String ParsedResultType;
		private String ParsedResult;

		public String getRawText() {
			return RawText;
		}

		public String getBarcodeFormat() {
			return BarcodeFormat;
		}

		public String getParsedResultType() {
			return ParsedResultType;
		}

		public String getParsedResult() {
			return ParsedResult;
		}
	}
}
