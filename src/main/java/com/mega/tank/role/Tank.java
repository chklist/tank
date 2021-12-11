package com.mega.tank.role;

import com.mega.tank.Direction;
import com.mega.tank.Group;

import java.awt.Rectangle;

public abstract class Tank extends GameRole implements Removable, Fireable {

    private static final int DEFAULT_SPEED = 10;

    private int speed = DEFAULT_SPEED;

    protected Direction dir;

    private boolean moving = false;

    protected Group group;

    protected Rectangle rect;

    protected boolean living = true;

    public abstract void die();

    @Override
    public void move() {
        if (!moving) {
            return;
        }
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
    }

    public Group getGroup() {
        return group;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isLiving() {
        return living;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Direction getDir() {
        return dir;
    }
}
