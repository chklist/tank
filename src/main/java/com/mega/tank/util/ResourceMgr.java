package com.mega.tank.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ResourceMgr {
    public static BufferedImage tank1Left, tank1Up, tank1Down, tank1Right;
    public static BufferedImage tank2Left, tank2Up, tank2Down, tank2Right;
    public static BufferedImage bullet1Left, bullet1Up, bullet1Down, bullet1Right;
    public static BufferedImage bullet2Left, bullet2Up, bullet2Down, bullet2Right;
    public static BufferedImage[] simpleExplodes = new BufferedImage[16];
    public static BufferedImage[] prettyExplodes = new BufferedImage[11];

    static {
        try {
            ClassLoader classLoader = ResourceMgr.class.getClassLoader();
            tank1Up = ImageIO.read(Objects.requireNonNull(classLoader.getResource("images/tankU_1.gif")));
            tank1Down = ImageUtil.rotateImage(tank1Up, 180);
            tank1Left = ImageUtil.rotateImage(tank1Up, -90);
            tank1Right = ImageUtil.rotateImage(tank1Up, 90);

            tank2Up = ImageIO.read(Objects.requireNonNull(classLoader.getResource("images/tankU_2.png")));
            tank2Down = ImageUtil.rotateImage(tank2Up, 180);
            tank2Left = ImageUtil.rotateImage(tank2Up, -90);
            tank2Right = ImageUtil.rotateImage(tank2Up, 90);

            bullet1Up = ImageIO.read(Objects.requireNonNull(classLoader.getResource("images/bulletU_1.gif")));
            bullet1Down = ImageUtil.rotateImage(bullet1Up, 180);
            bullet1Left = ImageUtil.rotateImage(bullet1Up, -90);
            bullet1Right = ImageUtil.rotateImage(bullet1Up, 90);

            bullet2Up = ImageIO.read(Objects.requireNonNull(classLoader.getResource("images/bulletU_2.png")));
            bullet2Down = ImageUtil.rotateImage(bullet2Up, 180);
            bullet2Left = ImageUtil.rotateImage(bullet2Up, -90);
            bullet2Right = ImageUtil.rotateImage(bullet2Up, 90);

            for (int i = 0; i < 16; i++) {
                simpleExplodes[i] = ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream("images/e" + (i + 1) + ".gif")));
            }

            for (int i = 0; i < 11; i++) {
                prettyExplodes[i] = ImageIO.read(Objects.requireNonNull(classLoader.getResourceAsStream("images/" + i + ".gif")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
