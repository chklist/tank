package com.mega.tank;

import com.mega.tank.util.ResourceMgr;

import java.awt.*;

public class Explode extends GameRole {

    static int WIDTH = ResourceMgr.explodes[0].getWidth();
    static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int step = 0;

    Explode(GameModel gm, int x, int y) {
        this.gm = gm;
        this.x = x;
        this.y = y;
    }

    @Override
    void paint(Graphics g) {
        g.drawImage(ResourceMgr.explodes[step++], x, y, null);
        if (step >= ResourceMgr.explodes.length)
            gm.getGameRoles().remove(this);
    }

    @Override
    public void move() {
        throw new UnsupportedOperationException();
    }
}
