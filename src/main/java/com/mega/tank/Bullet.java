package com.mega.tank;

import com.mega.tank.util.ResourceMgr;

import java.awt.*;
import java.util.UUID;

public class Bullet extends GameRole {
    private static final int WIDTH = ResourceMgr.bulletUp.getWidth();
    private static final int HEIGHT = ResourceMgr.bulletUp.getHeight();

    private Tank tank;

    private Rectangle rect;

    private boolean living = true;

    Bullet(Direction dir, Tank tank) {
        this(dir, GameModel.INSTANCE, tank, tank.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2, tank.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2);
    }

    private Bullet(Direction dir, GameModel gm, Tank tank) {
        this(dir, gm, tank, tank.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2, tank.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2);
    }

    private Bullet(Direction dir, GameModel gm, Tank tank, int x, int y) {
        this.dir = dir;
        this.gm = gm;
        this.tank = tank;
        this.x = x;
        this.y = y;

        this.uuid = UUID.randomUUID();
        rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    @Override
    public void move() {
        super.move();
        rect.x = this.x;
        rect.y = this.y;
        if (x < 0 || y < 0 || x > TankFrame.WIDTH || y > TankFrame.HEIGHT) {
            gm.getGameRoles().remove(this.uuid);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (!living) {
            gm.getGameRoles().remove(this.uuid);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletLeft, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletUp, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletDown, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletRight, x, y, null);
                break;
        }
        move();
    }

    public Rectangle getRect() {
        return rect;
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public boolean isLiving() {
        return living;
    }

    public void die() {
        living = false;
    }
}
