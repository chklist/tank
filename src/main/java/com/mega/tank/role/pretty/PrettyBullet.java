package com.mega.tank.role.pretty;

import com.mega.tank.Direction;
import com.mega.tank.GameModel;
import com.mega.tank.role.Bullet;
import com.mega.tank.role.Tank;
import com.mega.tank.util.ResourceMgr;

import java.awt.*;

public class PrettyBullet extends Bullet {
    private static final int WIDTH = ResourceMgr.bullet2Up.getWidth();
    private static final int HEIGHT = ResourceMgr.bullet2Up.getHeight();

    PrettyBullet(Tank tank) {
        this(tank.getDir(), tank, tank.getX() + PrettyTank.WIDTH / 2 - PrettyBullet.WIDTH / 2, tank.getY() + PrettyTank.HEIGHT / 2 - PrettyBullet.HEIGHT / 2);
    }

    PrettyBullet(Direction dir, Tank tank) {
        this(dir, tank, tank.getX() + PrettyTank.WIDTH / 2 - PrettyBullet.WIDTH / 2, tank.getY() + PrettyTank.HEIGHT / 2 - PrettyBullet.HEIGHT / 2);
    }

    private PrettyBullet(Direction dir, Tank tank, int x, int y) {
        this.dir = dir;
        this.tank = tank;
        this.x = x;
        this.y = y;

        rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void paint(Graphics g) {
        if (!living) {
            GameModel.getInstance().remove(this);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bullet2Left, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bullet2Up, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bullet2Down, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bullet2Right, x, y, null);
                break;
        }
        move();
    }
}
