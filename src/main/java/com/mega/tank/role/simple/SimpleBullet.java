package com.mega.tank.role.simple;

import com.mega.tank.Direction;
import com.mega.tank.GameModel;
import com.mega.tank.role.Bullet;
import com.mega.tank.role.Tank;
import com.mega.tank.util.ResourceMgr;

import java.awt.*;

public class SimpleBullet extends Bullet {
    private static final int WIDTH = ResourceMgr.bullet1Up.getWidth();
    private static final int HEIGHT = ResourceMgr.bullet1Up.getHeight();

    SimpleBullet(Tank tank) {
        this(tank.getDir(), tank, tank.getX() + SimpleTank.WIDTH / 2 - SimpleBullet.WIDTH / 2, tank.getY() + SimpleTank.HEIGHT / 2 - SimpleBullet.HEIGHT / 2);
    }

    SimpleBullet(Direction dir, Tank tank) {
        this(dir, tank, tank.getX() + SimpleTank.WIDTH / 2 - SimpleBullet.WIDTH / 2, tank.getY() + SimpleTank.HEIGHT / 2 - SimpleBullet.HEIGHT / 2);
    }

    private SimpleBullet(Direction dir, Tank tank, int x, int y) {
        this.dir = dir;
        this.tank = tank;
        this.x = x;
        this.y = y;

        rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void paint(Graphics g) {
        if (!living) {
            GameModel.getInstance().getGameRoles().remove(this);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bullet1Left, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bullet1Up, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bullet1Down, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bullet1Right, x, y, null);
                break;
        }
        move();
    }
}
