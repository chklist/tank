package com.mega.tank.role.pretty;

import com.mega.tank.GameModel;
import com.mega.tank.role.Explode;
import com.mega.tank.util.ResourceMgr;

import java.awt.*;

public class PrettyExplode extends Explode {

    static int WIDTH = ResourceMgr.prettyExplodes[10].getWidth();
    static int HEIGHT = ResourceMgr.prettyExplodes[10].getHeight();

    private int step = 0;

    PrettyExplode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.prettyExplodes[step++], x, y, null);
        if (step >= ResourceMgr.prettyExplodes.length)
            GameModel.getInstance().remove(this);
    }
}
