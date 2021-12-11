package com.mega.tank.role;

import com.mega.tank.Direction;
import com.mega.tank.TankFrame;

import java.awt.*;

public abstract class Bullet extends GameRole implements Removable {

    private static final int DEFAULT_SPEED = 10;

    protected Direction dir;

    protected Rectangle rect;

    protected Tank tank;

    protected boolean living = true;

    @Override
    public void move() {
        int speed = DEFAULT_SPEED;
        switch (dir) {
            case LEFT:
                x -= speed;
                break;
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case RIGHT:
                x += speed;
                break;
        }
        rect.x = this.x;
        rect.y = this.y;
        if (x < 0 || y < 0 || x > TankFrame.WIDTH || y > TankFrame.HEIGHT) {
            //gm.getGameRoles().remove(this);
            this.living = false;
        }
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
