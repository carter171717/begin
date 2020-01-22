package com.aling.common.util;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码生成器
 *
 * @author Boomer
 * @date 2017/10/16 10:50
 */
public class RandomCodeUtil {

	public static final String KEY_BUFF_IMG = "buffImg";
	public static final String KEY_RANDOM_CODE = "randomCode";

	/**
	 * <p>Description:创建验证码</p>
	 * <p>Author:Gred</p>
	 * <p>Date:2017/4/12 9:23</p>
	 * <p>param:request</p>
	 **/
	public static Map<String, Object> createRandomImg(HttpServletRequest request) {
		//验证码图片的宽度。
		int width = 60;
		//验证码图片的高度。
		int height = 20;
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();

		//创建一个随机数生成器类。
		Random random = new Random();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		//创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Times New Roman", Font.PLAIN, 18);
		//设置字体。
		g.setFont(font);

		//画边框。
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);

		//随机产生10条干扰线，使图象中的认证码不易被其它程序探测到。
		g.setColor(Color.GRAY);
		int lineTimes = 10;
		for (int i = 0; i < lineTimes; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		//randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;

		//随机产生4位数字的验证码。
		int codeNumber = 4;
		for (int i = 0; i < codeNumber; i++) {
			//得到随机产生的验证码数字。
			String strRand = String.valueOf(random.nextInt(10));

			//产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(200);
			green = random.nextInt(200);
			blue = random.nextInt(200);

			//产生随机高度 13至height之间
			float imght = 0;
			int randomHeight = 12;
			while (imght <= randomHeight) {
				imght = Float.parseFloat(String.valueOf(random.nextInt(height)));
			}
			//用随机产生的颜色将验证码绘制到图像中。
			g.setColor(new Color(red, green, blue));
			g.drawString(strRand, 13 * i + 6, imght);

			//将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		Map<String, Object> map = new HashMap<>();
		map.put(KEY_RANDOM_CODE, randomCode.toString());
		map.put(KEY_BUFF_IMG, buffImg);
		//将四位数字的验证码保存到Session中。
//		HttpSession session = request.getSession();
//		session.setAttribute("randomCode", );
		return map;
	}
}
