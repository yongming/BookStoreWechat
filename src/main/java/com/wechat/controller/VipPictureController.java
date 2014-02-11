package com.wechat.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.net.URLDecoder;

import javax.imageio.ImageIO;

import com.jfinal.core.Controller;

public class VipPictureController extends Controller {
	public void index() {
		try {
			String name = getPara(0);
			if (null == name || "".equals(name = name.trim())) {
				renderNull();
				return;
			}
			BufferedImage image = ImageIO.read(new FileInputStream(getSession().getServletContext().getRealPath("/") + "/static/images/vipcard.png"));
			Graphics2D g = image.createGraphics();
			g.setColor(Color.WHITE);
			g.setFont(new Font("宋体", Font.BOLD, 20));
			g.drawString("姓名:" + URLDecoder.decode(name, "UTF-8"), 35, image.getHeight() - 35);
			g.dispose();
			ImageIO.write(image, "PNG", getResponse().getOutputStream());
			renderNull();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
