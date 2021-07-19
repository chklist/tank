package com.mega.tank;

import java.awt.*;

public abstract class GameRole implements Removable {
    private static final int DEFAULT_SPEED = 10;

    int speed = DEFAULT_SPEED;

    GameModel gm;

    Direction dir;

    int x, y;

    @Override
    public void move() {
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
    }

    abstract void paint(Graphics graphics);

    public void setGm(GameModel gm) {
        this.gm = gm;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
