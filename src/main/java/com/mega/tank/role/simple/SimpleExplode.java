package com.mega.tank.role.simple;

import com.mega.tank.GameModel;
import com.mega.tank.role.Explode;
import com.mega.tank.util.ResourceMgr;

import java.awt.*;

public class SimpleExplode extends Explode {

    static int WIDTH = ResourceMgr.simpleExplodes[0].getWidth();
    static int HEIGHT = ResourceMgr.simpleExplodes[0].getHeight();

    private int step = 0;

    SimpleExplode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.simpleExplodes[step++], x, y, null);
        if (step >= ResourceMgr.simpleExplodes.length)
            GameModel.getInstance().remove(this);
    }
}
