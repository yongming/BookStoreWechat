package com.wechat.common;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.wechat.bean.*;
import com.wechat.controller.*;
import com.wechat.interceptor.WebInterceptor;

public class Config extends JFinalConfig {
	public static class MyConfig {
		public static Boolean DevMode = false;
		public static String CDNHost = "";
		public static String WebHost = "";
		public static String WechatToken = "";
	}

	public Config() {
		loadPropertyFile("config.properties");
		MyConfig.DevMode = getPropertyToBoolean("DebugMode", false);
		MyConfig.CDNHost = getProperty("CDNHost", "");
		MyConfig.WebHost = getProperty("WebHost", "");
		MyConfig.WechatToken = getProperty("WechatToken", "");
	}

	public void configConstant(Constants me) {
		me.setViewType(ViewType.FREE_MARKER);
		me.setDevMode(MyConfig.DevMode);
		me.setUrlParaSeparator("&");
	}

	public void configRoute(Routes me) {
		me.add(new WebRoutes());
		me.add(new WechatRoutes());
	}

	private class WebRoutes extends Routes {
		public void config() {
			add("/", IndexController.class);
			add("/book", WebBookSearchController.class);
			add("/store", WebStoreSearchController.class);
			add("/member", WebMemberController.class);
			add("/showcard", VipPictureController.class);
			add("/activity", WebActivityController.class);
		}
	}

	private class WechatRoutes extends Routes {
		public void config() {
			add("/wechat", WechatController.class);
			add("/wechat/message/event", WcEventController.class);
			add("/wechat/message/location", WcLocationController.class);
			add("/wechat/message/picture", WcPictureController.class);
			add("/wechat/action/findBook", WcBookSearchController.class);
			add("/wechat/action/storeSearch", WcStoreSearchController.class);
			add("/wechat/action/member", WcVipController.class);
		}
	}

	public void configPlugin(Plugins me) {

		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("JdbcUrl"), getProperty("JdbcUser"), getProperty("JdbcPass").trim());
		c3p0Plugin.setAcquireIncrement(2).setMaxIdleTime(60);
		c3p0Plugin.setInitialPoolSize(2).setMaxPoolSize(20).setMinPoolSize(1);
		me.add(c3p0Plugin);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.addMapping("stocknum", BookStockBean.class);
		arp.addMapping("storelist", "storeId", StoreListBean.class);
		arp.addMapping("userlist", "userWechat", UserListBean.class);
		me.add(arp);

	}

	public void configInterceptor(Interceptors me) {
		me.add(new WebInterceptor());
	}

	public void configHandler(Handlers me) {

	}

}
