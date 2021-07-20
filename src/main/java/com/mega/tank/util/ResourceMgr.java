package com.mega.tank.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ResourceMgr {
    public static BufferedImage tankLeft, tankUp, tankDown, tankRight;
    public static BufferedImage bulletLeft, bulletUp, bulletDown, bulletRight;
    public static BufferedImage[] explodes = new BufferedImage[16];

    static {
        try {
            tankLeft = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResource("images/tankL.gif")));
            tankUp = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResource("images/tankU.gif")));
            tankDown = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResource("images/tankD.gif")));
            tankRight = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResource("images/tankR.gif")));

            bulletUp = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResource("images/bulletU.png")));
            bulletDown = ImageUtil.rotateImage(bulletUp, 180);
            bulletLeft = ImageUtil.rotateImage(bulletUp, -90);
            bulletRight = ImageUtil.rotateImage(bulletUp, 90);

            for (int i = 0; i < 16; i++) {
                explodes[i] = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
