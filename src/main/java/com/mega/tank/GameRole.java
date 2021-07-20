package com.mega.tank;

import java.awt.*;
import java.util.UUID;

public abstract class GameRole implements Removable {
    private static final int DEFAULT_SPEED = 10;

    UUID uuid;

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

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "GameRole{" +
                "uuid=" + uuid +
                ", speed=" + speed +
                ", gm=" + gm +
                ", dir=" + dir +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
