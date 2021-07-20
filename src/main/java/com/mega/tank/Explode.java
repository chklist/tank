package com.mega.tank;

import com.mega.tank.util.ResourceMgr;

import java.awt.*;
import java.util.UUID;

public class Explode extends GameRole {

    static int WIDTH = ResourceMgr.explodes[0].getWidth();
    static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int step = 0;

    Explode(int x, int y) {
        this.x = x;
        this.y = y;

        this.uuid = UUID.randomUUID();
    }

    @Override
    void paint(Graphics g) {
        g.drawImage(ResourceMgr.explodes[step++], x, y, null);
        if (step >= ResourceMgr.explodes.length)
            GameModel.INSTANCE.getGameRoles().remove(this.uuid);
    }

    @Override
    public void move() {
        throw new UnsupportedOperationException();
    }
}
