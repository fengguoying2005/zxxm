package txsb;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Test {

	public static void main(String[] args) throws Exception {
		BufferedImage bi = (BufferedImage) ImageIO.read(new File("C:/e.jpg"));

		// 获取图像的宽度和高度
		int width = bi.getWidth();
		int height = bi.getHeight();

		// 扫描图片
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {// 行扫描
				int dip = bi.getRGB(j, i);
				if (dip == -1)
					System.out.print(" ");
				else
					System.out.print("♦");
			}
			System.out.println();// 换行
		}
	}

}
