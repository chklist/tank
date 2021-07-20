package com.mega.tank.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * 图像处理工具类
 */
class ImageUtil {
    /**
     * 图像翻转
     *
     * @param bufferedImage 缓存图像
     * @param degree        翻转度数
     * @return 缓存图像
     */
    static BufferedImage rotateImage(final BufferedImage bufferedImage,
                                     final int degree) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int type = bufferedImage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2.0, h / 2.0);
        graphics2d.drawImage(bufferedImage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}
