package com.mega.tank;

import com.mega.tank.strategy.FireStrategy;
import com.mega.tank.util.ResourceMgr;

import java.awt.*;
import java.util.Random;

public class Tank extends GameRole implements Fireable, Removable {

    static final int WIDTH = ResourceMgr.tankUp.getWidth();
    static final int HEIGHT = ResourceMgr.tankUp.getHeight();

    private boolean moving = false;

    private Group group;

    private Rectangle rect;

    private boolean living = true;

    private Random random = new Random();

    /**
     * CGLIB 需要一个空构造器
     */
    Tank() {
        this(Direction.UP);
    }

    Tank(Direction dir) {
        this(dir, GameModel.INSTANCE);
    }

    Tank(Direction dir, GameModel gm) {
        this(dir, gm, Group.GOOD, (TankFrame.WIDTH - 50) >> 1, 400);
    }

    Tank(Direction dir, GameModel gm, Group group, int x, int y) {
        this.dir = dir;
        this.gm = gm;
        this.group = group;
        this.x = x;
        this.y = y;

        rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public Group getGroup() {
        return group;
    }

    void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isLiving() {
        return living;
    }

    public void die() {
        living = false;
        int eX = x + Tank.WIDTH / 2 - Explode.WIDTH / 2;
        int eY = y + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
        gm.getGameRoles().add(new Explode(gm, eX, eY));
    }

    public Rectangle getRect() {
        return rect;
    }

    @Override
    public void move() {
        if (!moving) {
            return;
        }
        super.move();

        rect.x = this.x;
        rect.y = this.y;

        if (group == Group.BAD && random.nextInt(100) > 95) {
            this.fire(() -> gm.getGameRoles().add(new Bullet(dir, gm, this)));
            randomDir();
        }
        boundsCheck();
    }

    @Override
    public void paint(Graphics g) {
        if (!living) {
            gm.getGameRoles().remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.tankLeft, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.tankUp, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tankDown, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tankRight, x, y, null);
                break;
        }
        move();
    }

    @Override
    public void fire(FireStrategy fireStrategy) {
        fireStrategy.fire();
    }

    private void randomDir() {
        this.dir = Direction.values()[random.nextInt(4)];
    }

    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 25) y = 25;
        if (this.x > TankFrame.WIDTH - Tank.WIDTH - 2) x = TankFrame.WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.HEIGHT - Tank.HEIGHT - 2) y = TankFrame.HEIGHT - Tank.HEIGHT - 2;
    }
}